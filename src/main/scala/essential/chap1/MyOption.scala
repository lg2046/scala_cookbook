package essential.chap1

trait Functor[F[_]] {
  def map2[A, B](fa: F[A])(fn: A => B): F[B]
}

trait MyOption[A]

case class MySome[A](v: A) extends MyOption[A]

case object MyNone extends MyOption[Nothing]

object MyOption {
  def main(args: Array[String]): Unit = {
    val MyOptionFunctor = new Functor[MyOption] {
      override def map2[A, B](fa: MyOption[A])(fn: A => B): MyOption[B] = fa match {
        case MySome(v) => MySome(fn(v))
        case MyNone => MyNone.asInstanceOf[MyOption[B]]
      }
    }

    val myOption = MySome(123)
    println(MyOptionFunctor.map2(myOption)(v => v + 10))
    println(MyOptionFunctor.map2(MyNone.asInstanceOf[MyOption[Int]])(v => v + 10))

  }
}