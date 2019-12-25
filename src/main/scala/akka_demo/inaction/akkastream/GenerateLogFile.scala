package akka_demo.inaction.akkastream

import java.nio.file.StandardOpenOption._
import java.nio.file.{Path, Paths}
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

object FileArg {
  def shellExpanded(path: String): Path = {
    if (path.startsWith("~/")) {
      Paths.get(System.getProperty("user.home"), path.drop(2))
    } else {
      Paths.get(path)
    }
  }
}

object GenerateLogFile extends App {
  val filePath = "logs/bigfile.log"
  val numberOfLines = 2000000
  val rnd = new java.util.Random()
  val sink = FileIO.toPath(FileArg.shellExpanded(filePath), Set(CREATE, WRITE, APPEND))

  def line(i: Int) = {
    val host = "my-host"
    val service = "my-service"
    val time = ZonedDateTime.now.format(DateTimeFormatter.ISO_INSTANT)
    val state = if (i % 10 == 0) "warning"
    else if (i % 101 == 0) "error"
    else if (i % 1002 == 0) "critical"
    else "ok"
    val description = "Some description of what has happened."
    val tag = "tag"
    val metric = rnd.nextDouble() * 100
    s"$host | $service | $state | $time | $description | $tag | $metric \n"
  }

  val graph = Source.fromIterator { () =>
    Iterator.tabulate(numberOfLines)(line)
  }.map(l => ByteString(l)).toMat(sink)(Keep.right)

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  graph.run().foreach { result =>
    println(s"Wrote ${result.count} bytes to '$filePath'.")
    system.terminate()
  }
}