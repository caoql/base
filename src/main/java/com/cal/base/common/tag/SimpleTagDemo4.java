package com.cal.base.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 控制整个jsp页面是否执行 SUN公司针对SimpleTag接口提供了一个默认的实现类SimpleTagSupport，
 * SimpleTagSupport类中实现了SimpleTag接口的所有方法，
 * 因此我们可以编写一个类继承SimpleTagSupport类，然后根据业务需要再重写doTag方法。
 * 
 * @author andyc 2018-3-14
 * 
 */
public class SimpleTagDemo4 extends SimpleTagSupport {

	/**
	 * 简单标签使用这个方法就可以完成所有的业务逻辑 在方法体内通过操作JspFragment对象，就可以实现是否执行、迭代、修改标签体的目的。
	 * 在doTag方法抛出SkipPageException异常即可，jsp收到这个异常，将忽略标签余下jsp页面的执行。
	 */
	@Override
	public void doTag() throws JspException, IOException {
		//抛出一个SkipPageException异常就可以控制标签余下的Jsp不执行
        throw new SkipPageException();
	}

}
