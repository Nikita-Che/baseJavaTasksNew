package com.urise.webapp;

public class MainDEADLOCK {
    public static final Object objectForLocking = new Object();
    public static final Object objectForLocking2 = new Object();
    private static int fistCount = 0;
    private static int secondCount = 0;

    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        MyThread myThread1 = new MyThread();
        myThread.start();
        myThread1.start();

        System.out.println(fistCount);
        System.out.println(secondCount);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            method1();
            method2();
        }
    }

    private static void creatingDeadThreads() {
        for (int i = 0; i < 2; i++) {
            MyThread myThread = new MyThread();
            myThread.start();
        }
    }

    private static void deadLocking(Object objectForLocking, long millis, Object objectForLocking2) {
        synchronized (objectForLocking) {
            sleeper(millis);
            synchronized (objectForLocking2) {
                counting();
            }
        }
    }

    public static void method1() {
        synchronized (objectForLocking) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (objectForLocking2) {
                fistCount++;
                secondCount++;
            }
        }
    }

    public static void method2() {
        synchronized (objectForLocking2) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (objectForLocking) {
                secondCount++;
                fistCount++;
            }
        }
    }
    private static void counting() {
        fistCount++;
        secondCount++;
    }

    private static void sleeper(Long millis)  {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}