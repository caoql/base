package com.cal.base.common.shiro.encryption;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;


/**
 * shiro密码加密配置
 *
 */
public class PasswordHash implements InitializingBean {
	// 摘要算法名称
	private String algorithmName;
	// 加密次数
	private int hashIterations;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
	}
	
	public String toHex(Object source, Object salt) {
		return DigestUtils.hashByShiro(algorithmName, source, salt, hashIterations);
	}
	
	public String getAlgorithmName() {
		return algorithmName;
	}
	
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	
	public int getHashIterations() {
		return hashIterations;
	}
	
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
}
