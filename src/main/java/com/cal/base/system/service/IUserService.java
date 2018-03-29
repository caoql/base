package com.cal.base.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.query.UserParam;
import com.cal.base.system.entity.vo.UserVO;

public interface IUserService {
	
	ResponsePageInfo listAll(UserParam param);

	boolean insertUser(UserVO addVo);
	
	boolean updateUser(UserVO editVo);

	// 查看
	UserPO queryUser(String userId);

	List<UserPO> selectByLoginName(String username);
	
	// 删除用户
	boolean deleteByPrimaryKey(String userId);

	// 通过用户信息获取资源列表
	ResponseInfo getResourcesByUserid(Long userId);

	// 导出
	void export(UserParam param, String exportName, HttpServletResponse response) throws Exception;

	// 批量导入
	boolean batchImport(HttpServletRequest request);
}
