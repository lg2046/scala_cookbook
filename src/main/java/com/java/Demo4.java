package com.java;

public class Demo4 {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> localName = new ThreadLocal<>();
        localName.set("a");
        System.out.println(localName.get());

        new Thread(() -> {
            System.out.println(localName.get());
        }).start();

        Thread.sleep(10);
    }
}
