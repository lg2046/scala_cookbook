package playground

import java.security.MessageDigest

import com.softwaremill.quicklens._
import org.apache.commons.codec.binary.Hex


case class Country(name: String, code: String)

case class City(name: String, country: Country)

case class Address(number: Int, street: String, city: City)

case class Company(name: String, address: Address)

case class User(name: String, company: Company, address: Address)

object Demo5 {
  def main(args: Array[String]): Unit = {
    //    val a = List(1, 2, 3)
    //    a.foreach(println) // 内部迭代器
    //
    //    val itr = a.iterator
    //    while (itr.hasNext) {
    //      println(itr.next())
    //    }
    //
    //
    //    val uk = Country("United Kingdom", "uk")
    //    val london = City("London", uk)
    //    val buckinghamPalace = Address(1, "Buckingham Palace Road", london)
    //    val castleBuilders = Company("Castle Builders", buckinghamPalace)
    //
    //    val switzerland = Country("Switzerland", "CH")
    //    val geneva = City("geneva", switzerland)
    //    val genevaAddress = Address(1, "Geneva Lake", geneva)
    //    val ivan = User("Ivan", castleBuilders, genevaAddress)
    //
    //    println(ivan.modify(_.company.address.city.country.code).using(_.toUpperCase))
    //    println(ivan)
    //
    //    val squareRoot: PartialFunction[Int, Double] = {
    //      case a if a >= 0 => Math.sqrt(a)
    //    }
    //    println(squareRoot.isDefinedAt(-50))
    //
    //    val run = squareRoot.runWith(println)
    //    println(run(20))

    val start = System.currentTimeMillis()

    val memMd5 = mem(md5)
    for (i <- 1 to 100000000) {
      memMd5("google")
    }

    val end = System.currentTimeMillis()

    println(end - start)

    val map = collection.mutable.HashMap[String, String]()
    map("google") = "google"

    map.get("haha")
  }

  def md5(input: String) = {
    new String(
      Hex.encodeHex(MessageDigest.getInstance("MD5").digest(input.getBytes)))
  }

  def mem[A, B](fn: A => B): A => B = {
    val map = collection.mutable.HashMap[A, B]()
    (x: A) => {
      map.getOrElseUpdate(x, fn(x))
    }
  }
}