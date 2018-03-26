package com.cal.base.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * 修改jsp页面内容输出 　
 * 
 * @author andyc 2018-3-14 BodyTagSupport类实现了BodyTag接口接口
 */
public class OldTagDemo4 extends BodyTagSupport {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6087154640923380834L;

	@Override
	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		// this.getBodyContent()得到代表标签体的bodyContent对象
		BodyContent bodyContent = this.getBodyContent();
		// 拿到标签体
		String content = bodyContent.getString();
		// 修改标签体里面的内容，将标签体的内容转换成大写
		String result = content.toUpperCase();
		try {
			// 输出修改后的内容
			this.pageContext.getOut().write(result);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return Tag.EVAL_PAGE;
	}

}
