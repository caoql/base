package com.cal.base.common.shiro;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cal.base.SystemConstant;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.service.RoleService;
import com.cal.base.system.service.UserService;

/**
 * @description Realm：安全实体数据源，即用于获取安全实体的；
 * 
 * @author：andyC 2018-3-15
 */
public class ShiroDbRealm extends AuthorizingRealm {
	// 日志记录器
	private static final Logger logger = LoggerFactory
			.getLogger(ShiroDbRealm.class);

	// 注入用户Service
	@Autowired
	private UserService userService;

	// 注入角色Service
	@Autowired
	private RoleService roleService;

	public ShiroDbRealm(CacheManager cacheManager) {
		super(cacheManager);
	}

	public ShiroDbRealm() {
		super();
	}

	/**
	 * 提供账户信息返回认证信息 AuthenticationInfo代表了用户的角色信息集合
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		logger.debug("Shiro开始登录认证...");
		// 转化为登录时创建的令牌类型
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 登录用户名
		String username = (String) token.getPrincipal();
		// 数据库查询验证
		UserPO user = userService.selectByLoginName(username);
		if (user == null) {
			// 用户名不存在抛出异常
			throw new UnknownAccountException();
		}
		// 账号未启用
		if (!Objects.equals(user.getIsEnabled(), SystemConstant.IS_YES_SHORT)) {
			throw new DisabledAccountException();
		}
		// 密码不相等
		if (!Objects.equals(new String(token.getPassword()), user.getPassword())) {
			throw new IncorrectCredentialsException();
		}

		return new SimpleAuthenticationInfo(user.getUserId(),
				token.getCredentials(), getName());
	}

	/**
	 * AuthorizationInfo代表了角色的权限信息集合 Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		logger.debug("Shiro开始授权认证...");
		String userId = (String) principals.getPrimaryPrincipal();
		// 读取用户的url和角色
		Map<String, Set<String>> resourceMap = roleService
				.selectResourceMapByUserId(userId);
		Set<String> urls = resourceMap.get("urls");
		Set<String> roles = resourceMap.get("roles");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.addStringPermissions(urls);

		return info;
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
}
