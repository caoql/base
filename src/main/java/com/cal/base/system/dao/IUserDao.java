package com.cal.base.system.dao;

import java.util.List;

import com.cal.base.system.entity.po.User;
import com.cal.base.system.entity.query.UserParam;

public interface IUserDao {
    int deleteByPrimaryKey(String userId);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

	List<User> listAll(UserParam param);
}