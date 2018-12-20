package com.roedeer.concurrent.threadCommunication;

/**
 * @Description 线程通信的方式,通过调用等待wait()和notify()来进行通信
 *              例子为两个线程交替打印奇数偶数
 *
 * @Author Roedeer
 * @Date 12/18/2018 11:09 AM
 **/
public class TwoThreadWaitNotify {

    private int start = 1;

    private boolean flag = false;

    /**
     * 线程 A 和线程 B 都对同一个对象 TwoThreadWaitNotify.class 获取锁, A 线程调用了同步对象的 wait() 方法释放了锁并进入 WAITING 状态
     * B 线程调用了 notify() 方法，这样 A 线程收到通知之后就可以从 wait() 方法中返回
     * 这里利用了 TwoThreadWaitNotify.class 对象完成了通信。
     * 有一些需要注意:
         wait() 、notify()、notifyAll() 调用的前提都是获得了对象的锁(也可称为对象监视器)。
         调用 wait() 方法后线程会释放锁，进入 WAITING 状态，该线程也会被移动到等待队列中。
         调用 notify() 方法会将等待队列中的线程移动到同步队列中，线程状态也会更新为 BLOCKED
         从 wait() 方法返回的前提是调用 notify() 方法的线程释放锁，wait() 方法的线程获得锁。
     * @param args
     */
    public static void main(String[] args) {
        TwoThreadWaitNotify twoThread = new TwoThreadWaitNotify();

        Thread t1 = new Thread(new EvenNum(twoThread));
        t1.setName("A");

        Thread t2 = new Thread(new OddNum(twoThread));
        t2.setName("B");

        t1.start();
        t2.start();
    }

    /**
     * 偶数线程
     */
    public static class EvenNum implements Runnable {

        private TwoThreadWaitNotify number;

        public EvenNum(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("偶数线程抢到锁了");
                    if (number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+偶数" + number.start);
                        number.start++;

                        number.flag = false;
                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 奇数线程
     */
    public static class OddNum implements Runnable {

        private TwoThreadWaitNotify number;

        public OddNum(TwoThreadWaitNotify number) {
            this.number = number;
        }

        @Override
        public void run() {
            while (number.start <= 100) {
                synchronized (TwoThreadWaitNotify.class) {
                    System.out.println("奇数线程抢到锁了");
                    if (!number.flag) {
                        System.out.println(Thread.currentThread().getName() + "+-+奇数" + number.start);
                        number.start++;

                        number.flag = true;

                        TwoThreadWaitNotify.class.notify();
                    } else {
                        try {
                            TwoThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
