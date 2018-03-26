package com.cal.base.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 控制jsp页面内容重复执行
 * SUN公司针对SimpleTag接口提供了一个默认的实现类SimpleTagSupport，
 * SimpleTagSupport类中实现了SimpleTag接口的所有方法，
 * 因此我们可以编写一个类继承SimpleTagSupport类，然后根据业务需要再重写doTag方法。
 * @author andyc 2018-3-14
 * 
 */
public class SimpleTagDemo2 extends SimpleTagSupport {

	/**
	 * 简单标签使用这个方法就可以完成所有的业务逻辑
	 * 在方法体内通过操作JspFragment对象，就可以实现是否执行、迭代、修改标签体的目的。
	 */
	@Override
	public void doTag() throws JspException, IOException {
		 // 得到代表jsp标签体的JspFragment
        JspFragment jspFragment = this.getJspBody();
        for (int i = 0; i < 5; i++) {
            // 将标签体的内容输出到浏览器
            jspFragment.invoke(null);
        }
	}
	
}
