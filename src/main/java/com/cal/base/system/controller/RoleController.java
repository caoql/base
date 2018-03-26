package com.cal.base.system.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cal.base.system.service.IRoleService;

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
    private IRoleService roleService;

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
     * 权限列表
     *
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     *//*
    @PostMapping("/dataGrid")
    @ResponseBody
    public DataGridResult dataGrid(Integer page, Integer rows, String sort, String order) {
    	//MyBatis 分页插件
        //不进行count查询，第三个参数设为false 默认第三个参数为true  //PageHelper.startPage(1, 10, false);
        PageHelper.startPage(page, rows); 
		Map<String, Object> map = new HashMap<String, Object>();
		List<RolePO> roleList=roleService.queryAll(map);
        PageInfo<RolePO> pageInfo = new PageInfo<RolePO>(roleList);
        //分页渲染实体类的构建
        DataGridResult jqueryPageInfo=new DataGridResult();
        jqueryPageInfo.setTotal(pageInfo.getTotal());
        jqueryPageInfo.setRows(pageInfo.getList());
        return jqueryPageInfo;
    }

    *//**
     * 权限树
     * @return
     *//*
    @PostMapping("/tree")
    @ResponseBody
    public Object tree() {
        return roleService.selectTree();
    }

    *//**
     * 添加权限页
     * @return
     *//*
    @GetMapping("/addPage")
    public String addPage() {
        return "admin/role/roleAdd";
    }

    *//**
     * 添加权限
     * @param role
     * @return
     *//*
    @PostMapping("/add")
    @ResponseBody
    public Object add(RolePO role) {
        roleService.insert(role);
        return renderSuccess("添加成功！");
    }

    *//**
     * 删除权限
     *
     * @param id
     * @return
     *//*
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {
        roleService.deleteByPrimaryKey(id);
        return renderSuccess("删除成功！");
    }

    *//**
     * 编辑权限页
     *
     * @param model
     * @param id
     * @return
     *//*
    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        RolePO role = roleService.selectByPrimaryKey(id);
        model.addAttribute("role", role);
        return "admin/role/roleEdit";
    }

    *//**
     * 删除权限
     *
     * @param role
     * @return
     *//*
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit( RolePO role) {
        roleService.updateByPrimaryKeySelective(role);
        return renderSuccess("编辑成功！");
    }

    *//**
     * 授权页面
     *
     * @param id
     * @param model
     * @return
     *//*
    @GetMapping("/grantPage")
    public String grantPage(Model model, Long id) {
        model.addAttribute("id", id);
        return "admin/role/roleGrant";
    }

    *//**
     * 授权页面页面根据角色查询资源
     *
     * @param id
     * @return
     *//*
    @RequestMapping("/findResourceIdListByRoleId")
    @ResponseBody
    public Object findResourceByRoleId(Long id) {
        List<Long> resources = roleService.selectResourceIdListByRoleId(id);
        return renderSuccess(resources);
    }

    *//**
     * 授权
     *
     * @param id
     * @param resourceIds
     * @return
     *//*
    @RequiresRoles("admin")
    @RequestMapping("/grant")
    @ResponseBody
    public Object grant(Long id, String resourceIds) {
        roleService.updateRoleResource(id, resourceIds);
        return renderSuccess("授权成功！");
    }
*/
}