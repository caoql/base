package com.cal.base.system.mapper;

import java.util.List;
import java.util.Map;

import com.cal.base.system.entity.dto.RoleListDTO;
import com.cal.base.system.entity.po.RolePO;
import com.cal.base.system.entity.query.RoleParam;

/**
 * 角色Mapper,主要操作system_role表
 * @author andyc
 *
 */
public interface RoleMapper {
	
    int deleteByPrimaryKey(String roleId);

    int insertSelective(RolePO record);
    
    int batchInsert(List<RolePO> records);

    RolePO selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(RolePO record);
    
    List<RolePO> queryPos(Map<String, Object> paramMap);

    // 根据角色ID获取资源列表
	List<Map<Object, String>> selectResourceListByRoleId(String roleId);

	// 角色界面数据展示
	List<RoleListDTO> listAll(RoleParam param);

	// 根据角色ID查资源ID
	List<String> selectResourceIdListByRoleId(String id);

	// 获取角色数据
	List<Map<String, Object>> selectRoleInfo(Map<String, Object> param);
}