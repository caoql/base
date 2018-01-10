package com.cal.base.common.cache;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

/*
 * 使用第三方缓存服务器，处理二级缓存
 */
public class RedisCache implements Cache {

	/** The ReadWriteLock. */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private String id;// 这个id值是namespace的值
	private final String COMMON_CACHE_KEY = "MYBATIS:";

	public RedisCache() {}

	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("必须传入ID");
		}
		this.id = id;
	}
	
	/**
	 * 按照一定规则标识key
	 */
	private String getKey(Object key) {
		StringBuilder accum = new StringBuilder();
		accum.append(COMMON_CACHE_KEY);
		accum.append(this.id).append(":");
		accum.append(String.valueOf(key));
		return accum.toString();
	}

	/**
	 * redis key规则前缀
	 */
	private String getKeys() {
		return COMMON_CACHE_KEY + this.id + ":*";
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		RedisClient.set(getKey(key), value);
	}

	@Override
	public Object getObject(Object key) {
		return RedisClient.getObj(getKey(key));
	}

	@Override
	public Object removeObject(Object key) {
		return RedisClient.del(getKey(key));
	}

	@Override
	public void clear() {
		Set<byte[]> keys = RedisClient.keys(getKeys());
		for (byte[] key : keys) {
			RedisClient.del(key);
        }
	}

	@Override
	public int getSize() {
		Set<byte[]> keys = RedisClient.keys(getKeys());
		int result = 0;
		if (null != keys && !keys.isEmpty()) {
			result = keys.size();
		}
		return result;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
}
