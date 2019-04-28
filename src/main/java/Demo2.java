import java.util.ArrayList;

public class Demo2 {
    public static void main(String[] args) {
        ArrayList<String> cates = new ArrayList<>();
        cates.add("a");
        cates.add("b");
        cates.add("c");

        for (int i = 0; i < cates.toArray(new String[cates.size()]).length; i++) {
            System.out.println(cates.toArray(new String[cates.size()])[i]);
        }
    }
}
