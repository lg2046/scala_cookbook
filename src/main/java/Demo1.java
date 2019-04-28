import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Demo1 {
    public static void main(String[] args) {

        Boolean a = false;

        System.out.println(a.equals(false));


        Random r = new Random();
        r.setSeed(0);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            strings.add(geneRandomLabel(new String[]{"a", "b", "c"}, new double[]{1, 2, 3}, r));
        }

        Map<String, Integer> stringIntegerHashMap = new HashMap<>();
        for (int i = 0; i < strings.size(); i++) {
            if (stringIntegerHashMap.get(strings.get(i)) == null) {
                stringIntegerHashMap.put(strings.get(i), 1);
            } else {
                stringIntegerHashMap.put(strings.get(i), stringIntegerHashMap.get(strings.get(i)) + 1);
            }
        }

        stringIntegerHashMap.forEach((k, v) -> System.out.println(k + ":" + v));

        System.out.println(0.0f / 0.0f);
    }

    public static String geneRandomLabel(String[] labels, double[] ws, Random r) {
        double[] ws_cum = new double[ws.length];
        for (int i = 0; i < ws.length; i++) {
            if (i == 0) {
                ws_cum[i] = ws[i];
            } else {
                ws_cum[i] = ws[i] + ws_cum[i - 1];
            }
        }

        double max = ws_cum[ws_cum.length - 1];

        double genVal = r.nextDouble() * max;

        //判断genVal的位置
        for (int i = 0; i < ws_cum.length; i++) {
            if (genVal <= ws_cum[i]) {
                return labels[i];
            }
        }

        return labels[0]; //just for nothing

    }
}
