package com.cal.base.common.cache;

import redis.clients.jedis.ShardedJedis;

/**
 * getRedisClient() ： 取得redis的客户端，可以执行命令了。
returnResource(ShardedJedis shardedJedis) ： 将资源返还给pool
returnResource(ShardedJedis shardedJedis, boolean broken) : 出现异常后，将资源返还给pool （其实不需要第二个方法）
 * @author andyc
 *
 */
public interface RedisDataSource {
	  public abstract ShardedJedis getRedisClient();
	    public void returnResource(ShardedJedis shardedJedis);
	    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}
