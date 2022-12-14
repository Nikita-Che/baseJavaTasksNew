package com.urise.webapp;

public class MainDEADLOCK {
    public static final Object objectForLocking = new Object();
    public static final Object objectForLocking2 = new Object();
    private static int fistCount = 0;
    private static int secondCount = 0;

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                method1();
                method2();
            });
            thread.start();
            Thread thread1 = new Thread(() -> {
                method2();
                method1();
            });
            thread1.start();

        }

        System.out.println(fistCount);
        System.out.println(secondCount);
    }

    public static void method1() {
        synchronized (objectForLocking) {
            synchronized (objectForLocking2) {
                fistCount++;
                secondCount++;
            }
        }
    }

    public static void method2() {
        synchronized (objectForLocking2) {
            synchronized (objectForLocking) {
                secondCount++;
                fistCount++;
            }
        }
    }
}
