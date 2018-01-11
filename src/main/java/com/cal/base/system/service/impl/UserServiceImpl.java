package com.cal.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cal.base.common.cache.RedisClient;
import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.ServiceException;
import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.util.page.PageUtil;
import com.cal.base.system.SystemConstant;
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

	// 这个是redis查询缓存的应用场景
	@Override
	public ResponseInfo queryUser(Long userId) {
		if (userId == null) {
			throw new ServiceException("userId不能为空");
		}
		ResponseInfo info = new ResponseInfo();
		
		String userRedisKey = SystemConstant.REDIS_USER_PRE + userId;
		User user = (User)RedisClient.get(userRedisKey);
		if (user == null) {
			user = userMapper.selectByPrimaryKey(userId);
			if (user != null) {
				RedisClient.set(userRedisKey, user, 60*60);// 失效时间设置为一个小时
			}
		}
		info.data = user;
		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

}
