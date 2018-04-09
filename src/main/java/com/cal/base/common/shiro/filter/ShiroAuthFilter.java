package com.cal.base.common.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.util.web.WebUtil;

/**
 * shiro 过滤器
 * @author andyc 2018-4-3
 *
 */
public class ShiroAuthFilter extends AuthenticationFilter {
	 private static List<String> ignoreUrls = new ArrayList<String>();
	 static {
		 ignoreUrls.add("/login.jsp");
		 ignoreUrls.add("/login");
		 ignoreUrls.add("/loginout");
		 ignoreUrls.add("/403.html");
	 }
	 
	 /**
     * 日志记录器
     */
    private Logger logger = Logger.getLogger(getClass());
    
	/**
	 * 
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		 logger.debug("===========ShiroAuthFilter.isAccessAllowed=========" + WebUtil.getRequestUri());
		Subject subject = SecurityUtils.getSubject();
         String uri = WebUtil.getRequestUri();
        CurrentUserInfo user = WebUtil.getRedisUser();
        if ((user == null) || (!subject.isAuthenticated())) {
            try {
                redirectToLogin(request, response);
            } catch (IOException ioe) {
                throw new CommonException(ErrorCodeEnum.AUTH_ERROR);
            }
            return false;
        }
        // 超级管理员处理
        if ((!ignoreUrls.contains(uri))) {
            if (!subject.isPermitted(uri)) {
                HttpServletRequest httpRequest = WebUtils.toHttp(request);
                try {
                    if ("application/json".equals(httpRequest.getContentType())
                            || StringUtils.startsWith(httpRequest.getContentType(), "multipart/form-data")) {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write("{\"code\":403,\"message\":\"没有权限\"}");
                        writer.flush();
                    } else {
                        WebUtils.issueRedirect(request, response, "/403.html");
                    }
                    return false;
                } catch (IOException e) {
                    throw new  CommonException(ErrorCodeEnum.AUTH_ERROR);
                }
            }
        }
		return super.isAccessAllowed(request, response, mappedValue);
	}

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