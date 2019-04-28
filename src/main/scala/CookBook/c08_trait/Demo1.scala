package CookBook.c08_trait

import scala.language.reflectiveCalls

trait BaseSoundPlayer {
  def play()

  def close()

  def pause()

  def stop()

  def resume()
}

trait A2 {

}

//class Mp3Player extends BaseSoundPlayer with A2 {
//  //...
//}

case class Player(name: String) {
  def google(password: String): Boolean = true
}

case class Google(name:String){
  def printGo(): Unit = println("go")
}

trait Go{
  def printGo2(): Unit = println("trait go")
}

object Demo1 {
  def main(args: Array[String]): Unit = {
    (new Google("google") with Go).printGo2()
  }
}


trait Fly {
  this: {def google(password: String): Boolean} =>

  def sayHello(): Unit = println(google("haha"))
}