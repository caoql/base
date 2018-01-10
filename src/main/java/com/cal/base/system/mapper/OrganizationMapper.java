package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.Organization;

public interface OrganizationMapper {
    int insert(Organization record);

    int insertSelective(Organization record);
}