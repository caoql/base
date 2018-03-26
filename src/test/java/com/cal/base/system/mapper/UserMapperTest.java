package com.cal.base.system.mapper;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.query.UserParam;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml","classpath:spring/applicationContext-redis.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testZDeleteByPrimaryKey() {
	}

	@Test
	public void testInsertSelective() {
	}
	
	@Test
	public void testBatchInsert() {
		List<UserPO> records = new LinkedList<UserPO>();
		UserPO user1 = new UserPO();
		user1.setAccount("测试账号1");
		user1.setName("测试名字1");
		
		UserPO user2 = new UserPO();
		user2.setAccount("测试账号2");
		user2.setName("测试名字2");
		
		records.add(user1);
		records.add(user2);
		
		int result = userMapper.batchInsert(records);
		Assert.assertEquals(2, result);
		for (UserPO user: records) {
			System.out.println(user.getUserId());
		}
	}

	@Test
	public void testSelectByPrimaryKey() {
		Assert.assertNotNull(userMapper);
		UserPO user = userMapper.selectByPrimaryKey(12L);
		userMapper.selectByPrimaryKey(12L);
		System.out.println(user);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

	@Test
	public void testListAll() {
		UserParam param = new UserParam();
		List<UserPO> list = userMapper.listAll(param);
		System.out.println(list);
	}

}
