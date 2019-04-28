package CookBook.c14_misc


case class Foo()

case class Boo()

object Demo1 {

  implicit def boo2foo(a: Boo): Foo = Foo()

  def main(args: Array[String]): Unit = {
    1 -> 2

    m1(Boo())

    M.m(List(1, 2, 3))
    M.m(List("a", "b", "c"))
  }

  def m1(foo: Foo): Unit = println(foo)
}

object M {

  implicit object IntMarker

  implicit object StringMarker

  //  类型参数删除时 这两个方法也会被为是不同的 会正常工作
  def m(seq: Seq[Int])(implicit i: IntMarker.type): Unit = println(s"Seq[Int] $seq")

  def m(seq: Seq[String])(implicit i: StringMarker.type): Unit = println(s"Seq[String] $seq")
}