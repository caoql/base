package com.cal.base.common.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * 自定义标签主要用于移除Jsp页面中的java代码。
 * note:JSP引擎遇到自定义标签时，首先创建标签处理器类的实例对象，然后按照JSP规范定义的通信规则依次调用它的方法。
 * 
 * @author andyc 2018-3-14
 */
public class ViewIPTag implements Tag {

	// 接收传递进来的PageContext对象
	private PageContext pageContext;

	// 1.JSP引擎实例化标签处理器后，将调用setPageContext方法将JSP页面的pageContext对象传递给标签处理器，标签处理器以后可以通过这个pageContext对象与JSP页面进行通信。
	@Override
	public void setPageContext(PageContext pc) {
		System.out.println("调用setPageContext()方法");
		this.pageContext = pc;
	}

	// 2.setPageContext方法执行完后，WEB容器接着调用的setParent方法将当前标签的父标签传递给当前标签处理器，如果当前标签没有父标签，则传递给setParent方法的参数值为null。
	@Override
	public void setParent(Tag t) {
		System.out.println("调用setParent()方法,参数为："+ t);
	}

	@Override
	public Tag getParent() {
		System.out.println("调用getParent()方法");
		return null;
	}

	// 3.调用了setPageContext方法和setParent方法之后，WEB容器执行到自定义标签的开始标记时，就会调用标签处理器的doStartTag方法。
	@Override
	public int doStartTag() throws JspException {
		System.out.println("调用doStartTag()方法");
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut();
		String ip = request.getRemoteAddr();
		try {
			// 这里输出的时候会抛出IOException异常
			out.write(ip);
		} catch (IOException e) {
			// 捕获IOException异常后继续抛出
			throw new RuntimeException(e);
		}
		// doStartTag方法执行完后可以向WEB容器返回常量EVAL_BODY_INCLUDE或SKIP_BODY。
		// 如果doStartTag方法返回EVAL_BODY_INCLUDE=1，WEB容器就会接着执行自定义标签的标签体；
		// 如果doStartTag方法返回SKIP_BODY=0，WEB容器就会忽略自定义标签的标签体，直接解释执行自定义标签的结束标记。
		return 0;// 
	}

	// 4.WEB容器执行完自定义标签的标签体后，就会接着去执行自定义标签的结束标记，此时，WEB容器会去调用标签处理器的doEndTag方法。
	@Override
	public int doEndTag() throws JspException {
		System.out.println("调用doEndTag()方法");
		// doEndTag方法执行完后可以向WEB容器返回常量EVAL_PAGE或SKIP_PAGE。
		// 如果doEndTag方法返回常量EVAL_PAGE=6，WEB容器就会接着执行JSP页面中位于结束标记后面的JSP代码；
		// 如果doEndTag方法返回SKIP_PAGE=5，WEB容器就会忽略JSP页面中位于结束标记后面的所有内容。
		return 0;
	}

	// end.通常WEB容器执行完自定义标签后，标签处理器会驻留在内存中，为其它请求服务器，直至停止web应用时，web容器才会调用release方法。
	@Override
	public void release() {
		System.out.println("调用release()方法");
	}

}
