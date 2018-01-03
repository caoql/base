package com.cal.base.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @description 
 *	问题:
 *	我们的拦截器是单例,因此不管用户请求多少次都只有一个拦截器实现,即线程不安全.那我们应该怎么记录时间呢?
 *	解决方案是使用ThreadLocal,它是线程绑定的变量,提供线程局部变量(一个线程一个ThreadLocal,
 *	A线程的ThreadLocal只能看到A线程的ThreadLocal,不能看到B线程的ThreadLocal).
 *	在测试时需要把PerformanceInterceptor放在拦截器链的第一个,这样得到的时间才能是比较精确的.
 * @author andyc
 */
public class PerformanceInterceptor extends HandlerInterceptorAdapter {
	
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * NamedThreadLocal: Spring提供的一个命名的ThreadLocal实现
	 */
	private NamedThreadLocal<Long> threadLocal = new NamedThreadLocal<Long>("threadLocal"); 
	
	/**
	 * 在进入处理器之前记录开始时间，即在拦截器的preHandle记录开始时间
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis(); // 1.开始时间  
		threadLocal.set(beginTime); // 线程绑定变量(该数据只有当前请求的线程可见)  
        return true;//继续流程  
	}

	/**
	 * 在结束请求处理之后记录结束时间,即在拦截器的afterCompletion记录结束实现,并用结束时间-开始时间得到这次请求的处理时间
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long endTime = System.currentTimeMillis(); // 2.结束时间  
        long beginTime = threadLocal.get(); // 得到线程绑定的局部变量(开始时间)  
        long consumeTime = endTime - beginTime; // 3.消耗的时间  
        if (consumeTime > 500) { // 此处认为处理时间超过500毫秒的请求为慢请求  
        	// 记录到日志文件  
        	log.warn(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));
        }
	}
	
}
