package com.cal.base.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 控制整个jsp页面是否执行
 * 
 * @author andyc 2018-3-14
 *
 */
public class OldTagDemo2 extends TagSupport {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 642844190125765452L;

	/**
	 * 重写doEndTag方法，控制jsp页面是否执行
	 */
	@Override
	public int doEndTag() throws JspException {
		// 如果这个方法返回EVAL_PAGE，则执行标签余下的jsp页面，如果返回SKIP_PAGE，则不执行余下的jsp，默认返回EVAL_PAGE
		// return super.doEndTag();
		return Tag.SKIP_PAGE;
	}

}
