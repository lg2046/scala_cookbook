import java.util.*;

public class FZCDemo {
    public static void main(String[] args) {
        Map<String, String> map1 = new HashMap<>();
        map1.put("a", "1.1");
        map1.put("b", "1.2");
        map1.put("c", "1.3");
        Map<String, String> map2 = new HashMap<>();
        map2.put("b", "1.1");
        map2.put("c", "1.2");
        map2.put("d", "1.3");
        map2.put("e", "1.4");

        Random rand = new Random();
        String rands = map1.keySet().toArray(new String[0])[rand.nextInt(map1.size())];

        System.out.println(rands);

        mergeMap(map1, map2, true).forEach((k, v) -> System.out.println(k + ":" + v));

        ArrayList<Double> list = new ArrayList<>();
        list.add(1.0);
        list.add(2.0);
        list.add(3.0);
        list.add(4.0);
        list.add(5.0);
        topK(list, 6).forEach(System.out::println);

        System.out.println(list.subList(5 - 3, 5));

        ArrayList<String> list2 = new ArrayList<>();
        System.out.println(countMax(list2));

        Random random = new Random();
        random.setSeed(0);

        Map<String, Double> sampleMap = new HashMap<>();
        sampleMap.put("a", 0.1);
        sampleMap.put("b", 0.2);
        sampleMap.put("c", 0.3);

        for (int i = 0; i < 60; i++) {
            System.out.println(generateSampleCate(sampleMap, random));
        }
    }

    //左边的占优势
    static Map<String, Double> mergeMap(Map<String, String> map1, Map<String, String> map2, boolean isAdded) {
        Map<String, Double> resultMap = new HashMap<>();

        for (Map.Entry<String, String> kv1 : map1.entrySet()) {
            String k1 = kv1.getKey();
            double v1 = Double.parseDouble(kv1.getValue());

            if (map2.containsKey(k1)) {
                if (isAdded) {
                    v1 = v1 + Double.parseDouble(map2.get(k1));
                }

            }
            resultMap.put(k1, v1);
        }

        for (Map.Entry<String, String> kv2 : map2.entrySet()) {
            String k2 = kv2.getKey();
            Double v2 = Double.parseDouble(kv2.getValue());

            if (!map1.containsKey(k2)) {
                resultMap.put(k2, v2);
            }

        }
        return resultMap;
    }

    static <T> List<T> topK(List<T> list, Integer k) {
        ArrayList<T> retList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (retList.size() < k) {
                retList.add(list.get(i));
            }
        }
        return retList;
    }

    static String countMax(List<String> list) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String v = list.get(i);
            if (map.containsKey(v)) {
                map.put(v, map.get(v) + 1);
            } else {
                map.put(v, 1);
            }
        }
        if (map.size() > 0) {
            String maxKey = "";
            Integer maxV = 0;

            for (Map.Entry<String, Integer> kv : map.entrySet()) {
                if (kv.getValue() > maxV) {
                    maxKey = kv.getKey();
                    maxV = kv.getValue();
                }
            }

            return maxKey + "_" + maxV;
        } else {
            return null;
        }
    }

    static String generateSampleCate(Map<String, Double> map, Random random) {
        int size = map.size();
        double total = map.values().stream().mapToDouble(v -> v).sum();
        String[] label = new String[size];
        double[] accum = new double[size];

        int currentInd = 0;
        for (Map.Entry<String, Double> kv : map.entrySet()) {
            label[currentInd] = kv.getKey();

            if (currentInd > 0) {
                accum[currentInd] = accum[currentInd - 1] + kv.getValue();
            } else {
                accum[currentInd] = kv.getValue();
            }
            currentInd += 1;
        }

        double sampleVal = random.nextDouble() * total;
        for (int i = 0; i < size; i++) {
            if (sampleVal <= accum[i]) {
                return label[i];
            }
        }

        return null;
    }
}
