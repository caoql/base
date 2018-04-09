package com.cal.base.common.shiro.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.util.web.WebUtil;

/**
 * shiro 过滤器
 * Shiro的ProxiedFilterChain执行流程：1、先执行Shiro自己的Filter链；2、再执行Servlet容器的Filter链（即原始的Filter）。
 * @author andyc 2018-4-3
 *
 */
public class ShiroAuthFilter extends AuthenticationFilter {
	private static List<String> ignoreUrls = new ArrayList<String>();
	static {
		ignoreUrls.add("/login.jsp");
		ignoreUrls.add("/login");
		ignoreUrls.add("/loginout");
	}

	/**
	 * 日志记录器
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		logger.debug("===========ShiroAuthFilter.isAccessAllowed========="
				+ WebUtil.getRequestUri());
		Subject subject = SecurityUtils.getSubject();
		CurrentUserInfo user = WebUtil.getRedisUser();
		if ((user == null) || (!subject.isAuthenticated())) {
			try {
				redirectToLogin(request, response);
			} catch (IOException ioe) {
				throw new CommonException(ErrorCodeEnum.AUTH_ERROR);
			}
			return false;
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}

	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest req = WebUtils.toHttp(request);
		String xmlHttpRequest = req.getHeader("X-Requested-With");
		if (StringUtils.isNotBlank(xmlHttpRequest)) {
			if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
				HttpServletResponse res = WebUtils.toHttp(response);
				// 采用res.sendError(401);在Easyui中会处理掉error，$.ajaxSetup中监听不到
				res.setHeader("oauthstatus", "401");
				return false;
			}
		}
		return true;
	}
}