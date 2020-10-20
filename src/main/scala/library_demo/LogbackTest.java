package library_demo;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);

        //打印logback内部状态  找的是哪个配置
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);

        logger.debug("this is a debug msg");
        logger.debug("hi, welcome {}, today is {}", "admin", "Sunday");


        // logger也会层级关系
        // 命名为 io.beansoft 的 logger，是命名为 io.beansoft.logback 的 logger 的父亲，
        // 是命名为 io.beansoft.logback.demo 的 logger 的祖先
        // logback内部维护一个root logger 是所有logger的祖先
//      //
//        System.out.println(((ch.qos.logback.classic.Logger)LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)).getLevel());


        //这里强转为了设置level
        ch.qos.logback.classic.Logger logger1 =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.foo");

        logger1.setLevel(Level.INFO);

        //子logger
        Logger logger11 = LoggerFactory.getLogger("com.foo.Bar");

        logger1.warn("can be printed because WARN > INFO");

        logger1.debug("can not be printed because DEBUG < INFO");

        logger11.info("can be printed because INFO >=  INFO");
        logger11.warn("can be printed because WARN > INFO");

        logger11.debug("can not be printed because DEBUG < INFO");
        logger11.error("can not be printed because Error > INFO");
    }
}
