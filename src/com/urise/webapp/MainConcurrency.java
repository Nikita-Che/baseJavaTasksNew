package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    //    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
                throw new IllegalStateException();
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

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(sdf.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
//            completionService.poll();
//            System.out.println(future.isDone());
//            System.out.println(future.get());
        }


//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
//        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread1 = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    mainConcurrency.inc();
//                }
//                latch.countDown();
//            });
//            thread1.start();
//            threads.add(thread1);
//        }

//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        Thread.sleep(500);
        latch.await();
        executorService.shutdown();
        System.out.println(counter);
        System.out.println(atomicInteger);
        System.out.println(MainConcurrency.counter);
        System.out.println(MainConcurrency.atomicInteger);

        final String lock1 = "lock1";
        final String lock2 = "lock2";
//        deadLock(lock1, lock2);
//        deadLock(lock2, lock1);
    }

    private static void deadLock(String lock2, String lock1) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private void inc() {
        atomicInteger.incrementAndGet();
    }

//    private void inc() {
//        lock.lock();
//        try {
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//    }

//    private synchronized void inc() {
//        counter++;
//    }

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
