package com.cal.base.common.aop;

import org.springframework.stereotype.Component;

import com.cal.base.common.annotation.SystemActionLog;

@Component
public class TestClass {
	
	public String test(int a,String b) {
		System.out.println("test:" + a + b);
		return "aop";
	}
	
	@SystemActionLog(desc="这是一个自定义注解的测试")
	public String test(String a,Boolean b) {
		System.out.println("test:" + a);
		return "aop";
	}
	
	public String test(boolean a,String b) {
		System.out.println("test:" + a + b);
		return "aop";
	}
	
	
}
