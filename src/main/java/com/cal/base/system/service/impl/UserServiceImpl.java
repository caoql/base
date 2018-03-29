package com.cal.base.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cal.base.SystemConstant;
import com.cal.base.common.cache.RedisClient;
import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.excel.ExcelUtil;
import com.cal.base.common.excel.ExcelUtil2;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.exception.ServiceException;
import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.util.file.FileUtil;
import com.cal.base.common.util.idgen.UUIDUtil;
import com.cal.base.common.util.page.PageUtil;
import com.cal.base.system.entity.dto.UserListDTO;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.query.UserParam;
import com.cal.base.system.entity.vo.UserVO;
import com.cal.base.system.mapper.UserMapper;
import com.cal.base.system.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	// 注入用户Mapper
	@Autowired
	private UserMapper userMapper;

	/**
	 * 用户管理列表数据展示
	 */
	@Override
	public ResponsePageInfo listAll(UserParam param) {
		ResponsePageInfo info = new ResponsePageInfo();
		// 1.请求参数封装
		PageUtil.pageBefore(info, param);

		// 2.调用dao执行方法
		List<UserListDTO> list = userMapper.listAll(param);

		// 3.返回数据封装
		PageUtil.pageAfter(info, list);

		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

	/**
	 * 添加用户
	 */
	@Override
	public boolean insertUser(UserVO addVo) {
		// 校验？
		UserPO record = addVo.toUserPO();
		int result = userMapper.insertSelective(record);
		logger.debug("插入的生成的用户id是" + record.getUserId());
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 编辑用户
	 */
	@Override
	public boolean updateUser(UserVO editVo) {
		// 校验
		if (editVo == null || StringUtils.isBlank(editVo.getUserId())) {
			throw new ServiceException("userId不能为空");
		}
		UserPO record = editVo.toUserPO();
		int result = userMapper.updateByPrimaryKeySelective(record);
		// 清除缓存
		String userRedisKey = SystemConstant.REDIS_USER_PRE +  editVo.getUserId();
		long l = RedisClient.del(userRedisKey);
		logger.debug("redis清除缓存用户id:" + editVo.getUserId() + "返回的结果是>>>"+ l);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 查看
	 */
	// 这个是redis查询缓存的应用场景
	@Override
	public UserPO queryUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new ServiceException("用户ID不能为空");
		}
		String userRedisKey = SystemConstant.REDIS_USER_PRE + userId;
		UserPO user = (UserPO) RedisClient.get(userRedisKey);
		if (user == null) {
			user = userMapper.selectByPrimaryKey(userId);
			if (user != null) {
				RedisClient.set(userRedisKey, user, 60 * 60);// 失效时间设置为一个小时
			}
		}
		return user;
	}

	@Override
	public List<UserPO> selectByLoginName(String username) {
		if (username == null) {
			throw new ServiceException("username不能为空");
		}

		return userMapper.selectByLoginName(username);
	}

	/**
	 * 通过主键用户ID删除用户
	 */
	@Override
	public boolean deleteByPrimaryKey(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw new ServiceException("用户ID不能为空");
		}
		int result = userMapper.deleteByPrimaryKey(userId);
		// 清除缓存
		String userRedisKey = SystemConstant.REDIS_USER_PRE + userId;
		Long l = RedisClient.del(userRedisKey);
		logger.debug("redis删除用户id:" + userId + "返回的结果是>>>"+ l);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 通过用户信息获取资源列表
	@Override
	public ResponseInfo getResourcesByUserid(Long userId) {
		ResponseInfo info = new ResponseInfo();
		List<Map<String, Object>> resultList = userMapper.getResources(userId);
		List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
		// 对数据进行处理
		if (resultList != null && resultList.size() > 0) {
			int len = resultList.size();
			for (int i = 0; i < len; i++) {
				Map<String, Object> map = resultList.get(i);
				// 第一轮先拿出主菜单
				if (map.get("pid") == null) {
					map.put("level", 0);
					infoList.add(map);
				}
			}
		}
		for (Map<String, Object> m : infoList) {
			String resourceId = (String) m.get("id");
			List<Map<String, Object>> childMenu = new ArrayList<Map<String, Object>>();
			if (resultList != null && resultList.size() > 0) {
				int len = resultList.size();
				for (int i = 0; i < len; i++) {
					Map<String, Object> r = resultList.get(i);
					// 第二轮拿出子菜单
					if (resourceId.equals(r.get("pid"))) {
						r.put("level", 1);// 菜单级别
						childMenu.add(r);
					}
				}
			}
			m.put("menus", childMenu);
		}

		info.data = infoList;
		info.code = ErrorCodeEnum.CALL_SUCCESS.getCode();
		info.msg = ErrorCodeEnum.CALL_SUCCESS.getMsg();

		return info;
	}

	// 导出
	@Override
	public void export(UserParam param, String exportName,
			HttpServletResponse response) throws Exception {
		List<UserListDTO> list = userMapper.listAll(param);
		ExcelUtil.export(list, exportName, response);
	}

	// 批量导入
	@Override
	@Transactional
	public boolean batchImport(HttpServletRequest request) {
		List<String> filesPath = null;
		try {
			filesPath = FileUtil.upload(request,"xls","xlsx");// excel格式
		}  catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			throw new CommonException("文件上传失败");
		}
		if (filesPath != null && filesPath.size() > 0) {
			for (String path : filesPath) {
				try {
					ExcelUtil2 eu = new ExcelUtil2();  
					eu.setExcelPath(path);  
					eu.setStartReadPos(1); //从第2行读取
					List<String[]> list =  eu.readExcel();
					// 删除临时文件
					File file = new File(path);
					if (file.exists()) {
						file.delete();
					}
					return batchInsert(list);
				} catch (Exception e) {
					throw new CommonException("文件解析失败");
				}
			}
		}
		return true;
	}
	
	private boolean batchInsert(List<String[]> rowlist) {
		List<UserPO> records = new ArrayList<UserPO>();
		if (rowlist != null) {
			for (String[] row : rowlist) {
				UserPO po = new UserPO();
				// 数据校验还没做？
				po.setUserId(UUIDUtil.getUUID());
				po.setAccount(row[0]);
				po.setName(row[1]);
				po.setPhone(row[2]);
				if ("男".equals(row[3])) {
					po.setSex("m");
				} else if ("女".equals(row[3])) {
					po.setSex("w");
				} else {
					po.setSex(row[3]);
				}
				records.add(po);
			}
		}
		int result = userMapper.batchInsert(records);
		if (result > 0){
			return true;
		}
		return false;
	}

}
