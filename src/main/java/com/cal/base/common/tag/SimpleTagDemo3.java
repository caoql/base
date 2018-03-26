package com.cal.base.common.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 修改jsp页面内容输出 SUN公司针对SimpleTag接口提供了一个默认的实现类SimpleTagSupport，
 * SimpleTagSupport类中实现了SimpleTag接口的所有方法，
 * 因此我们可以编写一个类继承SimpleTagSupport类，然后根据业务需要再重写doTag方法。
 * 
 * @author andyc 2018-3-14
 * 
 */
public class SimpleTagDemo3 extends SimpleTagSupport {

	/**
	 * 简单标签使用这个方法就可以完成所有的业务逻辑 在方法体内通过操作JspFragment对象，就可以实现是否执行、迭代、修改标签体的目的。
	 * 　JspFragment.invoke方法是JspFragment最重要的方法，利用这个方法可以控制是否执行和输出标签体的内容、
	 * 是否迭代执行标签体的内容或对标签体的执行结果进行修改后再输出。例如：
	 * 　　在标签处理器中如果没有调用JspFragment.invoke方法，其结果就相当于忽略标签体内容；
	 * 　　在标签处理器中重复调用JspFragment.invoke方法，则标签体内容将会被重复执行；
	 * 　　若想在标签处理器中修改标签体内容，只需在调用invoke方法时指定一个可取出结果数据的输出流对象
	 * （例如StringWriter），让标签体的执行结果输出到该输出流对象中
	 * ，然后从该输出流对象中取出数据进行修改后再输出到目标设备，即可达到修改标签体的目的。
	 */
	@Override
	public void doTag() throws JspException, IOException {
		// 得到代表jsp标签体的JspFragment
		// invoke()方法用于执行JspFragment对象所代表的JSP代码片段，参数out用于指定将JspFragment对象的执行结果写入到哪个输出流对象中，
		// 如果
		// 传递给参数out的值为null，则将执行结果写入到JspContext.getOut()方法返回的输出流对象中。(简而言之，可以理解为写给浏览器)
		JspFragment jspFragment = this.getJspBody();
		StringWriter sw = new StringWriter();
		// 将标签体的内容写入到sw流中
		jspFragment.invoke(sw);
		// 获取sw流缓冲区的内容
		String content = sw.getBuffer().toString();
		content = content.toUpperCase();
		PageContext pageContext = (PageContext) this.getJspContext();
		// 将修改后的content输出到浏览器中
		pageContext.getOut().write(content);
	}

}
