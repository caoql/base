package com.cal.base.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 控制jsp页面内容重复执行
 * 
 * @author andyc 2018-3-14
 *
 */
public class OldTagDemo3 extends TagSupport {

	int x = 5;
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3165058277479545092L;

	@Override
	public int doStartTag() throws JspException {
		return Tag.EVAL_BODY_INCLUDE;
	}

	/*
	 * 控制doAfterBody()方法的返回值， 如果这个方法返回EVAL_BODY_AGAIN， 则web服务器又执行一次标签体，
	 * 依次类推，一直执行到doAfterBody方法返回SKIP_BODY，则标签体才不会重复执行。 默认值是SKIP_BODY
	 */
	@Override
	public int doAfterBody() throws JspException {
		x--;
		if (x > 0) {
			return IterationTag.EVAL_BODY_AGAIN;
		} else {
			return IterationTag.SKIP_BODY;
		}
	}

}
