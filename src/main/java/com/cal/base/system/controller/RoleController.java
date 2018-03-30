package com.cal.base.system.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.system.entity.query.RoleParam;
import com.cal.base.system.entity.vo.RoleVO;
import com.cal.base.system.service.RoleService;

/**
 * 角色控制器
 * @author andyc 2018-3-15
 *
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());
	
	// 注入角色Service
    @Autowired
    private RoleService roleService;

    /**
     * 权限管理页
     *
     * @return
     */
    @GetMapping("/manager")
    public String manager() {
        return "admin/role/role";
    }

    /**
     * 角色界面数据展示
     * @param param
     * @return
     */
    @PostMapping("/list")
	@ResponseBody
	public ResponsePageInfo list(RoleParam param) {
		return roleService.listAll(param);
	}
    
    /**
     * 角色新增页面展示
     * @return
     */
    @GetMapping("/addpage")
	public String showAddPage() {
		return "admin/role/roleAdd";
	}
    
    /**
     * 角色新增数据保存
     * @param addVo
     * @param result
     * @return
     */
    @PostMapping("/add")
	@ResponseBody
	public Object saveRole(@Validated RoleVO addVo, BindingResult result) {
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		boolean flag = roleService.saveRole(addVo);
		if (!flag) {
			return renderError("添加失败！");
		}
		return renderSuccess("添加成功！");
	}
    
    /**
     * 授权页跳转
     * @param model
     * @param id
     * @return
     */
	@GetMapping("/grantpage/{id}")
	public String grantpage(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		return "admin/role/roleGrant";
	}
	
	 /**
     * 授权页面页面根据角色查询资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Object findResourceByRoleId(String id) {
        List<String> resources = roleService.selectResourceIdListByRoleId(id);
        return renderSuccess(resources);
    }
    
    @RequestMapping("/grant")
    @ResponseBody
    public Object grant(String id, String resourceIds) {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }
}