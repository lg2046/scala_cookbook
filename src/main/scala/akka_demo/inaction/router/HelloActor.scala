package akka_demo.inaction.router

import akka.actor.{Actor, ActorIdentity, ActorSystem, DeadLetter, Identify, PoisonPill, Props}
import akka.agent.Agent
import akka.pattern._
import akka.util.Timeout
import akka_demo.inaction.router.HelloActor.SayHello

import scala.concurrent.duration._

object HelloActor {

  def props = Props[HelloActor]

  // Messages
  case class SayHello(name: String)


  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val timeout: Timeout = 3.seconds
    implicit val dispatcher = system.dispatcher

    val stateAgent = Agent(1)

    println(stateAgent.get())

    stateAgent.send(oldState => oldState + 1)

    stateAgent.alter(oldState => oldState + 1).foreach { _ =>
      println(stateAgent.get())
    }



    //    val hello1 = system.actorOf(Props(new HelloActor), "W1")
    //
    //    system.eventStream.subscribe(hello1, classOf[DeadLetter])
    //
    //    val hello2 = system.actorOf(Props(new HelloActor), "W2")
    //
    //    hello2 ! PoisonPill
    //
    //    hello2.tell("haha", hello1)
    //
    //    system.deadLetters.tell("haha", hello1)
    //
    //    println(system.deadLetters.path)
    //
    //    (system.actorSelection("/") ? Identify(None)).mapTo[ActorIdentity].map(println)


    //    val hello2 = system.actorOf(Props(new HelloActor), "W2")
    //    val hello3 = system.actorOf(Props(new HelloActor), "W3")
    //    val hello4 = system.actorOf(Props(new HelloActor), "W4")
    //    val hello5 = system.actorOf(Props(new HelloActor), "W5")
    //
    //    val grouper = system.actorOf(BroadcastGroup(List(
    //      "/user/W1",
    //      "/user/W2"
    //    )).props(), "random-router-group")
    //
    //    grouper ! SayHello("1")
    //    grouper ! SayHello("2")
    //    grouper ! SayHello("3")
    //    grouper ! SayHello("4")
    //    grouper ! SayHello("5")
    //
    //    Thread.sleep(3000)
    //    println("addRoutee")
    //    grouper ! AddRoutee(ActorRefRoutee(hello3))
    //    Thread.sleep(3000)
    //
    //    grouper ! SayHello("1")
    //    grouper ! SayHello("2")
    //    grouper ! SayHello("3")
    //    grouper ! SayHello("4")
    //    grouper ! SayHello("5")
  }
}


class HelloActor extends Actor {
  override def receive: Receive = {
    case SayHello(name) =>
      println(s"Hello , $name, i am ${self}")

    case DeadLetter(m, s, r) =>
      println(s"m: $m , s: $s, r: $r")
  }
}

