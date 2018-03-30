package com.cal.base.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<ResourcePO> resources =  resourceMapper.queryAll(map);
		// 对数据进行处理
        if (resources != null && resources.size() > 0) {
            int len = resources.size();
            for (int i = 0; i < len; i++) {
                ResourcePO po = resources.get(i);
                // 第一轮先拿出主菜单
                if (StringUtils.isBlank(po.getPid())) {
                    // 封装树形数据一级结构
                    Tree tree = new Tree();
                    tree.setId(po.getResourceId());
                    tree.setText(po.getName());
                    tree.setAttributes(po.getUrl());
                    trees.add(tree);
                }
            }
        }
        loopChilldTree(resources, trees);
		return trees;
	}

	private void loopChilldTree(List<ResourcePO> resultList, List<Tree> trees) {
        for (Tree m : trees) {
            String resourceId = m.getId();
            List<Tree> childMenu = new ArrayList<Tree>();
            if (resultList != null && resultList.size() > 0) {
                int len = resultList.size();
                for (int i = 0; i < len; i++) {
                    ResourcePO r = resultList.get(i);
                    // 第二轮拿出子菜单
                    if (resourceId.equals(r.getPid())) {
                        Tree tree = new Tree();
                        tree.setId(r.getResourceId());
                        tree.setText(r.getName());
                        tree.setPid(r.getPid());
                        tree.setAttributes(r.getUrl());
                        childMenu.add(tree);
                    }
                }
                loopChilldTree(resultList, childMenu);
            }
            m.setChildren(childMenu);
        }
    }
}
