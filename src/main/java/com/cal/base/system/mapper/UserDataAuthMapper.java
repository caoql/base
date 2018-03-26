package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.UserDataAuthPO;

/**
 * 用户数据权限Mapper,操作的主要是system_user_data_auth表
 * 
 * @author andyc 2018-3-15
 *
 */
public interface UserDataAuthMapper {
	int insertSelective(UserDataAuthPO record);
}