package library_demo;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigTest {
    public static void main(String[] args) {
        Config conf = ConfigFactory.load();
        System.out.println(conf.getInt("foo.bar"));
        System.out.println(conf.getInt("foo.bar"));
        System.out.println(conf.getString("person.name"));
        // System.out.println(conf.getInt("person.name"));  // cast error

        System.out.println(conf.getString("site_name"));

        System.out.println(ConfigTest.class.getClassLoader());
    }
}
