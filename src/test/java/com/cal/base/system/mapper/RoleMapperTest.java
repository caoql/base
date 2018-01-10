package com.cal.base.system.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cal.base.system.entity.po.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-dao.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleMapperTest {

	@Autowired
	private RoleMapper roleMapper;

	private static String roleId;

	@Test
	public void testZDeleteByPrimaryKey() {
		// roleId = "" // 数据库抓
		int actual = roleMapper.deleteByPrimaryKey(roleId);
		Assert.assertEquals(1, actual);
	}

	@Test
	public void testInsertSelective() {
		Role record = new Role();
		record.setName("测试角色J");
		record.setRemark("junit单元测试新增");
		int result = roleMapper.insertSelective(record);
		Assert.assertEquals(1, result);
		roleId = record.getRoleId();
		System.out.println("返回的主键角色ID:" + roleId);
	}

	@Test
	public void testBatchInsert() {
		List<Role> records = new LinkedList<Role>();
		Role record1 = new Role();
		record1.setRoleId(UUID.randomUUID().toString().replace("-", ""));
		record1.setName("测试角色B1" + Math.round(10000));
		record1.setRemark("junit单元测试批量新增");
		Role record2 = new Role();
		record2.setRoleId(UUID.randomUUID().toString().replace("-", ""));
		record2.setName("测试角色B2" + Math.round(10000));
		record2.setRemark("junit单元测试批量新增");
		records.add(record1);
		records.add(record2);

		int result = roleMapper.batchInsert(records);
		Assert.assertEquals(2, result);
		for (Role role : records) {
			System.out.println("批量新增的角色ID:" + role.getRoleId());
		}
	}

	@Test
	public void testSelectByPrimaryKey() {
		// roleId = "" // 数据库抓
		Role r = roleMapper.selectByPrimaryKey(roleId);
		Role r2 = roleMapper.selectByPrimaryKey(roleId);
		System.out.println(r == r2);
		System.out.println(r);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		Role record = new Role();
		// roleId = "" // 数据库抓
		record.setRoleId(roleId);
		record.setRemark("一起跑测试" + Math.round(10000));
		int actual = roleMapper.updateByPrimaryKeySelective(record);
		Assert.assertEquals(1, actual);
	}

	@Test
	public void testQueryPos() {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("remark", "junit");
		List<Role> result = roleMapper.queryPos(map);
		for (Role r : result) {
			System.out.println("查询出来的结果是："+ r);
		}
	}
}
