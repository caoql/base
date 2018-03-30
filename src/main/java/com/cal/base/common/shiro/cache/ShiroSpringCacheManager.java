package com.cal.base.common.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * 使用spring-cache作为shiro缓存
 * 缓存管理器
 */
public class ShiroSpringCacheManager implements CacheManager, Destroyable {
	// spring的CacheManager
	private org.springframework.cache.CacheManager cacheManager;
	
	public org.springframework.cache.CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		org.springframework.cache.Cache cache = cacheManager.getCache(name);
		return new ShiroSpringCache<K, V>(cache);
	}

	@Override
	public void destroy() throws Exception {
		cacheManager = null;
	}

}
