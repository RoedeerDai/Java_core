package com.roedeer.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 观察现象：如果thread-1得到了锁，阻塞......thread-0尝试获取锁，如果拿不到，则可以被中断等待
 * @author
 *
 */
public class MyInterruptibly {
	 private Lock lock = new ReentrantLock();  
	 
	    public static void main(String[] args)  {
	    	MyInterruptibly test = new MyInterruptibly();
	        MyThread thread0 = new MyThread(test);
	        MyThread thread1 = new MyThread(test);
	        thread1.start();
	        thread0.start();
	         
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        thread0.interrupt();
	        System.out.println("=====================");
	    }
	     
	    public void insert(Thread thread) throws InterruptedException {
	        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
	        try {
	            System.out.println(thread.getName()+"得到了锁");
	            long startTime = System.currentTimeMillis();
	            for(    ;     ;) {
	                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
	                    break;
	                //插入数据
	            }
	        }
	        finally {
	            System.out.println(Thread.currentThread().getName()+"执行finally");
	            lock.unlock();
	            System.out.println(thread.getName()+"释放了锁");
	        }  
	    }

	static class MyThread extends Thread {
		private MyInterruptibly test = null;
		public MyThread(MyInterruptibly test) {
			this.test = test;
		}
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
			try {
				test.insert(Thread.currentThread());
			} catch (Exception e) {
				System.out.println(Thread.currentThread().getName()+"被中断");
			}
		}

	}
}
