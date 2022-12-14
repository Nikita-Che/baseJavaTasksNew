package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw  new IllegalStateException();
            }
        };
        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
                    counter++;
                }
            }
        }).start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread.getState());

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread1 = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread1.start();
            threads.add(thread1);
        }

        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//        Thread.sleep(500);
        System.out.println(counter);
        System.out.println(MainConcurrency.counter);
    }

    private synchronized void inc() {
        counter++;
    }

//    private void inc() {
////        double a = Math.sin(12.2);
////        try {
//            synchronized (this) {
//                counter++;
////                wait();
////                notifyAll();
//                //readFile
//            }
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//
//    }

//    private synchronized void inc() {
//        double a = Math.sin(12.2);
//        counter++;
//    }

//    private static void inc() {
//        double a = Math.sin(12.2);
//        synchronized (LOCK) {
//            counter++;
//        }
//    }

//    private static synchronized void inc() {
//        double a = Math.sin(12.2);
//            counter++;
//    }

//    private static void inc() {
//        double a = Math.sin(12.2);
//        Object lock = new Object();
//        synchronized (lock) {
//            counter++;
//        }
//    }

//    private static synchronized void inc() {
//        double a = Math.sin(12.2);
//        counter++;
//    }
}
