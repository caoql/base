package com.cal.base.system.mapper;

import java.util.List;

import com.cal.base.system.entity.po.User;
import com.cal.base.system.entity.query.UserParam;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insertSelective(User record);
    
    int batchInsert(List<User> records);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

	List<User> listAll(UserParam param);
}