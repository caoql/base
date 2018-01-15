package com.cal.base.common.cache;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring/applicationContext-redis.xml"})
public class RedisClientTest {

	@Autowired
	private RedisClient redisClient;
	
	// 3.测试列表
	@Test
	public void testList() {
		Assert.assertNotNull("redis客户端为空", redisClient);
		
		// 测试string
		//RedisClient.setnx("name", 444);
		
		// 测试hash
		/*RedisClient.del("user:info:12");
		RedisClient.hset("user:info:12", "name", "caoql");
		System.out.println(RedisClient.hget("user:info:12", "name"));
		RedisClient.hdel("user:info:12", "name");
		System.out.println(RedisClient.hlen("user:info:12"));
		RedisClient.hset("user:info:12", "age", 26);*/
	}
	
	// 4.测试集合Set
	@Test
	public void testSet() {
		// 4.1.1测试添加元素的两种方法
		RedisClient.del("myset");
		long actual = RedisClient.sadd("myset", "1", "2", "3");
		Assert.assertEquals(3, actual);
		RedisClient.del("myset2");
		long actual2 = RedisClient.sadd("myset2".getBytes(), "a".getBytes(), "b".getBytes());
		Assert.assertEquals(2, actual2);
		
		// 4.1.2测试删除元素的两种方法
		long r1 = RedisClient.srem("myset", "1", "2");
		Assert.assertEquals(2, r1);
		long r2 = RedisClient.srem("myset2".getBytes(), "a".getBytes());
		Assert.assertEquals(1, r2);
		
		// 4.1.3测试集合中元素个数的两种方法
		long t1 = RedisClient.scard("myset");
		Assert.assertEquals(1, t1);
		long t2 = RedisClient.scard("myset2".getBytes());
		Assert.assertEquals(1, t2);
		
		// 4.1.4测试判断元素否在集合中
		boolean b1 = RedisClient.sismember("myset", "1");
		Assert.assertEquals(false, b1);
		boolean b2 = RedisClient.sismember("myset2".getBytes(), "b".getBytes());
		Assert.assertEquals(true, b2);
		
		// 4.1.5测试随机从集合中返回指定个数元素
		String ran = RedisClient.srandmember("myset");
		System.out.println(ran);
		byte[] ran2 = RedisClient.srandmember("myset2".getBytes());
		System.out.println(ran2);
		RedisClient.del("db");
		RedisClient.sadd("db", "oracle", "mysql", "redis", "mongedb");
		System.out.println(RedisClient.srandmember("db", 0));
		System.out.println(RedisClient.srandmember("db", 5));
		System.out.println(RedisClient.srandmember("db", -5));
		RedisClient.del("yy".getBytes());
		RedisClient.sadd("yy".getBytes(), "java".getBytes(), "js".getBytes(), "python".getBytes(), "shell".getBytes());
		System.out.println(RedisClient.srandmember("yy".getBytes(), 0));
		System.out.println(RedisClient.srandmember("yy".getBytes(), 6));
		System.out.println(RedisClient.srandmember("yy".getBytes(), -6));
		
		// 4.1.6测试从集合中弹出元素
		System.out.println(RedisClient.spop("db"));
		System.out.println(RedisClient.scard("db"));
		System.out.println(RedisClient.spop("yy".getBytes()));
		System.out.println(RedisClient.scard("yy".getBytes()));
		System.out.println(RedisClient.spop("db", 2));
		System.out.println(RedisClient.scard("db"));
		System.out.println(RedisClient.spop("yy".getBytes(), 3));
		System.out.println(RedisClient.scard("yy".getBytes()));
		
		// 4.1.7测试集合中的所有元素
		RedisClient.del("db");
		long tt1 = RedisClient.sadd("db", "oracle", "mysql", "redis", "mongedb");
		Assert.assertEquals(4, tt1);
		RedisClient.del("yy".getBytes());
		long tt2 = RedisClient.sadd("yy".getBytes(), "java".getBytes(), "js".getBytes(), "python".getBytes(), "shell".getBytes());
		Assert.assertEquals(4, tt2);
		
		// 4.2.1测试集合之间的交集
		RedisClient.del("hobby1");
		RedisClient.sadd("hobby1", "java", "mysql", "ball", "book");
		RedisClient.del("hobby2");
		RedisClient.sadd("hobby2", "js", "mysql", "linux", "ball", "git");
		Set<String> inter = RedisClient.sinter("hobby1", "hobby2");
		for (String i:inter) {
			System.out.println(i);
		}
		RedisClient.del("hobby3");
		RedisClient.sadd("hobby3".getBytes(), "java".getBytes(), "mysql".getBytes(), "ball".getBytes(), "book".getBytes());
		RedisClient.del("hobby4");
		RedisClient.sadd("hobby4".getBytes(), "js".getBytes(), "mysql".getBytes(), "linux".getBytes(), "ball".getBytes(), "git".getBytes());
		Set<byte[]> inter2 = RedisClient.sinter("hobby3".getBytes(), "hobby4".getBytes());
		Assert.assertEquals(2, inter2.size());
		
		// 4.2.2测试集合之间的并集
		RedisClient.del("hobby1");
		RedisClient.sadd("hobby1", "java", "mysql", "ball", "book");
		RedisClient.del("hobby2");
		RedisClient.sadd("hobby2", "js", "mysql", "linux", "ball", "git");
		Set<String> union = RedisClient.sunion("hobby1", "hobby2");
		for (String i:union) {
			System.out.println(i);
		}
		RedisClient.del("hobby3");
		RedisClient.sadd("hobby3".getBytes(), "java".getBytes(), "mysql".getBytes(), "ball".getBytes(), "book".getBytes());
		RedisClient.del("hobby4");
		RedisClient.sadd("hobby4".getBytes(), "js".getBytes(), "mysql".getBytes(), "linux".getBytes(), "ball".getBytes(), "git".getBytes());
		Set<byte[]> union2 = RedisClient.sunion("hobby3".getBytes(), "hobby4".getBytes());
		Assert.assertEquals(7, union2.size());
		
		// 4.2.3测试集合之间的差集
		RedisClient.del("hobby1");
		RedisClient.sadd("hobby1", "java", "mysql", "ball", "book");
		RedisClient.del("hobby2");
		RedisClient.sadd("hobby2", "js", "mysql", "linux", "ball", "git");
		Set<String> diff = RedisClient.sdiff("hobby1", "hobby2");
		for (String i:diff) {
			System.out.println(i);
		}
		RedisClient.del("hobby3");
		RedisClient.sadd("hobby3".getBytes(), "java".getBytes(), "mysql".getBytes(), "ball".getBytes(), "book".getBytes());
		RedisClient.del("hobby4");
		RedisClient.sadd("hobby4".getBytes(), "js".getBytes(), "mysql".getBytes(), "linux".getBytes(), "ball".getBytes(), "git".getBytes());
		Set<byte[]> diff2 = RedisClient.sdiff("hobby3".getBytes(), "hobby4".getBytes());
		Assert.assertEquals(2, diff2.size());
	}
	
	// 5.测试有序集合
	@Test
	public void testZsort() {
		// 5.1测试添加元素
		RedisClient.del("zkey");
		long actual = RedisClient.zadd("zkey", 100, "caoql");
		Assert.assertEquals(1, actual);
		
		// 5.2测试成员个数
		long t = RedisClient.zcard("zkey");
		Assert.assertEquals(1, t);
		
		// 5.3测试某个成员的分数
		double s = RedisClient.zscore("zkey", "caoql");
		System.out.println(s);
		
		// 5.4测试删除成员
		long r = RedisClient.zrem("zkey", "caoql");
		Assert.assertEquals(1, r);
		
		// 5.5测试计算成员的排名
		RedisClient.del("star");
		RedisClient.zadd("star", 95, "curry");
		RedisClient.zadd("star", 97, "kd");
		RedisClient.zadd("star", 100, "jorden");
		RedisClient.zadd("star", 99, "lbj");
		RedisClient.zadd("star", 98, "kobe");
		Assert.assertEquals(0, RedisClient.zrank("star", "curry"));
		Assert.assertEquals(4, RedisClient.zrevrank("star", "curry"));
		
		// 5.6测试增加成员的分数
		System.out.println(RedisClient.zincrby("star", 2, "curry"));
	}
}

