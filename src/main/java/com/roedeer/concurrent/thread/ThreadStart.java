package com.roedeer.concurrent.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description 定制Thread类
 * @Author Roedeer
 * @Date 1/25/2019 9:54 PM
 **/
public class ThreadStart extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public ThreadStart(Runnable runnable) {
        this(runnable, DEFAULT_NAME);
    }

    public ThreadStart(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e);
                    }
                }
        );
    }

    public void run() {
        //复制debug标志以确保一致的值
        boolean debug = debugLifecycle;
        if (debug)
            log.log(Level.FINE, "Create " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug)
                log.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static int getThreadsCreated() { return created.get(); }
    public static int getThreadAlive() { return alive.get(); }
    public static boolean getDebug() { return debugLifecycle; }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }
}
