package com.cal.base.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cal.base.common.exception.ServiceException;
import com.cal.base.common.info.ResponseInfo;
import com.cal.base.system.service.IRoleService;
import com.cal.base.system.service.IUserService;

@Controller
public class LoginController extends BaseController {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	// 注入用户Service
	@Autowired
	private IUserService userService;
	
	// 注入角色Service
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 访问首页
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/index")
	public String index(Model model) {
		logger.debug("get 访问 index...");
		return "index";
	}

	
	@PostMapping("/login")
	@ResponseBody
	public Object doLoginByPost(String username, String password,@RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
		logger.info("POST请求登录");
		// 改为全部抛出异常，避免ajax csrf token被刷新?
		if (StringUtils.isBlank(username)) {
			throw new ServiceException("用户名不能为空");
		}
		if (StringUtils.isBlank(password)) {
			throw new ServiceException("密码不能为空");
		}
		// 后续扩展验证码？
		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// 设置记住密码
        token.setRememberMe(1 == rememberMe);
        try {
            user.login(token);
            return renderSuccess();
        } catch (UnknownAccountException e) {
            throw new RuntimeException("账号不存在", e);
        } catch (DisabledAccountException e) {
            throw new RuntimeException("账号未启用", e);
        } catch (IncorrectCredentialsException e) {
        	//注意：此处按照安全框架的显示应该是密码错误，但是前端不能直接显示密码错误用户名或者密码错误
            throw new RuntimeException("用户名或者密码错误", e);
        } catch (UnknownSessionException e) {  
        	throw new RuntimeException("会话失效，请重新登录", e);  
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
	}

	// 登录系统做到session共享或者是token共享

	// 发送短信验证码限速，网站限制IP地址一秒之内不能访问超过N次等

	// 获取用户的资源
	@PostMapping("/getresources")
	@ResponseBody
	public Object getResources(HttpServletRequest request) {
		// 以后拿当期用户的
		Long userId = 12L;
		ResponseInfo info = userService.getResourcesByUserid(userId);
		return info;
	}
}
