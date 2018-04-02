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
import com.cal.base.common.exception.ServiceException;
import com.cal.base.common.info.CurrentUserInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.info.Tree;
import com.cal.base.common.util.web.WebUtil;
import com.cal.base.system.entity.dto.ResourceListDTO;
import com.cal.base.system.entity.po.ResourcePO;
import com.cal.base.system.entity.query.ResourceParam;
import com.cal.base.system.entity.vo.ResourceVO;
import com.cal.base.system.mapper.ResourceMapper;
import com.cal.base.system.mapper.RoleResourceMapper;

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

	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	/**
	 * 资源界面数据展示
	 * 
	 * @param param
	 * @return
	 */
	public ResponsePageInfo listAll(ResourceParam param) {
		ResponsePageInfo info = new ResponsePageInfo();
		List<ResourceListDTO> resultList = resourceMapper.listAll(param);
		List<ResourceListDTO> infoList = new ArrayList<ResourceListDTO>();
		// 对数据进行处理
				if (resultList != null && resultList.size() > 0) {
					int len = resultList.size();
				    for (int i = 0; i < len; i++) {
				    	ResourceListDTO o = resultList.get(i);
				    	//第一轮先拿出主菜单
				    	if (StringUtils.isBlank(o.getPid())) {
				    		//封装树形数据一级结构
				    		infoList.add(o);
				    	} 
				    }
				}
				loopChildList(resultList, infoList);
				// 组织过滤后期待优化？
		        if (infoList.size() > 0) {
		        	info.rows = infoList;
		        } else {
		        	info.rows = resultList;
		        }

		info.setErrorInfo(ErrorCodeEnum.CALL_SUCCESS);
		return info;
	}

	/**
     * 做组织的datagrid展示
     * @param resultList
     * @param infoList
     */
    private void loopChildList(List<ResourceListDTO> resultList,
			List<ResourceListDTO> infoList) {
		for (ResourceListDTO m: infoList) {
			String code = m.getResourceId();
			List<ResourceListDTO> childMenu = new ArrayList<ResourceListDTO>();
			if (resultList != null && resultList.size() > 0) {
				int len = resultList.size();
			    for (int i = 0; i < len; i++) {
			    	ResourceListDTO r = resultList.get(i);
			    	//第二轮拿出子菜单
			    	if (code.equals(r.getPid())) {
				        childMenu.add(r);
			    	} 
			    }
			    loopChildList(resultList, childMenu);//循环迭代下一个
			}
			m.setChildren(childMenu);
		}
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

	/**
	 * 资源删除
	 * @param resourceId
	 * @return
	 */
	public boolean deleteResource(String resourceId) {
		// 1.先校验
		if (StringUtils.isBlank(resourceId)) {
			throw new ServiceException("资源ID不能为空");
		}
		int num = 0;
	    List<String> ids = getIds(resourceId);
	    // 校验这些资源是否被引用
	    if (ids != null && ids.size() > 0) {
	    	int result = roleResourceMapper.selectPosByIds(ids);
	    	if (result > 0) {
	    		throw new ServiceException("资源被引用，不能删除");
	    	}
	    	num = resourceMapper.deleteByIds(ids);
	    }
	    if (num > 0) {
	    	return true;
	    }
		return false;
	}

	// 获取资源及其后代的resourceId
	private List<String> getIds(String resourceId) {
		List<String> ids = new ArrayList<String>();
		// 通过id查询code信息
		ResourcePO po = resourceMapper.selectByPrimaryKey(resourceId);
    	loopResourceId(ids, po);
		return ids;
	}

	private void loopResourceId(List<String> ids, ResourcePO po) {
		if (po != null && StringUtils.isNoneBlank(po.getResourceId())) {
    		ids.add(po.getResourceId());
    		// 1.通过resourceId转化成pid查询所有的子pid和子resourceId
    		Map<String, Object> map = new HashMap<>();
    		map.put("pid", po.getResourceId());
    		List<ResourcePO> pos = resourceMapper.queryAll(map);
    		if (pos != null && pos.size() > 0) {
    			// 2.子resourceId添加，子pid继续重复第二步
    			for (ResourcePO item : pos) {
    				loopResourceId(ids, item);
    			}
    		}
    	}
	}
}
