package library_demo

import java.util.{Calendar, Date, Locale}

import org.joda.time.{DateTime, DateTimeConstants, Duration, Interval, LocalDate, LocalTime, Period}

object JodaTimeDemo {
  def main(args: Array[String]): Unit = {
    val date1 = new DateTime(2000, 1, 1, 0, 0, 0)
    val date2 = date1.plusDays(90)
    println(date1.plusDays(45).plusMonths(1).dayOfWeek().withMaximumValue().toString("yyy/MM/dd HH:mm:ss"))

    /** 1: 创建joda对象 */
    // 1: 不指定时区会默认设置为运行代码的机器所在的时区
    val d1 = new DateTime() //当前时间
    println("default: ", d1)
    // 2:指定时间
    println("use spec number: ", new DateTime(2000, 1, 1, 0, 0, 0))
    // 3: 从经历的毫秒数初始
    println("use millseconds: ", new DateTime(new Date().getTime))
    // 4: 或者直接传递java的日期或者另一个joda时间
    println("use java date: ", new DateTime(new Date()))
    println("use joda date: ", new DateTime(date1))
    // 5: 或者是一个格式化的字符串
    // 没有时区部分都是本地时区
    println("use string: ", new DateTime("2019-11-13"))
    println("use string: ", new DateTime("2019-11-13T11:37:20"))
    println("use string: ", new DateTime("2019-11-13T11:37:20.016+08:00"))


    // 只需要日期或者是时间的时候
    println(new LocalDate(2009, 9, 6))
    println(new LocalTime(13, 30, 26, 0))

    /** 2: 获取日期相关属性 */
    println(s"year: ${d1.getYear}")
    println(s"getMonthOfYear: ${d1.getMonthOfYear}") //与月份名一致
    println(s"getDayOfMonth: ${d1.getDayOfMonth}") //与日期一致
    println(s"getDayOfWeek: ${d1.getDayOfWeek}") //与当前星期几一致 星期天为7
    println(s"getHourOfDay: ${d1.getHourOfDay}")
    println(s"getMinuteOfHour: ${d1.getMinuteOfHour}")
    println(s"getSecondOfMinute: ${d1.getSecondOfMinute}")
    println(s"getMillisOfSecond: ${d1.getMillisOfSecond}")
    //还有
    d1.getDayOfYear
    d1.getMinuteOfDay
    d1.getSecondOfDay
    d1.getMillisOfDay
    println(DateTimeConstants.SUNDAY) // 7

    /** 3: 转成java date */
    println(d1.toDate)
    println(new Date(d1.getMillis))
    //转成java calendar
    val c1 = Calendar.getInstance()
    c1.setTimeInMillis(d1.getMillis)
    println(c1)
    //直接通过实例方法转
    println(d1.toCalendar(Locale.getDefault()))

    /** 4: 日期计算 */
    //计算上一个月的最后一天  只关心天  所以可以转成localDate
    //dayOfMonth返回Property
    //一般使用有:
    // yearOfCentury
    // dayOfYear
    // monthOfYear
    // dayOfMonth
    // dayOfWeek
    // year
    println(d1.toLocalDate.minusMonths(1).dayOfMonth().withMaximumValue())
    println(d1.getMonthOfYear)

    //今年6月第一天后加6天
    println(new LocalDate()
      .monthOfYear()
      .setCopy(6)
      .dayOfMonth()
      .withMinimumValue()
      .plusDays(6)
    )
    //当前星期天加一天 即下个星期一
    println(new LocalDate()
      .dayOfWeek()
      .setCopy(DateTimeConstants.SUNDAY)
      .plusDays(1)
    )
    //取下个星期一也可以这样
    println(new LocalDate()
      .plusWeeks(1).
      dayOfWeek().
      withMinimumValue())

    //两个日期的计算
    println("----two date diff----")
    val d2 = d1.minusDays(1).plusHours(1).plusMinutes(1)
    //差的毫秒数 小的在前大的在后 返回正数
    println(new Duration(d2, d1).getStandardDays) //0
    println(new Duration(d2, d1).getStandardHours) //22
    println(new Duration(d2, d1).getStandardMinutes) //1379
    println(new Duration(d2, d1).getStandardSeconds) //82740
    println(new Duration(d2, d1).getMillis) //println(new Duration(d2, d1).getMillis) //

    //差的区的整体展示 上面的是一个区间换算成各个单位的计量 相互间没有关系
    //即整体差0天22个小时59分钟
    println(new Period(d2, d1).getDays) //0
    println(new Period(d2, d1).getHours) //22
    println(new Period(d2, d1).getMinutes) //59
    println(new Period(d2, d1).getSeconds) //0
    println(new Period(d2, d1).getMillis) //0

    val i = new Interval(d2, d1)
    println(i.contains(d2.plusMinutes(1))) //true
    println(i.contains(d2.minusMinutes(1))) //false

    //日期比较
    println(d1.isAfterNow)
    println(d2.isBeforeNow)
    println(d1.isEqualNow)

    println(d1.isAfter(d2))
    println(d2.isBefore(d1))
    println(d1.isEqual(d2))

    /** 5: 格式化时间 */
    println("--format--")
    val dateTime: DateTime = DateTime.now
    println(dateTime.toString("yyyy-dd-MM HH:mm:ss"));
    println(dateTime.toString("yyyy-dd-MM HH:mm:ss.SSSa"));
    println(dateTime.toString("dd-MM-yyyy HH:mm:ss"));
    println(dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
    println(dateTime.toString("MM/dd/yyyy HH:mm ZZZZ"));
    println(dateTime.toString("MM/dd/yyyy HH:mm Z"));

    print("今天凌晨:")
    println(new DateTime(new LocalDate().toDate))
    println(new DateTime())

    //是否是闰年
    println(d1.year().isLeap)
  }
}
