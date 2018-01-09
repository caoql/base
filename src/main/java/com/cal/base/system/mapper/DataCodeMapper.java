package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.DataCode;

public interface DataCodeMapper {
    int insert(DataCode record);

    int insertSelective(DataCode record);
}