package com.cal.base.common.util.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import com.alibaba.fastjson.JSONObject;
import com.cal.base.SystemConstant;
import com.cal.base.common.cache.RedisClient;
import com.cal.base.common.info.CurrentUserInfo;

public class WebUtil extends org.springframework.web.util.WebUtils {
	public static String getUserId() {
		HttpServletRequest request = getCurrentRequest();
		String cookieValue = CookieUtil.getCookieValueByName(request,
				Constant.TMS_LOGIN);
		return LoginCookieDto.buildDto(cookieValue).getUserId();
	}

	// 获取当前的会话的sessionId
	public static String getSessionId() {
		return RequestContextHolder.currentRequestAttributes().getSessionId();
	}

	// 获取session共享用户信息-暂时还没做
	public static CurrentUserInfo getSessionUser() {
		HttpServletRequest request = getCurrentRequest();
		return (CurrentUserInfo) request.getSession().getAttribute(
				SystemConstant.LOGIN_USER_PRE);
	}

	// 获取redis设置的用户信息
	public static CurrentUserInfo getRedisUser() {
		HttpServletRequest request = getCurrentRequest();
		// 和登录的时候存储的值要对应
		CurrentUserInfo redisUser = (CurrentUserInfo) RedisClient.get(SystemConstant.getSessionKey(request.getRequestedSessionId()));
		return redisUser;
	}

	public static String getBaseUrl() {
		HttpServletRequest request = getCurrentRequest();
		String url = request.getRequestURL().toString();
		String ctxPath = request.getContextPath();
		return url.substring(
				0,
				url.indexOf(StringUtils.isBlank(ctxPath) ? "/" : ctxPath,
						url.indexOf("://") + 3)
						+ ctxPath.length());
	}

	public static String getRequestUri() {
		HttpServletRequest request = getCurrentRequest();
		String uri = (String) request
				.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
		if (uri == null) {
			uri = request.getRequestURI();
			uri = uri.substring(request.getContextPath().length() + 1);
		}
		return trimPathParameter(uri);
	}

	public static String trimPathParameter(String url) {
		if (url == null)
			return null;
		int i = url.indexOf(';');
		return i > -1 ? url.substring(0, i) : url;
	}

	// 获取到当前请求的response对象
	public static HttpServletResponse getCurrentResponse() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();
	}

	// 获取到当前请求的request对象
	public static HttpServletRequest getCurrentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	// 获取客户端IP
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static JSONObject getRequestParams(HttpServletRequest request) {
		JSONObject params = new JSONObject();
		if (null != request) {
			Set<String> paramsKey = request.getParameterMap().keySet();
			for (String key : paramsKey) {
				params.put(key, request.getParameter(key));
			}
		}
		return params;
	}

	public static Map<String, String> getRequestParamsMap(
			HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		if (null != request) {
			Set<String> paramsKey = request.getParameterMap().keySet();
			for (String key : paramsKey) {
				params.put(key, request.getParameter(key));
			}
		}
		return params;
	}

	public static String getReqData(HttpServletRequest request) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			throw new RuntimeException();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException();
				}
			}
		}
		return sb.toString();
	}

	// AAAAAAAAAAAAAA
	/**
	 * 将request对象转换成T对象
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	public static <T> T request2Bean(HttpServletRequest request, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断是否ajax请求 spring ajax 返回含有 ResponseBody 或者 RestController注解
	 * 
	 * @param handlerMethod
	 *            HandlerMethod
	 * @return 是否ajax请求
	 */
	public static boolean isAjax(HandlerMethod handlerMethod) {
		ResponseBody responseBody = handlerMethod
				.getMethodAnnotation(ResponseBody.class);
		if (null != responseBody) {
			return true;
		}
		// 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
		Class<?> beanType = handlerMethod.getBeanType();
		responseBody = AnnotationUtils.getAnnotation(beanType,
				ResponseBody.class);
		if (null != responseBody) {
			return true;
		}
		return false;
	}

	/**
	 * 读取cookie
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 清除 某个指定的cookie
	 * 
	 * @param response
	 * @param key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAgeInSeconds
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

}
