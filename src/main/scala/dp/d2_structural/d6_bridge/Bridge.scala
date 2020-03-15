package dp.d2_structural.d6_bridge

abstract class PasswordConverterBase {
  this: Hasher =>
  def convert(password: String): String
}

class SaltPasswordConverterBase(salt: String) extends PasswordConverterBase {
  this: Hasher =>
  def convert(password: String): String = {
    hash(s"$salt:$password")
  }
}

class SimplePasswordConverterBase extends PasswordConverterBase {
  this: Hasher =>
  def convert(password: String): String = {
    hash(password)
  }
}


//使用到的算法发展部分:

import java.security.MessageDigest
import org.apache.commons.codec.binary.Hex

trait Hasher {
  def hash(data: String): String

  protected
  def getDigest(algorithm: String, data: String): MessageDigest = {
    val crypt = MessageDigest.getInstance(algorithm)
    crypt.reset()
    crypt.update(data.getBytes("UTF-8"))
    crypt
  }
}

trait Sha1Hasher extends Hasher {
  override def hash(data: String): String = Hex.encodeHexString(getDigest("SHA-1", data).digest())
}

trait Md5Hasher extends Hasher {
  override def hash(data: String): String = Hex.encodeHexString(getDigest("MD5", data).digest())
}

object Demo extends App {
  val converter = new SimplePasswordConverterBase() with Md5Hasher
  val converter2 = new SaltPasswordConverterBase("salt") with Sha1Hasher
  println(converter.convert("haha"))
  println(converter2.convert("ifky"))
}