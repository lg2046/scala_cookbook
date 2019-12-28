package akka_demo.inaction.failure

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.BackoffSupervisor

class ChildActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: String => println(msg)
  }

  override def preStart(): Unit = println("child start")

  override def postStop(): Unit = println("child stop")
}

class LifeCycleHooks extends Actor with ActorLogging {
  println("Constructor")
  val child = context.actorOf(Props(new ChildActor))

  log.info("i am a akka log")

  override def preStart(): Unit = println("preStart")

  override def postStop(): Unit = println("postStop")

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {

    println("preReStart")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable): Unit = {
    println("postRestart")
    super.postRestart(reason)
  }

  override def receive = {
    case "restart" => throw new IllegalStateException("force restart")
    case msg: AnyRef => println("Receive")
      sender() ! msg
  }
}

object LifeCycle {
  def main(args: Array[String]): Unit = {
    //创建actorSystem运行Graph
    implicit val actorSystem = ActorSystem()
    val ec = actorSystem.dispatcher

    val lifeCycleHooks = actorSystem.actorOf(Props(new LifeCycleHooks))

    Thread.sleep(3000)

    println("-----after sleep 3000---- ")

    lifeCycleHooks ! "restart"


//    Thread.sleep(3000)
//    println("-----after sleep 3000---- ")

    //    actorSystem.stop(lifeCycleHooks)

    BackoffSupervisor
  }
}
