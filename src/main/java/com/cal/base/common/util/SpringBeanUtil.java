package com.cal.base.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class SpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext ac = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName){
		return (T) ac.getBean(beanName);
	}
}
