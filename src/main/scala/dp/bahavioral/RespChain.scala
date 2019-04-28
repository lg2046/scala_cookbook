package dp.bahavioral

case class Money(amount: Int)

trait Dispenser {
  val amount: Int
  val next: Option[Dispenser]

  def dispense(money: Money, result: Array[String] = Array[String]()): Array[String] = {
    //可取当前Money
    if (money.amount >= amount) {
      //取当前金额的数量与剩余
      val notes = money.amount / amount
      val left = money.amount % amount
      val newResult = if (notes > 0) result :+ s"""取 $notes 张 ${amount}元""" else result
      if (left > 0) {
        next.map(_.dispense(Money(left), newResult)).getOrElse(result)
      } else {
        newResult
      }
    } else {
      //取的钱比当前处理金额小，直接下一个
      next.map(_.dispense(money, result)).getOrElse(result)
    }
  }
}

class Dispenser50(val next: Option[Dispenser]) extends Dispenser {
  override val amount = 50
}

class Dispenser20(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 20
}

class Dispenser10(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 10
}

class Dispenser5(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 5
}

class Dispenser1(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 1
}

object RespChain {
  def main(args: Array[String]): Unit = {
    //构造职责链
    val dispenser: Dispenser = {
      val d1 = new Dispenser1(None)
      val d2 = new Dispenser5(Some(d1))
      val d3 = new Dispenser10(Some(d2))
      val d4 = new Dispenser20(Some(d3))
      new Dispenser50(Some(d4))
    }

    val result = dispenser.dispense(Money(37))
    result foreach println
  }
}

//使用Partial + 组合来构建职责键
object PartialFunctionDispenser {
  //一个函数就是一个handler
  def dispense(dispenserAmount: Int): PartialFunction[Money, Money] = {
    case Money(amount) if amount >= dispenserAmount =>
      val notes = amount / dispenserAmount
      val left = amount % dispenserAmount
      println(s"""Dispensing $notes note/s of $dispenserAmount.""")
      Money(left)
    case m@Money(_) => m
  }
}

object PartialDispenseDemo {
  def main(args: Array[String]): Unit = {
    val dispenser = PartialFunctionDispenser.dispense _
    val handles = dispenser(50).andThen(dispenser(20)).andThen(dispenser(10)).andThen(dispenser(5)).andThen(dispenser(1))

    println(handles(Money(137)))
  }
}