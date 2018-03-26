package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.DataCodePO;

/**
 * 码表Mapper，主要操作的表是system_data_code
 * 
 * @author andyc 2018-3-15
 */
public interface DataCodeMapper {

	int insertSelective(DataCodePO record);
}