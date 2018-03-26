package com.cal.base.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 控制jsp页面某一部分内容是否执行　
 * 
 * @author andyc 2018-3-14
 */
public class OldTagDemo1 extends TagSupport {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5892757079987837495L;

	/**
	 * 重写doStartTag方法，控制标签体是否执行
	 */
	@Override
	public int doStartTag() throws JspException {
		// 如果这个方法返回EVAL_BODY_INCLUDE，则执行标签体，如果返回SKIP_BODY，则不执行标签体，父类就是SKIP_BODY
		// return Tag.EVAL_BODY_INCLUDE;
		return super.doStartTag();
	}

}
