package com.cal.base.system.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cal.base.system.entity.vo.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml","classpath:spring/applicationContext-common.xml","classpath:spring/applicationContext-transaction.xml" })
public class IUserServiceTest {
	
	@Autowired
	private IUserService service;

	@Test
	public void testListAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertUser() {
		UserVO addVo = new UserVO();
		addVo.setAccount("aa");
		addVo.setName("junit测试名字");
		boolean result = service.insertUser(addVo);
		assertTrue(result);
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByLoginName() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetResourcesByUserid() {
		fail("Not yet implemented");
	}

	@Test
	public void testExport() {
		fail("Not yet implemented");
	}

}
