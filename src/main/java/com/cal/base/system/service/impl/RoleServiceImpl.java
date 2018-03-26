package com.cal.base.system.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cal.base.system.entity.po.RolePO;
import com.cal.base.system.mapper.RoleMapper;
import com.cal.base.system.mapper.RoleResourceMapper;
import com.cal.base.system.mapper.UserRoleMapper;
import com.cal.base.system.service.IRoleService;

/**
 * 角色相关的逻辑业务处理Service
 * 
 * @author andyc 2018-3-15
 *
 */
@Service
public class RoleServiceImpl implements IRoleService {
	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleResourceMapper roleResourceMapper;

	// 获得角色和资源列表
	@Override
	public Map<String, Set<String>> selectResourceMapByUserId(Long userId) {
		Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
		List<String> roleIdList = userRoleMapper.selectRoleIdListByUserId(userId);
		Set<String> urlSet = new HashSet<String>();
		Set<String> roles = new HashSet<String>();
		for (String roleId : roleIdList) {
			List<Map<Object, String>> resourceList = roleMapper.selectResourceListByRoleId(roleId);
			if (resourceList != null) {
				for (Map<Object, String> map : resourceList) {
					if (map != null && StringUtils.isNotBlank(map.get("url"))) {
						urlSet.add(map.get("url"));
					}
				}
			}
			RolePO role = roleMapper.selectByPrimaryKey(roleId);
			if (role != null) {
				roles.add(role.getName());
			}
		}
		resourceMap.put("urls", urlSet);
		resourceMap.put("roles", roles);
		return resourceMap;
	}

}
