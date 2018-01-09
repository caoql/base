/**
 * 一.拦截器使用的场景:原则上是处理所有的共性问题
 * 	1.解决乱码问题
 * 	2.解决权限验证问题
 * 	3.日志记录
 *  4.性能监控
 * 二.与过滤器的异同:
 * 		过滤器Filter依赖于Servlet容器，基于回调函数，过滤范围大
 * 		拦截器Interceptor依赖于框架容器，基于反射机制，只过滤请求
 * 三.提示:
 * 		推荐能使用servlet规范中的过滤器Filter实现的功能就用Filter实现,因为HandlerInteceptor只有在Spring Web MVC环境下
 * 		才能使用,因此Filter是最通用的,最先应该使用的(如登录这种拦截器最好使用Filter来实现)
 */
/**
 * @author andyc
 *
 */
package com.cal.base.common.interceptor;