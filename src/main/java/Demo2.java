import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Demo2 {
    public static void main(String[] args) throws UnknownHostException {
//        ArrayList<String> cates = new ArrayList<>();
//        cates.add("a");
//        cates.add("b");
//        cates.add("c");
//
//        for (int i = 0; i < cates.toArray(new String[cates.size()]).length; i++) {
//            System.out.println(cates.toArray(new String[cates.size()])[i]);
//        }

        System.out.println(Inet4Address.getLocalHost());
    }
}
