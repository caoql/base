package com.cal.base.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cal.base.system.mapper.OrganizationMapper;
import com.cal.base.system.service.IOrganizationService;

/**
 * 组织相关的业务处理Service
 * 
 * @author andyc 2018-3-15
 *
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {
	
	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());
	
	// 注入组织Mapper
	@Autowired
	private OrganizationMapper organizationMapper;
}
