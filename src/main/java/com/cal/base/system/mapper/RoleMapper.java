package com.cal.base.system.mapper;

import java.util.List;
import java.util.Map;

import com.cal.base.system.entity.po.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insertSelective(Role record);
    
    int batchInsert(List<Role> records);

    Role selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(Role record);
    
    List<Role> queryPos(Map<String, Object> paramMap);
}