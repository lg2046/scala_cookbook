package com.java;

import java.util.Arrays;
import java.util.List;

public class Demo3 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        list.forEach(System.out::println);
    }
}