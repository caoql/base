/**
 * 概念澄清：
 * RBAC: 基于资源的访问控制(Resource-Based Access Control)”。
 * principals：身份，即主体的标识属性，可以是任何东西，如用户名、邮箱等，唯一即可。一个主体可以有多个principals，
 * 但只有一个Primary principals，一般是用户名/密码/手机号。
 * credentials：证明/凭证，即只有主体知道的安全值，如密码/数字证书等。
 * 最常见的principals和credentials组合就是用户名/密码了。接下来先进行一个基本的身份认证
 * Realm: 域，Shiro从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，
 * 那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm看成DataSource，即安全数据源。
 * 如我们之前的ini配置方式将使用org.apache.shiro.realm.text.IniRealm。
 * Authenticator: 职责是验证用户帐号,是Shiro API中身份验证核心的入口点
 * 
 * 
 * 授权: 也叫访问控制，即在应用中控制谁能访问哪些资源（如访问页面/编辑数据/页面操作等）。
 * 在授权中需了解的几个关键对象：主体（Subject）、资源（Resource）、权限（Permission）、角色（Role）。
 * 主体: 即访问应用的用户，在Shiro中使用Subject代表该用户。用户只有授权后才允许访问相应的资源
 * 资源: 在应用中用户可以访问的任何东西，比如访问JSP页面、查看/编辑某些数据、访问某个业务方法、打印文本等等都是资源。用户只要授权后才能访问。
 * 权限: 安全策略中的原子授权单位，通过权限我们可以表示在应用中用户有没有操作某个资源的权力。即权限表示在应用中用户能不能访问某个资源，如：访问用户列表页面
	查看/新增/修改/删除用户数据（即很多时候都是CRUD（增查改删）式权限控制）
	打印文档等等。。。
 *角色: 角色代表了操作集合，可以理解为权限的集合，一般情况下我们会赋予用户角色而不是权限，即这样用户可以拥有一组权限，赋予权限时比较方便。
	典型的如：项目经理、技术总监、CTO、开发工程师等都是角色，不同的角色拥有一组不同的权限。
 *
 *Shiro支持三种方式的授权：
 *1.编程式：通过写if/else授权代码块完成： 
 *	Subject subject = SecurityUtils.getSubject();  
	if(subject.hasRole(“admin”)) {  
	    //有权限  
	} else {  
	    //无权限  
	}   
 *2.注解式：通过在执行的Java方法上放置相应的注解完成： 没有权限将抛出相应的异常；
 *	@RequiresRoles("admin")  
	public void hello() {  
	    //有权限  
	} 
 *3.JSP/GSP标签：在JSP/GSP页面通过相应的标签完成：
 *	<shiro:hasRole name="admin">  
	<!— 有权限 —>  
	</shiro:hasRole>  
 *
 *
 *
 */
/**
 * @author andyc
 *
 */
package com.cal.base.common.auth;