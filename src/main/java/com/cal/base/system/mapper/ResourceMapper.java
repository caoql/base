package com.cal.base.system.mapper;

import java.util.List;
import java.util.Map;

import com.cal.base.system.entity.dto.ResourceListDTO;
import com.cal.base.system.entity.po.ResourcePO;
import com.cal.base.system.entity.query.ResourceParam;

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

    //  资源界面数据展示
	List<ResourceListDTO> listAll(ResourceParam param);
	
	// 查询所有资源
	List<ResourcePO> queryAll(Map<String,Object> map);
}