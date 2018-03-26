package com.cal.base.system.mapper;

import com.cal.base.system.entity.po.OrganizationPO;

/**
 * 组织mapper,主要操作system_organization
 * @author andyc 2018-3-15
 *
 */
public interface OrganizationMapper {

    int insertSelective(OrganizationPO record);
}