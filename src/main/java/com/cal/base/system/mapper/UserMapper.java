package com.cal.base.system.mapper;

import java.util.List;
import java.util.Map;

import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.query.UserParam;

/**
 * 用户Mapper,主要操作的是system_user
 * @author andyc 2018-3-15
 *
 */
public interface UserMapper {
	// 删除用户：通过用户ID
    int deleteByPrimaryKey(String userId);

    int insertSelective(UserPO record);
    
    int batchInsert(List<UserPO> records);

    // 查看用户信息：通过主键-用户ID
    UserPO selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(UserPO record);

	List<UserPO> listAll(UserParam param);

	List<UserPO> selectByLoginName(String username);

	// 通过用户信息获取资源列表
	List<Map<String, Object>> getResources(Long userId);
}