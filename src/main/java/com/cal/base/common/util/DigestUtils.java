package com.cal.base.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密相关工具类直接使用Spring util封装，减少jar依赖
 */
public class DigestUtils {
    
    /**
     * 使用shiro的hash方式
     * @param algorithmName 算法
     * @param source 源对象
     * @param salt 加密盐
     * @param hashIterations hash次数
     * @return 加密后的字符
     */
    public static String hashByShiro(String algorithmName, Object source, Object salt, int hashIterations) {
        return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
    }
    
}
