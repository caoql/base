package com.cal.base.common.cache;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.cal.base.common.util.SerializeUtil;

/**
 * @author andyc
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
	 * 设置redis连接池,在配置文件中配置,项目启动的时候设置进去，构造器注入
	 * 
	 * @param jedisPool
	 */
	public void setJedisPool(JedisPool jedisPool) {
		RedisClient.jedisPool = jedisPool;
	}

	/**
	 * 从连接池获取一个连接jedis
	 * 
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
			// 如果使用JedisPool,close操作不是关闭连接，代表归还连接池
			// 可以查看源代码验证
			jedis.close();
		}
	}

	/**
	 * 判断key 是否存在
	 * @param key
	 * @return
	 */
	public static Boolean exists(String key) {
		Jedis jedis = null;
		Boolean flag = false; 
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			flag = jedis.exists(key);
		} catch (Exception e) {
			logger.error("#exists key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return flag;
	}

	/**
	 * 删除 keys
	 * @param keys
	 * @return 返回被删除 key 的数量
	 */
	public static Long del(String... keys) {
		Jedis jedis = null;
		Long num = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
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
	
	/**
	 * 重载删除key方法
	 * @param key
	 * @return
	 */
	public static Long del(byte[] key) {
		Jedis jedis = null;
		Long num = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.del(key); 
		} catch (Exception e) {
			logger.error("#del key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}

	/**
	 * 设置失效时间
	 * @param key
	 * @param timeout 过多少秒
	 * @return 1表示成功，0表示失败
	 */
	public static Long expire(String key, int timeout) {
		Jedis jedis = null;
		Long num = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.expire(key, timeout);
		} catch (Exception e) {
			logger.error("#expire key = " + key + ", timeout = " + key , e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}

	/**
	 * 自增/减: 常用来做计数功能，如用户的播放视频次数，登录系统次数
	 * @param key
	 * @num num 自增/减 数 default is 1
	 */
	public static Long incrby(String key, Long num) {
		Jedis jedis = null;
		if (key == null) {
			throw new RuntimeException("key不能是空对象");
		}
		if (num == null) {
			num = 1L;
		}
		try {
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.incrBy(key.getBytes(CAHRSET), num);
		} catch (Exception e) {
			logger.error("incrby key = " + key + ", num = " + num, e);
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

	
	
	/**
	 * Redis 字符串数据类型的相关命令用于管理 redis 字符串值
	 * 应用场景：
	 * 	1.缓存
	 * 	2.计数
	 *  3.共享session
	 *  4.限速
	 */
	
	/**
	 * 设值 key 
	 * @param key
	 * @param value
	 * 注意对象必须是要序列化的Serializable
	 */
	public static void set(String key, Object value) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (value == null) {
				throw new RuntimeException("不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.set(key.getBytes(CAHRSET), SerializeUtil.serialize(value));
		} catch (Exception e) {
			logger.error("#set key = " + key + ", value = " + value, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 设值 key 并设置失效时间 
	 * @param key
	 * @param value
	 * @param timeout 单位是秒
	 * 注意对象必须是要序列化的Serializable
	 */
	public static void set(String key, Object value, int timeout) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (value == null) {
				throw new RuntimeException("不能存空对象");
			}
			if (timeout > 0) {// 只有失效时间大于0才有设置的必要，否则到数据库马上就失效了，没什么意义
				jedis = jedisPool.getResource();
				jedis.select(DEFAULT_DB_INDEX);
				jedis.set(key.getBytes(CAHRSET), SerializeUtil.serialize(value));
				jedis.expire(key.getBytes(CAHRSET), timeout);
			}
		} catch (Exception e) {
			logger.error("#set key = " + key + ", value = " + value + ", timeout = " + timeout, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 当key不存在的时候设置成功：setnx的特性只有一个客户端能设置成功，可以作为分布式锁的一种实现方案
	 * @param key
	 * @param value
	 */
	public static void setnx(String key, Object value) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (value == null) {
				throw new RuntimeException("不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.setnx(key.getBytes(CAHRSET), SerializeUtil.serialize(value));
		} catch (Exception e) {
			logger.error("#setnx key = " + key + ", value = " + value, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
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
	 * 哈希的叫法可以是：哈希，字典，关联数组。
	 */
	
	/**
	 * 哈希表 key 中的字段 field 的值设为 value 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hset(String key, String field, Object value) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (field == null) {
				throw new RuntimeException("field不能是空对象");
			}
			if (value == null) {
				throw new RuntimeException("value不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.hset(key.getBytes(CAHRSET), SerializeUtil.serialize(field), SerializeUtil.serialize(value));
		} catch (Exception e) {
			logger.error("#hset key = " + key + ", field = " + field + ", value = " + value, e);
		} finally {
			closeJedis(jedis);
		}
	}

	/**
	 * 获取存储在哈希表中指定字段的值。。
	 * @param key
	 * @param field
	 * @param value
	 */
	public static Object hget(String key, String field) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (field == null) {
				throw new RuntimeException("field不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			byte[] obj = jedis.hget(key.getBytes(CAHRSET), SerializeUtil.serialize(field));
			if (obj != null) {
				return SerializeUtil.unserialize(obj);
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
	 * 删除一个哈希表字段
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hdel(String key, String field) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (field == null) {
				throw new RuntimeException("field不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			jedis.hdel(key.getBytes(CAHRSET), SerializeUtil.serialize(field));
		} catch (Exception e) {
			logger.error("#hdel key = " + key + ", fields = " + field, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	/**
	 * 计算field的个数
	 * @param key
	 */
	public static Long hlen(String key) {
		Jedis jedis = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			return jedis.hlen(key.getBytes(CAHRSET));
		} catch (Exception e) {
			logger.error("#hlen key = " + key, e);
			return null;
		} finally {
			closeJedis(jedis);
		}
	}
	
	
	
	/**
	 * Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）
	 * 应用场景包括：消息队列，
	 */
	
	/**
	 * 从左侧/表头/队列头部/插入
	 * 
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
	 * 
	 * @param key
	 * @param obj
	 */
	public static void rpush(String key, String... value) {
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
	 * 从左侧/表头/队列头取出 阻塞式 Redis Blpop 命令移出并获取列表的第一个元素，
	 * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 如果列表为空，返回一个 nil 。
	 * 否则，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
	 * 
	 * @param key
	 * @param timeout
	 *            阻塞式 时间, 单位是秒
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
	 * 从右侧/表尾/队列尾取出 阻塞式 Redis Brpop 命令移出并获取列表的最后一个元素，
	 * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。 假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
	 * 反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
	 * 
	 * @param key
	 * @param timeout
	 *            阻塞式 时间, 单位是秒
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
	 * 
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
	 * 
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
	 * 
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
	
	
	/**
	 * Redis 的 Set 是 String 类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据。
	 * 当元素个数较少且都为整数时，内部编码为intset;
	 * 当元素个数超过512(默认)个时，或者类型不为整数时， 内部编码为hashtable(intset无法满足的时候).
	 * 通过object encoding key来判断类型
	 * 集合类型比较典型的使用场景是标签(tag)
	 */
	// 1.集合内的操作
	/**
	 * 向集合中添加一个或多个元素
	 * @param key
	 * @param members
	 * @return 添加成功的元素个数
	 */
	public static Long sadd(String key, String... members) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (members == null) {
				throw new RuntimeException("members不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.sadd(key, members);
		} catch (Exception e) {
			logger.error("#sadd key = " + key + ", members = " + members, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 向集合中添加一个或多个元素的重载方法
	 * @param key
	 * @param members
	 * @return 添加成功的元素个数
	 */
	public static Long sadd(byte[] key, byte[]... members) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (members == null) {
				throw new RuntimeException("members不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.sadd(key, members);
		} catch (Exception e) {
			logger.error("#sadd key = " + key + ", members = " + members, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 从集合中删除一个或者多个元素
	 * @param key
	 * @param members
	 * @return 成功删除元素的个数
	 */
	public static Long srem(String key, String... members) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (members == null) {
				throw new RuntimeException("members不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.srem(key, members);
		} catch (Exception e) {
			logger.error("#srem key = " + key + ", members = " + members, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 从集合中删除一个或者多个元素的重载方法
	 * @param key
	 * @param members
	 * @return 成功删除元素的个数
	 */
	public static Long srem(byte[] key, byte[]... members) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (members == null) {
				throw new RuntimeException("members不能存空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.srem(key, members);
		} catch (Exception e) {
			logger.error("#srem key = " + key + ", members = " + members, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 计算集合中元素个数
	 * @param key
	 * @return
	 */
	public static Long scard(String key) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.scard(key);
		} catch (Exception e) {
			logger.error("#scard key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 计算集合中元素个数的重载方法
	 * @param key
	 * @return
	 */
	public static Long scard(byte[] key) {
		Jedis jedis = null;
		Long num = 0L;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			num = jedis.scard(key);
		} catch (Exception e) {
			logger.error("#scard key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return num;
	}
	
	/**
	 * 判断元素否在集合中
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key, String member) {
		Jedis jedis = null;
		Boolean flag = false;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (member == null) {
				throw new RuntimeException("member不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			flag = jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("#sismember key = " + key + ", member = " + member, e);
		} finally {
			closeJedis(jedis);
		}
		return flag;
	}
	
	/**
	 * 判断元素否在集合中的重载方法
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(byte key[], byte[] member) {
		Jedis jedis = null;
		Boolean flag = false;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			if (member == null) {
				throw new RuntimeException("member不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			flag = jedis.sismember(key, member);
		} catch (Exception e) {
			logger.error("#sismember key = " + key + ", member = " + member, e);
		} finally {
			closeJedis(jedis);
		}
		return flag;
	}
	
	/**
	 * 随机从集合中返回1个元素
	 * @param key
	 * @return
	 */
	public static String srandmember(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.srandmember(key);
		} catch (Exception e) {
			logger.error("#srandmember key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 随机从集合中返回1个元素的重载方法
	 * @param key
	 * @return
	 */
	public static byte[] srandmember(byte[] key) {
		Jedis jedis = null;
		byte[] result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.srandmember(key);
		} catch (Exception e) {
			logger.error("#srandmember key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 返回count的绝对值个随机元素，可能重复。注意：1.count值大于等于集合本身拥有的元素个数时，全部返回。
	 * 2.count是负数时就返回它的绝对值个数，即使超出了集合本身拥有的元素个数。
	 * @param key
	 * @param count
	 * @return
	 */
	public static List<String> srandmember(String key, int count) {
		Jedis jedis = null;
		List<String> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.srandmember(key,count);
		} catch (Exception e) {
			logger.error("#srandmember key = " + key + ", count = " + count, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 返回count的绝对值个随机元素，可能重复。注意：1.count值大于等于集合本身拥有的元素个数时，全部返回。
	 * 2.count是负数时就返回它的绝对值个数，即使超出了集合本身拥有的元素个数。
	 * @param key
	 * @param count
	 * @return
	 */
	public static List<byte[]> srandmember(byte[] key, int count) {
		Jedis jedis = null;
		List<byte[]> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.srandmember(key,count);
		} catch (Exception e) {
			logger.error("#srandmember key = " + key + ", count = " + count, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 随机从集合中弹出1个元素，元素会从集合中删除，集合的长度减1
	 * @param key
	 * @return
	 */
	public static String spop(String key) {
		Jedis jedis = null;
		String result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.spop(key);
		} catch (Exception e) {
			logger.error("#spop key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 *随机从集合中弹出1个元素，元素会从集合中删除，集合的长度减1的重载方法
	 * @param key
	 * @return
	 */
	public static byte[] spop(byte[] key) {
		Jedis jedis = null;
		byte[] result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.spop(key);
		} catch (Exception e) {
			logger.error("#spop key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 随机从集合中弹出count个元素，元素会从集合中删除，集合的长度减count，可能重复。注意：1.count值大于等于集合本身拥有的元素个数时，全部返回。
	 * 2.count是负数时就返回它的绝对值个数，即使超出了集合本身拥有的元素个数。// 从版本3.2开始支持
	 * @param key
	 * @param count
	 * @return
	 */
	public static Set<String> spop(String key, long count) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.spop(key, count);
		} catch (Exception e) {
			logger.error("#spop key = " + key + ", count = " + count, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 随机从集合中弹出count个元素，元素会从集合中删除，集合的长度减count，可能重复。注意：1.count值大于等于集合本身拥有的元素个数时，全部返回。
	 * 2.count是负数时就返回它的绝对值个数，即使超出了集合本身拥有的元素个数。// 从版本3.2开始支持
	 * @param key
	 * @param count
	 * @return
	 */
	public static Set<byte[]> spop(byte[] key, int count) {
		Jedis jedis = null;
		Set<byte[]> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.spop(key,count);
		} catch (Exception e) {
			logger.error("#spop key = " + key + ", count = " + count, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 返回集合中所有的元素
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.smembers(key);
		} catch (Exception e) {
			logger.error("#smembers key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 返回集合中所有的元素的重载方法
	 * @param key
	 * @return
	 */
	public static Set<byte[]> smembers(byte[] key) {
		Jedis jedis = null;
		Set<byte[]> result = null;
		try {
			if (key == null) {
				throw new RuntimeException("key不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.smembers(key);
		} catch (Exception e) {
			logger.error("#smembers key = " + key, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	// 2.集合间的操作
	/**
	 * 求多个集合之间的交集
	 * @param keys
	 * @return
	 */
	public static Set<String> sinter(String... keys) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sinter(keys);
		} catch (Exception e) {
			logger.error("#sinter keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 求多个集合之间的交集的重载方法
	 * @param keys
	 * @return
	 */
	public static Set<byte[]> sinter(byte[]... keys) {
		Jedis jedis = null;
		Set<byte[]> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sinter(keys);
		} catch (Exception e) {
			logger.error("#sinter keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 求多个集合之间的并集
	 * @param keys
	 * @return
	 */
	public static Set<String> sunion(String... keys) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sunion(keys);
		} catch (Exception e) {
			logger.error("#sunion keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 求多个集合之间的并集的重载方法
	 * @param keys
	 * @return
	 */
	public static Set<byte[]> sunion(byte[]... keys) {
		Jedis jedis = null;
		Set<byte[]> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sunion(keys);
		} catch (Exception e) {
			logger.error("#sunion keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 求多个集合之间的差集,返回的时第一个集合的
	 * @param keys
	 * @return
	 */
	public static Set<String> sdiff(String... keys) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sdiff(keys);
		} catch (Exception e) {
			logger.error("#sdiff keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
	
	/**
	 * 求多个集合之间的差集的重载方法,返回的时第一个集合的
	 * @param keys
	 * @return
	 */
	public static Set<byte[]> sdiff(byte[]... keys) {
		Jedis jedis = null;
		Set<byte[]> result = null;
		try {
			if (keys == null) {
				throw new RuntimeException("keys不能是空对象");
			}
			jedis = jedisPool.getResource();
			jedis.select(DEFAULT_DB_INDEX);
			result = jedis.sdiff(keys);
		} catch (Exception e) {
			logger.error("#sdiff keys = " + keys, e);
		} finally {
			closeJedis(jedis);
		}
		return result;
	}
}
