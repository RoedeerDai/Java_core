package com.roedeer.concurrent.volatileTest;

import java.util.concurrent.atomic.AtomicInteger;

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
