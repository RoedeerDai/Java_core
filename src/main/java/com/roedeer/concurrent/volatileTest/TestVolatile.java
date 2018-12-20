package com.roedeer.concurrent.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当一个变量被 volatile 修饰时，任何线程对它的写操作都会立即刷新到主内存中，
 * 并且会强制让缓存了该变量的线程中的数据清空，必须从主内存重新读取最新数据
 *
 * 使用volatile修饰基本数据内存不能保证原子性,因为numb++的操作不是原子的,volatile保证内存可见性
 * 可以使用synchronize或是锁的方式来保证原子性
 *
 * volatile可以防止JVM进行指令优化重排
 */
public class TestVolatile {

	public static volatile int numb = 0;
	public static AtomicInteger inc = new AtomicInteger();

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 1000; i++) {
						numb++;
						inc.getAndIncrement();
					}
				}
			}).start();

		}
		
		Thread.sleep(2000);
		System.out.println(numb + ";" + inc);
	}

}
