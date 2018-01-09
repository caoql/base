package com.cal.base.common.cache;


import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.cal.base.common.util.ByteToObjectUtils;
import com.cal.base.common.util.SerializeUtil;

/**
 * @author 
 * 
 */
public class RedisClient {
	private static JedisPool jedisPool = null;
	// 字符编码
	private static final String CAHRSET = "utf-8";
	// 数据库索引
	private static final int DEFAULT_DB_INDEX = 1;
	
	private static Logger logger = Logger.getLogger(RedisClient.class);
	
	/**
	 * 设置redis连接池,在配置文件中配置,项目启动的时候设置进去
	 * @param jedisPool
	 */
    public void setJedisPool(JedisPool jedisPool) {
    	RedisClient.jedisPool = jedisPool;
    }
    
	/**
	 * 从连接池获取一个连接
	 * @return
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 返回给连接池
	 * 
	 * @param jedis
	 */
	public static void closeJedis(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * 判断key 是否存在
	 * Redis EXISTS 命令用于检查给定 key 是否存在，若 key 存在返回 1 ，否则返回 0。
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("#exists key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return false;
	}
	
	/**
	 * 删除 keys
	 * Redis DEL 命令用于删除已存在的键。不存在的 key 会被忽略。返回被删除 key 的数量。
	 * @param keys
	 * @return 返回被删除 key 的数量
	 */
	public static long del(String... keys) {
		Jedis jedis = null;
		long num = 0;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.del(keys);
		} catch (Exception e) {
			logger.error("#del key = " + ArrayUtils.toString(keys), e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	public static long del(byte[] key) {
		Jedis jedis = null;
		long num = 0;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.del(key);
		} catch (Exception e) {
			logger.error("#del key = " + ArrayUtils.toString(key), e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * key 设置过期时间
	 * Redis Expire 命令用于设置 key 的过期时间。key 过期后将不再可用
	 * 设置成功返回 1 。当 key 不存在或者不能为 key 设置过期时间时返回 0
	 * @param key
	 * @param timeout 过多少秒
	 * @return 1表示成功，0表示失败
	 */
	public static long expire(String key, int timeout) {
		Jedis jedis = null;
		long num = 0;
		try {
			if (timeout > 0) {
				jedis = jedisPool.getResource();
				jedis.select(DEFAULT_DB_INDEX);
				num = jedis.expire(key, timeout);
			}
		} catch (Exception e) {
			logger.error("#expire key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	
	/**
	 * Redis 字符串数据类型的相关命令用于管理 redis 字符串值 
	 */
	/**
	 * 设值 key并设置失效时间
	 * Redis SET 命令用于设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型，在设置操作成功完成时，才返回 OK 。
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public static void set(String key, String value, int timeout) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.set(key, value);
			if (timeout > 0) {
				jedis.expire(key, timeout);
			}
		} catch (Exception e) {
			logger.error("#set key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static void set(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.set(key.getBytes(CAHRSET), SerializeUtil.serialize(value));
		} catch (Exception e) {
			logger.error("#set key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 设值 key
	 * Redis SET 命令用于设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型，在设置操作成功完成时，才返回 OK 。
	 * @param key
	 * @param value 注意对象必须是要序列化的Serializable
	 */
	public static void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error("#set key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * Redis Get 命令用于获取指定 key 的值。如果 key 不存在，返回 nil 。如果key 储存的值不是字符串类型，返回一个错误
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("#get key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static Object getObj(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			byte[] obj = jedis.get(key.getBytes(CAHRSET));
			if (obj != null) {
				return SerializeUtil.unserialize(obj);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("#get key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。
	 */
	/**
	 * 
	 *  Redis Hset 命令用于为哈希表中的字段赋值 。
	 *  如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
	 *	如果字段已经存在于哈希表中，旧值将被覆盖。
	 *  如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hset(String key, String field ,Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.hset(key.getBytes(CAHRSET), ByteToObjectUtils.ObjectToByte(field), ByteToObjectUtils.ObjectToByte(value));
		} catch (Exception e) {
			logger.error("#hset key = " + key + ", field = " + field+ ", value = " + value, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * Redis Hget 命令用于返回哈希表中指定字段的值。
	 * @param key
	 * @param field
	 * @param value
	 */
	public static Object hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			byte[] obj = jedis.hget(key.getBytes(CAHRSET), ByteToObjectUtils.ObjectToByte(field));
			if (obj != null) {
				return ByteToObjectUtils.ByteToObject(obj);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("#hget key = " + key + ", field = " + field, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * Redis Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hdel(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.hdel(key.getBytes(CAHRSET), ByteToObjectUtils.ObjectToByte(field));
		} catch (Exception e) {
			logger.error("#hdel key = " + key + ", fields = " + field, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	
	/**
	 * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
	 */
	/**
	 * 从左侧/表头/队列头部/插入
	 * @param key
	 * @param values
	 */
	public static void lpush(String key, String... values) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			if (values != null) {
				jedis.rpush(key, values);	
			}
		} catch (Exception e) {
			logger.error("#lpush key = " + key + ", values = " + values, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 从右侧/表尾/队列尾插入
	 * @param key
	 * @param obj
	 */
	public static void rpush(String key , String... value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			if (value != null) {
				jedis.rpush(key, value);	
			}
		} catch (Exception e) {
			logger.error("#rpush key = " + key + ", value = " + value, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 从左侧/表头/队列头取出  阻塞式 
	 * Redis Blpop 命令移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
	 * 如果列表为空，返回一个 nil 。 否则，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
	 * @param key
	 * @param timeout  阻塞式 时间, 单位是秒
	 */
	public static List<String> blpop(String key, Integer timeout) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.blpop(timeout, key);
		} catch (Exception e) {
			logger.error("# blpop key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 从右侧/表尾/队列尾取出  阻塞式 
	 * Redis Brpop 命令移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
	 * 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
	 * @param key
	 * @param timeout  阻塞式 时间, 单位是秒
	 */
	public static List<String> brpop(String key, Integer timeout) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error("# brpop key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 从左侧/表头/队列头取出 非阻塞式 
	 * @param key
	 */
	public static String lpop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.lpop(key);
		} catch (Exception e) {
			logger.error("# lpop key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 从右侧/表尾/队列尾取出 非阻塞式 
	 * @param key
	 */
	public static String rpop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.rpop(key);
		} catch (Exception e) {
			logger.error("# rpop key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 读取队列长度
	 * @param key
	 */
	public static Long llen(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.llen(key);
		} catch (Exception e) {
			logger.error("#llen key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 读取队列内容
	 * @param key
	 */
	public static List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			logger.error("#lrange key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	
	
	public static void setex(String key, Object value, Integer seconds) {
		Jedis jedis = null;
		try {
			logger.debug("#set key="+key+",value="+value);
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.setex(key.getBytes(CAHRSET), seconds, ByteToObjectUtils.ObjectToByte(value));
		} catch (Exception e) {
			logger.error(e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static void setnx(String key, Object value, Integer timeout) {
		Jedis jedis = null;
		try {
			logger.debug("#set key="+key+",value="+value);
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.setnx(key.getBytes(CAHRSET), ByteToObjectUtils.ObjectToByte(value));
			if (timeout != null && timeout > 0) {
				jedis.expire(key.getBytes(CAHRSET), timeout);
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 自增/减 
	 * @param key
	 * @num num 自增/减 数 default is 1
	 */
	public static Long incrby(String key,Long num) {
		Jedis jedis = null;
		if(num==null){
			num =1l ;
		}
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.incrBy(key.getBytes(CAHRSET), num);
		} catch (Exception e) {
			logger.error("key="+key+",num="+num,e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static Set<byte[]> keys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.keys(key.getBytes(CAHRSET));
		} catch (Exception e) {
			logger.error("jedis = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
}
