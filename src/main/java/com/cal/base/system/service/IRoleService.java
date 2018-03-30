package com.cal.base.system.service;

import java.util.Map;
import java.util.Set;

public interface IRoleService {

	Map<String, Set<String>> selectResourceMapByUserId(String userId);

}
