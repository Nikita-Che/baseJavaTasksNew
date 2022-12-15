package com.urise.webapp;

public class MainDEADLOCK {
    public static final Object objectForLocking = new Object();
    public static final Object objectForLocking2 = new Object();
    private static int fistCount = 0;
    private static int secondCount = 0;

    public static void main(String[] args) {

        creatingDeadThreads();

        System.out.println(fistCount);
        System.out.println(secondCount);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            deadLocking(objectForLocking, 100L, objectForLocking2);
            deadLocking(objectForLocking2, 110L, objectForLocking);
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
