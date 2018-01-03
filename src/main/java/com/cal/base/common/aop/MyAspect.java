package com.cal.base.common.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cal.base.common.annotation.SystemActionLog;
import com.cal.base.common.exception.CommonException;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 声明切面
 * @AspectJ风格的声明切面非常简单，使用@Aspect注解进行声明(这个是第2步)
 * @AspectJ风格的声明通知也支持5种通知类型
 * 然后将该切面声明为Bean后(使用注解@Component声明)，Spring就能自动识别并进行AOP方面的配置(这个是第3步)
 * @author andyc
 * @since 2018-1-1
 */
@Aspect
@Component
public class MyAspect {
	// 日志框架待续
    // private Logger logger = LoggerFactory.getLogger(getClass()); 
	private Logger logger = Logger.getLogger(getClass()); 
	
	/**
	 * 声明切入点(这个是第4步)
	 * @AspectJ风格的命名切入点使用org.aspectj.lang.annotation包下的@Pointcut+方法（方法必须是返回void类型）实现。
	 * @Pointcut(value="切入点表达式", argNames = "参数名列表") 
	 * value：指定切入点表达式；argNames：指定命名切入点方法参数列表参数名字，可以有多个用“,”分隔，这些参数将传递给通知方法同名的参数，
	 * 同时比如切入点表达式“args(param)”将匹配参数类型为命名切入点方法同名参数指定的参数类型。
	 * @param params
	 */
	@Pointcut(value="execution(* com.cal.base.common.aop.TestClass.test(..)) && args(param,param2)", argNames="param,param2")
	public void pointCutName(String params,String params2) {
	}
	
	@Pointcut("@annotation(com.cal.base.common.annotation.SystemActionLog)")
	public void actionPointCut() {
	}
	
	@Before(value="actionPointCut()")
	public void before(JoinPoint joinPoint) {
		// RequestAttributes 
		// RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		// 获取request
		// HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();    
		 //请求的浏览器    
       //  UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		logger.info("注解SystemActionLog内容:"+ getAnnotation(joinPoint) + "调用类: " + joinPoint.getTarget().getClass().getName() + " 调用方法:" + joinPoint.getSignature().getName() + " 调用参数值:" + Arrays.toString(joinPoint.getArgs()));
	}
	/**
	 * 前置通知：使用org.aspectj.lang.annotation 包下的@Before注解声明；(这个是第5步)
	 * @Before(value = "切入点表达式或命名切入点", argNames = "参数列表参数名")  
	 * 通知方法会在目标方法调用之前执行
	 * @param before
	 * @param before2
	 */
//	@Before(value="pointCutName(p,p2)", argNames="p,p2")
	public void before(String before,String before2) {
		System.out.println("before............" + before + before2);
	}
	
	/**
	 * 后置返回通知：使用org.aspectj.lang.annotation 包下的@AfterReturning注解声明；(这个是第5步)
	 * @AfterReturning(  
	 *  value="切入点表达式或命名切入点",  
	 *	pointcut="切入点表达式或命名切入点",注意：同样是指定切入点表达式或命名切入点，如果指定了将覆盖value属性指定的，pointcut具有高优先级； 
	 *  argNames="参数列表参数名",  
	 *	returning="返回值对应参数名") 
	 */
//	@AfterReturning(value="execution(* com.cal.base.common.aop.TestClass.test(..))", argNames="afterReturning", returning="afterReturning")
	public void afterReturning(String afterReturning) {
		System.out.println("afterReturning............" + afterReturning);
	}
	
	/**
	 * 后置异常通知：使用org.aspectj.lang.annotation 包下的@AfterThrowing注解声明；(这个是第5步)
	 * @AfterThrowing (
	 * value="切入点表达式或命名切入点",  
	 * pointcut="切入点表达式或命名切入点",注意：同样是指定切入点表达式或命名切入点，如果指定了将覆盖value属性指定的，pointcut具有高优先级；
	 * argNames="参数列表参数名",  
	 * throwing="异常对应参数名")  
	 * @param exception
	 */
//	@AfterThrowing(value="execution(* com.cal.base.common.aop.TestClass.test(..))", argNames="exception", throwing="exception")  
	public void afterThrowingAdvice(Exception exception) {  
	    System.out.println("afterThrowingAdvice........" + exception);  
	}  

	/**
	 * 后置最终通知：使用org.aspectj.lang.annotation 包下的@After注解声明；(这个是第5步)
	 * @After (  
	 * value="切入点表达式或命名切入点", 
	 * argNames="参数列表参数名")  
	 */
//	@After(value="execution(* com.cal.base.common.aop.TestClass.test(..))", argNames="after")
	public void after() {
		System.out.println("after.......");
	}
	
	/**
	 * 环绕通知：使用org.aspectj.lang.annotation 包下的@Around注解声明；(这个是第5步)
	 * @Around (  
	 * value="切入点表达式或命名切入点",
	 * argNames="参数列表参数名")  
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
//	@Around(value="execution(* com.cal.base.common.aop.TestClass.test(..))")  
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {  
	    System.out.println("===========around before advice");  
	    Object retVal = pjp.proceed(new Object[] {"replace"});  
	    System.out.println("===========around after advice");  
	    System.out.println("方法的返回结果是:" + retVal);
	    return retVal;  
	}  
	
	/**  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     @SuppressWarnings("rawtypes")
	private String getAnnotation(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getName(); // 目标对象类的完全限定名   
        String targetMethodName = joinPoint.getSignature().getName(); // 切点名字
        Object[] args = joinPoint.getArgs(); // 切点参数值数组,当没有参数时数组为[],不会时null
        int argsLength = args.length;
        Object[] argsType = new Object[argsLength];// 参数类型数组
        for (int i = 0; i < argsLength; i++) {
        	argsType[i] = args[i].getClass().getTypeName();
        }
		Class targetClass = null;
		try {
			targetClass = Class.forName(targetName);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new CommonException("反射加载类出错了，找不到:"+ targetName);
		}    
        Method[] methods =  targetClass.getDeclaredMethods(); // 得到目标对象的所有方法对象(包括继承的) 
        String desc = "";    
        for (Method method : methods) {    
        	// 特别要小心方法的重载
            if (method.getName().equals(targetMethodName)) {// 1.方法名字相等   
                Class[] clazzs = method.getParameterTypes(); 
                if (clazzs.length == argsType.length) {// 2.参数个数相等
                	boolean flag = true;
                	// 3.每个位置的参数类型相等
                	for (int j = 0; j < clazzs.length; j++) {
                		if (clazzs[j].toString().toUpperCase().indexOf((argsType[j].toString().toUpperCase())) == -1 
                				&& argsType[j].toString().toUpperCase().indexOf((clazzs[j].toString().toUpperCase())) == -1) {
                			flag = false;
                			break;
                		}
                	}
                	if (flag) {
                		 desc = method.getAnnotation(SystemActionLog.class).desc();    
                         break;
                	}
                }    
            }    
        }    
        return desc;    
    }    
}
