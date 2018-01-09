package com.cal.base.common.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cal.base.system.entity.po.Role;
import com.cal.base.system.entity.po.User;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring/applicationContext-redis.xml"})
public class RedisClientTest {

	@Autowired
	private RedisClient redisClient;
	
	@Test
	public void test() {
		// Assert.assertNotNull(redisClient);
		// Assert.assertTrue(RedisClient.exists("test")); 
		// System.out.println(RedisClient.del("test", ""));
		// Assert.assertEquals(1, RedisClient.expire("name", 20));
		// RedisClient.set("test", 123, 20);
		// RedisClient.set("test", 123);
		// System.out.println(RedisClient.get("test"));
		// RedisClient.hset("test", "aa", "123");
		// System.out.println(RedisClient.hget("test", "bb"));
		// RedisClient.hdel("test", "aa");
		// RedisClient.set(null, "", 20);
		// RedisClient.lpush("list", " ", "123");
		// System.out.println(RedisClient.llen("list"));
		// System.out.println(RedisClient.lrange("list", 0, -1));
		// RedisClient.rpush("list", "1", "2", "3");
		// System.out.println(redisClient.lpop("list"));
		// System.out.println(redisClient.rpop("list"));
		// System.out.println("..." + redisClient.blpop("list", 10));
		// System.out.println("aaaaaaaaaa");
		// System.out.println("---" + redisClient.brpop("list", 5));
		Jedis j = new Jedis();
		j.auth("redis");
		Map<String, String> userMap = new HashMap<String, String>();
		
		Role r1 = new Role();
		String roleId1 = UUID.randomUUID().toString().replace("-", "");
		r1.setRoleId(roleId1);
		r1.setName("角色1");
		r1.setCreator("cal");
		userMap.put(roleId1, JSON.toJSONString(r1));
		
		Role r2 = new Role();
		String roleId2 = UUID.randomUUID().toString().replace("-", "");
		r2.setRoleId(roleId2);
		r2.setName("角色2");
		r2.setCreator("cal");
		userMap.put(roleId2, JSON.toJSONString(r2));
		
		j.hmset("ROLE", userMap);
		List<String> list = j.hmget("ROLE", roleId1);
		for (String s: list) {
			System.out.println(s);
		}
	}
}

