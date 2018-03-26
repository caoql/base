package com.cal.base.system.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cal.base.system.service.IResourceService;

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
	private IResourceService resourceService;

	@GetMapping("/manager")
	public String manager() {
		return "admin/resource/resource";
	}
	/**O
	 * 菜单树
	 *
	 * @return
	 */
	/*
	 * @PostMapping("/tree")
	 * 
	 * @ResponseBody public Object tree() { ShiroUser shiroUser =
	 * getShiroUser(); return resourceService.selectTree(shiroUser); }
	 *//**
	 * 资源管理页
	 *
	 * @return
	 */
	/*
	 * @GetMapping("/manager") public String manager() { return
	 * "admin/resource/resource"; }
	 *//**
	 * 资源管理列表
	 *
	 * @return
	 */
	/*
	 * @PostMapping("/treeGrid")
	 * 
	 * @ResponseBody public Object treeGrid() { return
	 * resourceService.selectAll(); }
	 *//**
	 * 添加资源页
	 *
	 * @return
	 */
	/*
	 * @GetMapping("/addPage") public String addPage() { return
	 * "admin/resource/resourceAdd"; }
	 *//**
	 * 添加资源
	 *
	 * @param resource
	 * @return
	 */
	/*
	 * @RequestMapping("/add")
	 * 
	 * @ResponseBody public Object add( ResourcePO resource) {
	 * resource.setCreateTime(new Date()); // 选择菜单时将openMode设置为null Integer type
	 * = resource.getResourceType(); if (null != type && type == 0) {
	 * resource.setOpenMode(null); } resourceService.insert(resource); return
	 * renderSuccess("添加成功！"); }
	 *//**
	 * 查询所有的菜单
	 */
	/*
	 * @RequestMapping("/allTree")
	 * 
	 * @ResponseBody public Object allMenu() { return
	 * resourceService.selectAllMenu(); }
	 *//**
	 * 查询所有的资源tree
	 */
	/*
	 * @RequestMapping("/allTrees")
	 * 
	 * @ResponseBody public Object allTree() { return
	 * resourceService.selectAllTree(); }
	 *//**
	 * 编辑资源页
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	/*
	 * @RequestMapping("/editPage") public String editPage(Model model, Long id)
	 * { ResourcePO resource = resourceService.selectByPrimaryKey(id);
	 * model.addAttribute("resource", resource); return
	 * "admin/resource/resourceEdit"; }
	 *//**
	 * 编辑资源
	 *
	 * @param resource
	 * @return
	 */
	/*
	 * @RequestMapping("/edit")
	 * 
	 * @ResponseBody public Object edit( ResourcePO resource) {
	 * resourceService.updateByPrimaryKeySelective(resource); return
	 * renderSuccess("编辑成功！"); }
	 *//**
	 * 删除资源
	 *
	 * @param id
	 * @return
	 */
	/*
	 * @RequestMapping("/delete")
	 * 
	 * @ResponseBody public Object delete(Long id) {
	 * resourceService.deleteByPrimaryKey(id); return renderSuccess("删除成功！"); }
	 */
}
