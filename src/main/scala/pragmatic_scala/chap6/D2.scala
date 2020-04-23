package pragmatic_scala.chap6


trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Monad[F[_]] {
  def fmap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

class A

object D2 {
  def main(args: Array[String]): Unit = {


    val threadLocalInstance = new ThreadLocal[A] {
      override def initialValue(): A = new A
    }

    val optionFunctor = new Functor[Option] {
      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
        case Some(a) => Some(f(a))
        case None => None
      }
    }

    new Thread(() => {
      println(Thread.currentThread())
      println(threadLocalInstance.get())
      println(threadLocalInstance.get())
      println(threadLocalInstance.get())
    }).start()

    Thread.sleep(1000)
    new Thread(() => {
      println(Thread.currentThread())
      println(threadLocalInstance.get())
      println(threadLocalInstance.get())
      println(threadLocalInstance.get())
    }).start()

    println(optionFunctor.map(Some(123))(_ + 1))

    println(f(List("google", "f", "haha"): _*))

  }

  def f(a: String*) = a.length
}
