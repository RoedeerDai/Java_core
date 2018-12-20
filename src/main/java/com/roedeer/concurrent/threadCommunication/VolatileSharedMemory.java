package com.roedeer.concurrent.threadCommunication;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Description 采用共享内存的方式进行线程通信,主线程关闭 A 线程
 * volatile并不能保证线程安全性
 *
 * @Author Roedeer
 * @Date 12/18/2018 11:44 AM
 **/
public class VolatileSharedMemory implements Runnable {

    /**
     * flag放于主内存中,主线程和A线程都能看到
     * volatile修饰主要是为了内存可见性
     */
    private static volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "正在运行...");
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileSharedMemory volatileSharedMemory = new VolatileSharedMemory();
        new Thread(volatileSharedMemory, "Thread A").start();

        System.out.println("Main 线程正在运行...");

        TimeUnit.MILLISECONDS.sleep(1);

        volatileSharedMemory.stopThread();

        System.out.println("主线程退出了！");
    }

    private void stopThread() {
        flag = false;
    }
}
