package com.cal.base.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 开发带属性的标签 1.在标签处理器中编写每个属性对应的setter方法 2.在TLD文件中描术标签的属性
 * 为自定义标签定义属性时，每个属性都必须按照JavaBean的属性命名方式，在标签处理器中定义属性名对应的setter方法，用来接收
 * JSP页面调用自定义标签时传递进来的属性值。 例如属性url，在标签处理器类中就要定义相应的setUrl(String url)方法。
 * 　　在标签处理器中定义相应的set方法后，JSP引擎在解析执行开始标签前，也就是调用doStartTag方法前，会调用set属性方法，为标签设置属性。
 * 
 * @author andyc 2018-3-14
 * 
 */
public class SimpleTagDemo5 extends SimpleTagSupport {

	/**
	 * 定义标签的属性
	 */
	private int count;

	/**
	 * count属性对应的set方法
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 简单标签使用这个方法就可以完成所有的业务逻辑 在方法体内通过操作JspFragment对象，就可以实现是否执行、迭代、修改标签体的目的。
	 * 通过标签的属性控制标签体的执行次数
	 */
	@Override
	public void doTag() throws JspException, IOException {
		for (int i = 0; i < count; i++) {
			this.getJspBody().invoke(null);
		}
	}

}
