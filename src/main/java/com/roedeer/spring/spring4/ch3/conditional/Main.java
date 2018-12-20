package com.roedeer.spring.spring4.ch3.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过实现condition接口，重写match方法来构造判断条件
 * Windows下输出dir，linux输出ls
 */
public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConditionConifg.class);
		
		ListService listService = context.getBean(ListService.class);
		
		
		System.out.println(context.getEnvironment().getProperty("os.name") 
				+ "系统下的列表命令为: "
				+ listService.showListCmd());
		
		context.close();
	}
}
