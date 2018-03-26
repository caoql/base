package com.cal.base.system.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cal.base.system.service.IOrganizationService;

/**
 * 组织管理控制器
 * 
 * @author andyc 2018-3-15
 *
 */
@Controller
@RequestMapping("/admin/organization")
public class OrganizationController extends BaseController {

	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());
	
	// 注入组织资源
	@Autowired
	private IOrganizationService organizationService;

	/**
	 * 部门管理主页
	 *
	 * @return
	 */
	@GetMapping("/manager")
	public String manager() {
		return "admin/organization/organization";
	}

	/**
	 * 部门资源树
	 *
	 * @return
	 *//*
	@PostMapping(value = "/tree")
	@ResponseBody
	public Object tree() {
		return organizationService.selectTree();
	}

	*//**
	 * 部门列表
	 *
	 * @return
	 *//*
	@RequestMapping("/treeGrid")
	@ResponseBody
	public Object treeGrid() {
		return organizationService.selectTreeGrid();
	}

	*//**
	 * 添加部门页
	 *
	 * @return
	 *//*
	@RequestMapping("/addPage")
	public String addPage() {
		return "admin/organization/organizationAdd";
	}

	*//**
	 * 添加部门
	 *
	 * @param organization
	 * @return
	 *//*
	@RequestMapping("/add")
	@ResponseBody
	public Object add(OrganizationPO organization) {
		organization.setCreateTime(new Date());
		organizationService.insert(organization);
		return renderSuccess("添加成功！");
	}

	*//**
	 * 编辑部门页
	 *
	 * @param request
	 * @param id
	 * @return
	 *//*
	@GetMapping("/editPage")
	public String editPage(Model model, Long id) {
		OrganizationPO organization = organizationService
				.selectByPrimaryKey(id);
		model.addAttribute("organization", organization);
		return "admin/organization/organizationEdit";
	}

	*//**
	 * 编辑部门
	 *
	 * @param organization
	 * @return
	 *//*
	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(OrganizationPO organization) {
		organizationService.updateByPrimaryKeySelective(organization);
		return renderSuccess("编辑成功！");
	}

	*//**
	 * 删除部门
	 *
	 * @param id
	 * @return
	 *//*
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Long id) {
		organizationService.deleteByPrimaryKey(id);
		return renderSuccess("删除成功！");
	}*/
}