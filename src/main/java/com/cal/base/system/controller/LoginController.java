package com.cal.base.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cal.base.SystemConstant;
import com.cal.base.common.cache.RedisClient;
import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.util.web.WebUtil;
import com.cal.base.system.entity.LoginParam;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.service.RoleService;
import com.cal.base.system.service.UserService;

@Controller
public class LoginController extends BaseController {

	// 日志记录器
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 注入用户Service
	@Autowired
	private UserService userService;

	// 注入角色Service
	@Autowired
	private RoleService roleService;

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
	public Object doLogin(HttpServletRequest request, LoginParam param) {
		logger.debug("请求登录...");
		// 登录信息校验
		validate(param);
		UserPO userPO = null;
		// 获取Subject单例对象
		Subject user = SecurityUtils.getSubject();
		// 使用用户的登录信息创建令牌-登录的过程被抽象为Shiro验证令牌是否具有合法身份以及相关权限
		UsernamePasswordToken token = new UsernamePasswordToken(param.getUsername(), param.getPassword());
		try {
			user.login(token);
			if (user.isAuthenticated()) {
				userPO = userService.selectByLoginName(param.getUsername());
				if (userPO == null) {
					token.clear();
				}
			}
			// 异常错误的继承体系
			// 对于页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库；
		} catch (UnknownAccountException e) {
			throw new RuntimeException("账号不存在", e);
		} catch (DisabledAccountException e) {
			throw new RuntimeException("账号未启用", e);
		} catch (IncorrectCredentialsException e) {
			throw new RuntimeException("用户名或者密码错误", e);
		} catch (UnknownSessionException e) {
			throw new RuntimeException("会话失效，请重新登录", e);
		} catch (Throwable e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (userPO != null) {
			CurrentUserInfo userInfo = new CurrentUserInfo();
			BeanUtils.copyProperties(userPO, userInfo);
			// redis存储机制
			RedisClient.set(
					SystemConstant.getSessionKey(WebUtil.getSessionId()),
					userInfo);
			return renderSuccess();
		} else {
			return renderError("用户信息为空");
		}
	}

	@PostMapping("/loginout")
	@ResponseBody
	public Object doLoginOut(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("退出系统...");
		// 删除redis
		RedisClient.del(SystemConstant.getSessionKey(WebUtil.getSessionId()));
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		return renderSuccess();
	}

	// 获取用户的资源
	@PostMapping("/getresources")
	@ResponseBody
	public Object getResources(HttpServletRequest request) {
		// 以后拿当期用户的
		CurrentUserInfo userinfo = WebUtil.getRedisUser();
		if (userinfo == null) {
			throw new CommonException(ErrorCodeEnum.CURRENT_USER_IS_NULL);
		}
		ResponseInfo info = userService.getResourcesByUserid(userinfo
				.getUserId());
		return info;
	}
	
	// 校验参数
	private boolean validate(LoginParam param) {
		if (param == null) {
			throw new CommonException(ErrorCodeEnum.PARAM_IS_NULL);
		}
		if (StringUtils.isBlank(param.getUsername())) {
			throw new CommonException("用户名不能为空");
		}
		if (StringUtils.isBlank(param.getPassword())) {
			throw new CommonException("密码不能为空");
		}
		// 后续扩展验证码？
		return true;
	}
}
