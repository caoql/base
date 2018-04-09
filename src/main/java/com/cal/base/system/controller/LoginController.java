package com.cal.base.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
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
	private Logger logger = Logger.getLogger(getClass());

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
		validate(param);
		UserPO userPO = null;
		// 应用代码直接交互的对象是Subject，也就是说Shiro的对外API核心就是Subject
		// Subject：主体，代表了当前“用户”，这个用户不一定是一个具体的人，与当前应用交互的任何东西都是Subject,即一个抽象概念；所有Subject都绑定到SecurityManager，与Subject的所有交互都会委托给SecurityManager；可以把Subject认为是一个门面；SecurityManager才是实际的执行者；
		// SecurityManager：安全管理器；即所有与安全有关的操作都会与SecurityManager交互；且它管理着所有Subject；可以看出它是Shiro的核心，它负责与后边介绍的其他组件进行交互，如果学习过SpringMVC，你可以把它看成DispatcherServlet前端控制器；
		// Realm：域，Shiro从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
		
		// 获取Subject单例对象
		Subject user = SecurityUtils.getSubject();
		// 使用用户的登录信息创建令牌-登录的过程被抽象为Shiro验证令牌是否具有合法身份以及相关权限
		UsernamePasswordToken token = new UsernamePasswordToken(param.getUsername(), param.getPassword());
        try {
            user.login(token);
            if (user.isAuthenticated()) {
            	List<UserPO> list = userService.selectByLoginName(param.getUsername());
            	if (list != null && list.size() == 1) {// 取出用户数据
            		userPO = list.get(0);
            	} else {
            		token.clear();
            	}
            }
           
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
        if (userPO != null) {
        	CurrentUserInfo userInfo = new CurrentUserInfo();
        	BeanUtils.copyProperties(userPO, userInfo);
        	// redis存储机制
        	RedisClient.set(SystemConstant.getSessionKey(WebUtil.getSessionId()), userInfo);
        	return renderSuccess();
        } else {
        	return renderError("用户信息为空");
        }
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
	
	@PostMapping("/loginout")
	@ResponseBody
	public Object doLoginOut(HttpServletRequest request, HttpServletResponse response) {
		logger.info("POST请求退出系统");
		 // 删除redis
		RedisClient.del(SystemConstant.getSessionKey(WebUtil.getSessionId()));
		Subject subject = SecurityUtils.getSubject();
	    if (subject != null) subject.logout();
		return  renderSuccess();
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
		ResponseInfo info = userService.getResourcesByUserid(userinfo.getUserId());
		return info;
	}
}
