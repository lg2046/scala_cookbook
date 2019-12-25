package akka_demo.inaction.akkastream

import java.nio.file.Paths
import java.nio.file.StandardOpenOption._

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, IOResult}
import akka.util.ByteString

import scala.concurrent.Future

object StreamingCopy {
  def main(args: Array[String]): Unit = {
    val source: Source[ByteString, Future[IOResult]] = FileIO.fromPath(Paths.get("logs/app.log"))
    val sink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(
      Paths.get("logs/app2.log"), Set(CREATE, WRITE, APPEND)
    )

    val runnableGraph: RunnableGraph[Future[IOResult]] = source.to(sink)

    //创建actorSystem运行Graph
    implicit val system = ActorSystem()
    implicit val ec = system.dispatcher
    implicit val materializer = ActorMaterializer()

    runnableGraph.run().foreach { result =>
      println(s"${result.status}, ${result.count} bytes read")
      system.terminate()
    }
  }
}
