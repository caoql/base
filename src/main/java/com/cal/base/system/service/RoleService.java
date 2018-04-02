package com.cal.base.system.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.util.page.PageUtil;
import com.cal.base.common.util.web.WebUtil;
import com.cal.base.system.entity.dto.RoleListDTO;
import com.cal.base.system.entity.po.RolePO;
import com.cal.base.system.entity.po.RoleResourcePO;
import com.cal.base.system.entity.query.RoleParam;
import com.cal.base.system.entity.vo.RoleVO;
import com.cal.base.system.mapper.RoleMapper;
import com.cal.base.system.mapper.RoleResourceMapper;
import com.cal.base.system.mapper.UserRoleMapper;

/**
 * 角色相关的逻辑业务处理Service
 * 
 * @author andyc 2018-3-15
 *
 */
@Service
public class RoleService {
	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Autowired
	private RoleResourceMapper roleResourceMapper;

	// 获得角色和资源列表
	public Map<String, Set<String>> selectResourceMapByUserId(String userId) {
		Map<String, Set<String>> resourceMap = new HashMap<String, Set<String>>();
		List<String> roleIdList = userRoleMapper
				.selectRoleIdListByUserId(userId);
		Set<String> urlSet = new HashSet<String>();
		Set<String> roles = new HashSet<String>();
		for (String roleId : roleIdList) {
			List<Map<Object, String>> resourceList = roleMapper
					.selectResourceListByRoleId(roleId);
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

	/**
	 * 角色界面数据展示
	 * 
	 * @param param
	 * @return
	 */
	public ResponsePageInfo listAll(RoleParam param) {
		ResponsePageInfo info = new ResponsePageInfo();
		// 1.请求参数封装
		PageUtil.pageBefore(info, param);

		// 2.调用dao执行方法
		List<RoleListDTO> list = roleMapper.listAll(param);

		// 3.返回数据封装
		PageUtil.pageAfter(info, list);

		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

	/**
	 * 新增
	 * 
	 * @param addVo
	 * @return
	 */
	public boolean saveRole(RoleVO addVo) {
		// 校验？
		RolePO record = addVo.toRolePO();
		// 跟踪信息-record不会是null
		CurrentUserInfo redisUser = WebUtil.getRedisUser();
		if (redisUser != null) {
			record.setCreator(redisUser.getAccount());
		}
		record.setCreateTime(new Date());

		int result = roleMapper.insertSelective(record);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> selectResourceIdListByRoleId(String id) {
		return roleMapper.selectResourceIdListByRoleId(id);
	}

	@Transactional
	public void updateRoleResource(String roleId, String resourceIds) {
		// 先删除后添加,有点爆力
		roleResourceMapper.deleteByRoleId(roleId);

		String[] resourceIdArray = resourceIds.split(",");
		for (String resourceId : resourceIdArray) {
			RoleResourcePO roleResource = new RoleResourcePO();
			roleResource.setRoleId(roleId);
			roleResource.setResourceId(resourceId);
			roleResourceMapper.insertSelective(roleResource);
		}
	}
	
	
	public List<Map<String,Object>> queryUserRole(Map<String,Object> param) {
		return roleMapper.selectRoleInfo(param);
	}

	// 根据用户ID获取角色信息
	public List<Map<String, Object>> queryUserRoleByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new CommonException(ErrorCodeEnum.PARAM_IS_NULL);
		}
		return userRoleMapper.queryUserRoleByUserId(userId);
	}

}
