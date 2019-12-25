package dp.d3_bahavioral.d4_observer
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

//观察者
trait Observer[T] {
  def handleUpdate(subject: T)
}


//被观察者
trait Observable[T] {
  this: T => //保证被混入的时候 混入的类与Observable[T]里面的T一样

  private val observers: ListBuffer[Observer[T]] = ListBuffer[Observer[T]]()

  def addObserver(observer: Observer[T]): Unit = {
    observers += observer
  }

  def notifyObservers(): Unit = {
    observers.foreach(_.handleUpdate(this))
  }
}

case class User(name: String) extends Observer[Post] {
  override def handleUpdate(subject: Post): Unit = {
    println(
      s"""
         |Hey, I'm $name, the post got some new comments: ${subject.comments}
       """.stripMargin)
  }
}

case class Post(user: User, text: String) extends Observable[Post] {
  val comments = new ArrayBuffer[String]()

  def addComment(comment: String): Unit = {
    comments.append(comment)
    notifyObservers()
  }
}


object ObserverDemo {
  def main(args: Array[String]): Unit = {
    val user = User("andy")
    val post = Post(user, "topic title")

    post.addObserver(user)

    post.addComment("haha")
    post.addComment("laiba")
    post.addComment("comeon")
  }
}
