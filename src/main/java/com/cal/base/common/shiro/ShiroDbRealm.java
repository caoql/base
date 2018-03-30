package com.cal.base.common.shiro;

import java.util.List;
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
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.service.RoleService;
import com.cal.base.system.service.UserService;

/**
 * @description Realm：可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全实体的；
 *              可以是JDBC实现，也可以是LDAP实现
 *              ，或者内存实现等等；由用户提供；注意：Shiro不知道你的用户/权限存储在哪及以何种格式存储；
 *              所以我们一般在应用中都需要实现自己的Realm；
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
	 * Shiro登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ----
	 * shiro 自动去比较查询出密码和用户输入密码是否一致 ---- 进行登陆控制 )
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("Shiro开始登录认证");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		List<UserPO> list = userService.selectByLoginName(token.getUsername());
		// 账号不存在
		if (list == null || list.isEmpty()) {
			throw new UnknownAccountException();
		}
		// 默认取第一个用户的信息对比
		UserPO user = list.get(0);
		// 账号未启用
		if (user.getIsEnabled() != 1) {
			throw new DisabledAccountException();
		}
		// 密码不相等
		if (!Objects
				.equals(new String(token.getPassword()), user.getPassword())) {
			throw new IncorrectCredentialsException();
		}
		// 读取用户的url和角色
		Map<String, Set<String>> resourceMap = roleService
				.selectResourceMapByUserId(user.getUserId());
		Set<String> urls = resourceMap.get("urls");
		Set<String> roles = resourceMap.get("roles");
		ShiroUser shiroUser = new ShiroUser(user.getUserId(),
				user.getAccount(), user.getName(), urls);
		shiroUser.setRoles(roles); // 认证缓存信息 
		return new SimpleAuthenticationInfo(shiroUser, user.getPassword().toCharArray(), getName());
	}

	/**
	 * Shiro权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(shiroUser.getRoles());
		info.addStringPermissions(shiroUser.getUrlSet());

		return info;
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		removeUserCache(shiroUser);
	}

	/**
	 * 清除用户缓存
	 * 
	 * @param shiroUser
	 */
	public void removeUserCache(ShiroUser shiroUser) {
		removeUserCache(shiroUser.getLoginName());
	}

	/**
	 * 清除用户缓存
	 * 
	 * @param loginName
	 */
	public void removeUserCache(String loginName) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(loginName, super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}
}
