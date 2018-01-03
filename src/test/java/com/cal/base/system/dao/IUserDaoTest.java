package com.cal.base.system.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cal.base.system.entity.po.User;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml"})
public class IUserDaoTest {
	
	@Autowired
	private IUserDao userDao;

	@Test
	public void testDeleteByPrimaryKey() {
		String userId = "12";
		int actual = userDao.deleteByPrimaryKey(userId);
		Assert.assertEquals(1, actual);
		
	}

	@Test
	public void testInsertSelective() {
	}

	@Test
	public void testSelectByPrimaryKey() {
		Assert.assertNotNull(userDao);
		String userId = "12";
		User user = userDao.selectByPrimaryKey(userId);
		System.out.println(user);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
	}

}
