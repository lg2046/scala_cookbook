package akka_demo.inaction.persistent_actor

import akka.actor.ActorSystem
import akka.pattern._
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream._
import akka.stream.scaladsl._
import akka.util.Timeout
import akka_demo.inaction.persistent_actor.Calculator.{Clear, Reset}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object CalculatorApp {


  def main(args: Array[String]): Unit = {
    Class.forName("com.mysql.cj.jdbc.Driver")

    //创建actorSystem运行Graph
    implicit val system = ActorSystem()

    implicit val timeout: Timeout = 5.seconds

    val calc = system.actorOf(Calculator.props, Calculator.name)

//    calc ! Calculator.Add(1)
//    calc ! Calculator.Multiply(2)


//    calc ! Clear

    //异常后会自动恢复
    val f = (calc ? Calculator.GetResult)
      .mapTo[Double]

    f.foreach(println)
    f.failed.foreach(println)

        implicit val mat = ActorMaterializer()

        //返回一个ReadJournal
        val queries =
          PersistenceQuery(system).readJournalFor[LeveldbReadJournal](
            LeveldbReadJournal.Identifier
          )

        //current方法开头的就是读完后结束流
        //其他的不是 而是等待有新event并持续提供值
        val src = queries.currentEventsByPersistenceId(
          Calculator.name, 0L, Long.MaxValue
        )//.map(_.event.asInstanceOf[Calculator.Event])


        src.runWith(Sink.foreach(println))
  }
}
