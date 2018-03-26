package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.ResourcePO;

/**
 * 资源Mapper,主要操作的是system_resource表
 * @author andyc 2018-3-15
 *
 */
public interface ResourceMapper {
    int deleteByPrimaryKey(String resourceId);

    int insertSelective(ResourcePO record);

    ResourcePO selectByPrimaryKey(String resourceId);

    int updateByPrimaryKeySelective(ResourcePO record);
}