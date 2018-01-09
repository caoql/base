package com.cal.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.util.page.PageUtil;
import com.cal.base.system.entity.po.User;
import com.cal.base.system.entity.query.UserParam;
import com.cal.base.system.mapper.UserMapper;
import com.cal.base.system.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public ResponsePageInfo listAll(UserParam param) {
		ResponsePageInfo info = new ResponsePageInfo();
		//1.请求参数封装
        PageUtil.pageBefore(info, param);
        
        //2.调用dao执行方法
        List<User> list = userMapper.listAll(param);
        
        //3.返回数据封装
        PageUtil.pageAfter(info, list);
        
        info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
        return info;
	}

	@Override
	public ResponseInfo insertUser(User addVo) {
		ResponseInfo info = new ResponseInfo();
		userMapper.insertSelective(addVo);
		info.data = addVo.getUserId();
		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

}
