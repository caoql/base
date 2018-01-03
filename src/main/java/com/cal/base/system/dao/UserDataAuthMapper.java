package com.cal.base.system.dao;

import com.cal.base.system.entity.po.UserDataAuth;

public interface UserDataAuthMapper {
    int insert(UserDataAuth record);

    int insertSelective(UserDataAuth record);
}