package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.UserDataAuth;

public interface UserDataAuthMapper {
    int insert(UserDataAuth record);

    int insertSelective(UserDataAuth record);
}