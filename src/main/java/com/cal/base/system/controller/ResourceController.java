package com.cal.base.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cal.base.SystemConstant;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.system.entity.po.ResourcePO;
import com.cal.base.system.entity.query.ResourceParam;
import com.cal.base.system.entity.vo.ResourceVO;
import com.cal.base.system.service.ResourceService;

/**
 * 资源管理控制器
 * 
 * @author andyc 2018-3-15
 *
 */
@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	// 注入资源Service
	@Autowired
	private ResourceService resourceService;

	@GetMapping("/manager")
	public String manager() {
		return "admin/resource/resource";
	}

    /**
     * 资源界面数据展示
     * @param param
     * @return
     */
    @PostMapping("/list")
	@ResponseBody
	public ResponsePageInfo list(ResourceParam param) {
    	logger.debug("资源界面数据展示查询数据是：" + param);
		return resourceService.listAll(param);
	}
    
    /**
     * 资源新增页面展示
     * @return
     */
    @GetMapping("/addpage")
	public String showAddPage() {
		return "admin/resource/resourceAdd";
	}
    
    /**
     * 资源新增数据保存
     * @param addVo
     * @param result
     * @return
     */
    @PostMapping("/add")
	@ResponseBody
	public Object saveResource(@Validated ResourceVO addVo, BindingResult result) {
    	logger.debug(" 资源新增数据保存数据是：" + addVo);
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		boolean flag = resourceService.saveResource(addVo);
		if (!flag) {
			return renderError("添加失败！");
		}
		return renderSuccess("添加成功！");
	}
    
    /**
     * 
     * 查询所有的资源tree
     */
    @RequestMapping("/allTrees")
    @ResponseBody
    public Object allTree() {
        return resourceService.selectAllTree();
    }
    
    /**
     * 只有管理员有权限删除
     * 资源删除
     * @param resourceId
     * @return
     */ 
    @RequiresRoles("admin")
    @ResponseBody
    @DeleteMapping("/delete/{id}")
    public Object deleteResource(@PathVariable("id") String resourceId) {
    	boolean flag = resourceService.deleteResource(resourceId);
    	if (!flag) {
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
    }
    
    /**
     * 资源层级关系展示
     * @param request
     * @return
     */
    @GetMapping("/pidtree")
    @ResponseBody
    public Object pidtree(HttpServletRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 有效资源
        param.put("isEnabled", SystemConstant.IS_YES_INT);
        List<String> types = new ArrayList<String>();
        types.add("F");// 菜单组
        types.add("A");// 页面
        param.put("types", types);
        return resourceService.treeResource(param);
    }
    
    /**
     * 编辑页展示
     * @param model
     * @param resourceId
     * @return
     */
    @GetMapping("/editpage/{id}")
    public String editpage(Model model,@PathVariable("id") String resourceId) {
    	ResourcePO po = resourceService.queryResource(resourceId);
    	model.addAttribute("resource", po);
    	return "admin/resource/resourceEdit";
    }
    
    /**
     * 编辑资源
     * @param editVo
     * @param result
     * @return
     */
    @PostMapping("/edit")
	@ResponseBody
	public Object updateResource(@Validated ResourceVO editVo, BindingResult result) {
    	logger.debug("资源更新数据保存数据是：" + editVo);
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		boolean flag = resourceService.updateResource(editVo);
		if (!flag) {
			return renderError("更新失败！");
		}
		return renderSuccess("更新成功！");
	}
}
