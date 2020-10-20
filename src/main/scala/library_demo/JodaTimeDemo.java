package library_demo;

import org.joda.time.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JodaTimeDemo {
    public static void main(String[] args) {
        DateTime date1 = new DateTime(2000, 1, 1, 0, 0, 0);
        DateTime date2 = date1.plusDays(90);
        System.out.println(date1.plusDays(45).plusMonths(1).dayOfWeek()
                .withMaximumValue().toString("yyy/MM/dd HH:mm:ss"));

        /** 1: 创建joda对象 */
        // 1: 不指定时区会默认设置为运行代码的机器所在的时区
        DateTime d1 = new DateTime(); //当前时间
        System.out.println("default: " + d1);

        // 2:指定时间
        System.out.println("use spec number: " + new DateTime(2000, 1, 1, 0, 0, 0));

        // 3: 从经历的毫秒数初始
        System.out.println("use millSeconds: " + new DateTime(new Date().getTime()));

        // 4: 或者直接传递java的日期或者另一个joda时间
        System.out.println("use java date: " + new DateTime(new Date()));
        System.out.println("use joda date: " + new DateTime(date1));

        // 5: 或者是一个格式化的字符串
        // 没有时区部分都是本地时区
        System.out.println("use string: " + new DateTime("2019-11-13"));
        System.out.println("use string: " + new DateTime("2019-11-13T11:37:20"));
        System.out.println("use string: " + new DateTime("2019-11-13T11:37:20.016+08:00"));


        // 只需要日期或者是时间的时候
        System.out.println(new LocalDate(2009, 9, 6));
        System.out.println(new LocalTime(13, 30, 26, 0));

        /** 2: 获取日期相关属性 */
        System.out.println("year: " + d1.getYear());
        System.out.println("getMonthOfYear: " + d1.getMonthOfYear()); //与月份名一致
        System.out.println("getDayOfMonth: " + d1.getDayOfMonth()); //与日期一致
        System.out.println("getDayOfWeek: " + d1.getDayOfWeek()); //与当前星期几一致 星期天为7
        System.out.println("getHourOfDay: " + d1.getHourOfDay());
        System.out.println("getMinuteOfHour: " + d1.getMinuteOfHour());
        System.out.println("getSecondOfMinute: " + d1.getSecondOfMinute());
        System.out.println("getMillisOfSecond: " + d1.getMillisOfSecond());

        //还有
        //        d1.getDayOfYear
        //        d1.getMinuteOfDay
        //        d1.getSecondOfDay
        //        d1.getMillisOfDay
        System.out.println(DateTimeConstants.SUNDAY);// 7

        /** 3: 转成java date */
        System.out.println(d1.toDate());
        System.out.println(new Date(d1.getMillis()));

        //转成java calendar
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(d1.getMillis());
        System.out.println(c1);

        //直接通过实例方法转
        System.out.println(d1.toCalendar(Locale.getDefault()));

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
        System.out.println(d1.toLocalDate().minusMonths(1).dayOfMonth().withMaximumValue());
        System.out.println(d1.getMonthOfYear());

        //今年6月第一天后加6天
        System.out.println(new LocalDate()
                .monthOfYear()
                .setCopy(6)
                .dayOfMonth()
                .withMinimumValue()
                .plusDays(6)
        );

        //当前星期天加一天 即下个星期一
        System.out.println(new LocalDate()
                .dayOfWeek()
                .setCopy(DateTimeConstants.SUNDAY)
                .plusDays(1)
        );
        //取下个星期一也可以这样
        System.out.println(new LocalDate()
                .plusWeeks(1).
                        dayOfWeek().
                        withMinimumValue());


        //两个日期的计算
        System.out.println("----two date diff----");
        DateTime d2 = d1.minusDays(1).plusHours(1).plusMinutes(1);
        //差的毫秒数 小的在前大的在后 返回正数
        System.out.println(new Duration(d2, d1).getStandardDays()); //0
        System.out.println(new Duration(d2, d1).getStandardHours()); //22
        System.out.println(new Duration(d2, d1).getStandardMinutes()); //1379
        System.out.println(new Duration(d2, d1).getStandardSeconds()); //82740
        System.out.println(new Duration(d2, d1).getMillis());//System.out.println(new Duration(d2, d1).getMillis) //

        //差的区的整体展示 上面的是一个区间换算成各个单位的计量 相互间没有关系
        //即整体差0天22个小时59分钟
        System.out.println(new Period(d2, d1).getDays()); //0
        System.out.println(new Period(d2, d1).getHours()); //22
        System.out.println(new Period(d2, d1).getMinutes()); //59
        System.out.println(new Period(d2, d1).getSeconds()); //0
        System.out.println(new Period(d2, d1).getMillis()); //0

        Interval i = new Interval(d2, d1);
        System.out.println(i.contains(d2.plusMinutes(1))); //true
        System.out.println(i.contains(d2.minusMinutes(1))); //false

        //日期比较
        System.out.println(d1.isAfterNow());
        System.out.println(d2.isBeforeNow());
        System.out.println(d1.isEqualNow());

        System.out.println(d1.isAfter(d2));
        System.out.println(d2.isBefore(d1));
        System.out.println(d1.isEqual(d2));

        /** 5: 格式化时间 */
        System.out.println("--format--");
        DateTime dateTime = DateTime.now();
        System.out.println(dateTime.toString("yyyy-dd-MM HH:mm:ss"));
        System.out.println(dateTime.toString("yyyy-dd-MM HH:mm:ss.SSSa"));
        System.out.println(dateTime.toString("dd-MM-yyyy HH:mm:ss"));
        System.out.println(dateTime.toString("EEEE dd MMMM, yyyy HH:mm:ssa"));
        System.out.println(dateTime.toString("MM/dd/yyyy HH:mm ZZZZ"));
        System.out.println(dateTime.toString("MM/dd/yyyy HH:mm Z"));

        System.out.println("今天凌晨:");
        System.out.println(new DateTime(new LocalDate().toDate()));
        System.out.println(new DateTime());

        //是否是闰年
        System.out.println(d1.year().isLeap());
    }
}
