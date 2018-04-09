package com.cal.base.common.shiro.encryption;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.cal.base.system.entity.po.UserPO;

/**
 * 加盐密码
 * 
 * @author andyc 2018-4-9
 */
public class PasswordHelper {
	// 随机数生成帮助类
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	// 加密算法
	private String algorithmName = "md5";
	// 加密次数
	private final int hashIterations = 2;

	// 加密密码  
	public void encryptPassword(UserPO user) {
		// UserPO对象包含最基本的字段account和password
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		// 将用户的注册密码经过散列算法替换成一个不可逆的新密码保存进数据，散列过程使用了盐
		String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}

}
