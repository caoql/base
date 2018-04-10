package com.cal.base.system.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
import com.cal.base.common.mail.MailService;
import com.cal.base.common.mail.MyMailSenderTest;
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
	
	// 注入邮件Service
	@Autowired
	private MailService mailService;

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
	
	/**
	 * 跳转到登录页
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@GetMapping("/login")
	public void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("get 访问 /login...");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
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
			throw new CommonException("账号不存在");
		} catch (DisabledAccountException e) {
			throw new CommonException("账号未启用");
		} catch (IncorrectCredentialsException e) {
			throw new CommonException("用户名或者密码错误");
		} catch (UnknownSessionException e) {
			throw new CommonException("会话失效，请重新登录");
		} catch (AuthenticationException e) {  
            //其他错误，比如锁定，如果想单独处理请单独catch处理  
			throw new CommonException("登录验证失败"); 
        }  catch (Throwable e) {
			throw new CommonException(e.getMessage());
		}
		if (userPO != null) {
			CurrentUserInfo userInfo = new CurrentUserInfo();
			BeanUtils.copyProperties(userPO, userInfo);
			// redis存储机制
			RedisClient.set(
					SystemConstant.getSessionKey(WebUtil.getSessionId()),
					userInfo);
			/*//发送邮件是一件非常耗时的事情，因此这里开辟了另一个线程来专门发送邮件
			MyMailSenderTest send = new MyMailSenderTest();
	        //启动线程，线程启动之后就会执行run方法来发送邮件
	        send.start();*/
			mailService.send("系统登录成功了");
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
