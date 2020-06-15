package playground

import play.api.libs.json._

//一组元素 可以组合 带一个zero 元素
trait Monoid[A] {
  def combine(x: A, y: A): A

  def zero: A
}

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Demo1 {
  def main(args: Array[String]): Unit = {
    println(Json.parse(args.head).as[Map[String,String]])

    implicit val boolAndMonoidInstance = new Monoid[Boolean] {
      override def combine(x: Boolean, y: Boolean): Boolean = x && y

      override def zero: Boolean = true
    }

    implicit val NumberAndMonoidInstance = new Monoid[Int] {
      override def combine(x: Int, y: Int): Int = x + y

      override def zero: Int = 0
    }

    implicit val NumberMultiplyMonoidInstance = new Monoid[Int] {
      override def combine(x: Int, y: Int): Int = x * y

      override def zero: Int = 1
    }

    println(List(true, true, true).foldLeft(boolAndMonoidInstance.zero)(boolAndMonoidInstance.combine))
    println(List(1, 2, 3).foldLeft(NumberAndMonoidInstance.zero)(NumberAndMonoidInstance.combine))
    println(List(1, 2, 3, 4).foldLeft(NumberMultiplyMonoidInstance.zero)(NumberMultiplyMonoidInstance.combine))


    implicit val OptionMapInstance = new Functor[Option] {
      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
        case Some(a) => Some(f(a))
        case None => None
      }
    }

    println(OptionMapInstance.map(Option(123))(_ * 1))
    println(OptionMapInstance.map(None: Option[Int])(_ * 1))

  }
}