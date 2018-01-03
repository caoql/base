package com.cal.base.system.service;

import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.system.entity.query.UserParam;

public interface IUserService {
	
	ResponsePageInfo listAll(UserParam param);
}
