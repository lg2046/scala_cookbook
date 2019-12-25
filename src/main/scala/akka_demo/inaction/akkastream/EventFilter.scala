package akka_demo.inaction.akkastream

import java.nio.file.Paths
import java.nio.file.StandardOpenOption.{APPEND, CREATE, WRITE}

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorAttributes, ActorMaterializer, IOResult, Supervision}
import akka.stream.scaladsl.{FileIO, Flow, Framing, Keep, RunnableGraph, Sink, Source}
import akka.util.ByteString
import akka_demo.inaction.akkastream.LogStreamProcessor.LogParseException
import play.api.libs.json.Json

import scala.concurrent.Future

object EventFilter {
  def main(args: Array[String]): Unit = {
    //创建actorSystem运行Graph
    implicit val system = ActorSystem()
    implicit val ec = system.dispatcher
    implicit val materializer = ActorMaterializer()

    val inputFile = Paths.get("logs/app.log")
    val outputFile = Paths.get("logs/events.log")

    val source: Source[ByteString, Future[IOResult]] = FileIO.fromPath(inputFile)
    val sink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(outputFile, Set(CREATE, WRITE, APPEND))

    //单独定义每一个点Flow
    val frame: Flow[ByteString, String, NotUsed] =
      Framing.delimiter(ByteString("\n"), 1000)
        .map(_.decodeString("UTF8"))

    val parse: Flow[String, Event, NotUsed] = Flow[String].map(LogStreamProcessor.parseLineEx)
      .collect { case Some(e) => e }
      .withAttributes(ActorAttributes.supervisionStrategy({
        case _: LogParseException => Supervision.Resume
        case _ => Supervision.Stop
      }))

    val filter: Flow[Event, Event, NotUsed] = Flow[Event].filter(_.state == Ok)

    val serialize: Flow[Event, ByteString, NotUsed] = Flow[Event].map(event => ByteString(Json.toJson(event).toString()))

    //使用via组合Flow
    val composedFlow: Flow[ByteString, ByteString, NotUsed] = frame.via(parse).via(filter).via(serialize)

    val runnableGraph: RunnableGraph[Future[IOResult]] = source.via(composedFlow).toMat(sink)(Keep.right)



    //run
    runnableGraph.run().foreach { result =>
      println(s"Write ${result.count} bytes read to '$outputFile'.")
      system.terminate()
    }
  }
}
