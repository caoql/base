package com.cal.base.system.dao;

import com.cal.base.system.entity.po.UserOrganization;

public interface UserOrganizationMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserOrganization record);

    int insertSelective(UserOrganization record);

    UserOrganization selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserOrganization record);

    int updateByPrimaryKey(UserOrganization record);
}