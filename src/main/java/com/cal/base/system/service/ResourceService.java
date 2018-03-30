package com.cal.base.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.info.Tree;
import com.cal.base.common.util.page.PageUtil;
import com.cal.base.common.util.web.WebUtil;
import com.cal.base.system.entity.dto.ResourceListDTO;
import com.cal.base.system.entity.po.ResourcePO;
import com.cal.base.system.entity.query.ResourceParam;
import com.cal.base.system.entity.vo.ResourceVO;
import com.cal.base.system.mapper.ResourceMapper;

/**
 * 组织相关的业务处理Service
 * 
 * @author andyc 2018-3-15
 *
 */
@Service
public class ResourceService {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	// 注入组织Mapper
	@Autowired
	private ResourceMapper resourceMapper;

	/**
	 * 资源界面数据展示
	 * 
	 * @param param
	 * @return
	 */
	public ResponsePageInfo listAll(ResourceParam param) {
		ResponsePageInfo info = new ResponsePageInfo();
		// 1.请求参数封装
		PageUtil.pageBefore(info, param);

		// 2.调用dao执行方法
		List<ResourceListDTO> list = resourceMapper.listAll(param);

		// 3.返回数据封装
		PageUtil.pageAfter(info, list);

		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

	/**
	 * 资源新增数据保存
	 * 
	 * @param addVo
	 * @return
	 */
	public boolean saveResource(ResourceVO addVo) {
		// 校验？
		ResourcePO record = addVo.toResourcePO();
		// 跟踪信息-record不会是null
		CurrentUserInfo redisUser = WebUtil.getRedisUser();
		if (redisUser != null) {
			record.setCreator(redisUser.getAccount());
		}
		record.setCreateTime(new Date());

		int result = resourceMapper.insertSelective(record);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Tree> selectAllTree() {
		// 获取所有的资源 tree形式，展示
		List<Tree> trees = new ArrayList<Tree>();
		List<ResourcePO> resources = this.selectAll();
		if (resources == null) {
			return trees;
		}
		for (ResourcePO resource : resources) {
			Tree tree = new Tree();
			tree.setId(resource.getResourceId());
			tree.setPid(resource.getPid());
			tree.setText(resource.getName());
			tree.setAttributes(resource.getUrl());
			trees.add(tree);
		}
		return trees;
	}

	public List<ResourcePO> selectAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		return resourceMapper.queryAll(map);
	}
}
