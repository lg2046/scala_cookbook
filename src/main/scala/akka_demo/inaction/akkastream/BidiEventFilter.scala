package akka_demo.inaction.akkastream

import java.nio.file.Paths
import java.nio.file.StandardOpenOption.{APPEND, CREATE, WRITE}

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.json.Json

import scala.concurrent.Future

object BidiEventFilter {
  def main(args: Array[String]): Unit = {
    //创建actorSystem运行Graph
    implicit val system = ActorSystem()
    implicit val ec = system.dispatcher
    implicit val materializer = ActorMaterializer()

    val inputFile = Paths.get("logs/app.log")
    val outputFile = Paths.get("logs/events.log")

    val source: Source[ByteString, Future[IOResult]] = FileIO.fromPath(inputFile)
    val sink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(outputFile, Set(CREATE, WRITE, APPEND))

    val delimiter = "newLine"

    //支持不同的粘包方式
    val inFlow: Flow[ByteString, Event, NotUsed] = if (delimiter == "json") {
      JsonFraming.objectScanner(1024 * 1024) // 最大json对象字节数
        .map(bs => Json.parse(bs.decodeString("UTF8")).as[Event])
    } else {
      Framing.delimiter(ByteString("\n"), 1000)
        .map(_.decodeString("UTF8"))
        .map(LogStreamProcessor.parseLineEx)
        .collect { case Some(e) => e }
    }

    //支持不同格式的输出
    val outputFormat = "json"
    val outFlow = if (outputFormat == "json") {
      Flow[Event].map(event => ByteString(Json.toJson(event).toString()))
    } else {
      Flow[Event].map(event => ByteString(LogStreamProcessor.logLine(event)))
    }

    val bidiFlow = BidiFlow.fromFlows(inFlow, outFlow)

    val filter: Flow[Event, Event, NotUsed] = Flow[Event].filter(_.state == Ok)

    //bidiFlow与filter进行join
    val flow = bidiFlow.join(filter)

    val runnableGraph: RunnableGraph[Future[IOResult]] = source.via(flow).toMat(sink)(Keep.right)

    //run
    runnableGraph.run().foreach { result =>
      println(s"Write ${result.count} bytes read to '$outputFile'.")
      system.terminate()
    }
  }

}
