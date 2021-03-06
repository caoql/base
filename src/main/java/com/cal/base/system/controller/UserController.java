package com.cal.base.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

import com.cal.base.common.exception.CommonException;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.common.shiro.encryption.PasswordHash;
import com.cal.base.common.util.file.FileUtil;
import com.cal.base.common.util.idgen.UUIDUtil;
import com.cal.base.common.util.web.WebUtil;
import com.cal.base.system.entity.po.UserPO;
import com.cal.base.system.entity.query.UserParam;
import com.cal.base.system.entity.vo.UserRoleVO;
import com.cal.base.system.entity.vo.UserVO;
import com.cal.base.system.service.RoleService;
import com.cal.base.system.service.UserService;

/**
 * 用户控制器 基于Restful风格的增删改查等操作
 * 
 * @author andyc 2018-3-16
 *
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	// 日志记录器
	private Logger logger = Logger.getLogger(getClass());

	// 注入用戶Service
	@Autowired
	private UserService userService;
	
	// 注入角色Service
	@Autowired
	private RoleService roleService;
	
	// 注入加密算法帮助类
	@Autowired
    private PasswordHash passwordHash;

	/**
	 * 用戶管理页
	 * 
	 * @return "" 用戶管理頁面
	 */
	@GetMapping("/manager")
	public String manager() {
		logger.debug("GET请求用戶管理页...");
		return "admin/user/user";
	}

	/**
	 * 用户管理列表
	 * 
	 * @param param
	 *            查询条件数据封装
	 * @return 注意必须加@ResponseBody注解，否则报404错误，返回的是JSON格式的数据
	 */
	@PostMapping("/list")
	@ResponseBody
	public ResponsePageInfo list(UserParam param) {
		logger.debug("POST请求用户管理列表数据...");
		return userService.listAll(param);
	}

	/**
	 * 添加用户页
	 * 
	 * @return
	 */
	@GetMapping("/addpage")
	public String showAddPage() {
		logger.debug("GET请求添加用户页...");
		return "admin/user/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @param addVo
	 * @return
	 */
	@PostMapping("/add")
	@ResponseBody
	public Object insertUser(@Validated UserVO addVo, BindingResult result) {
		logger.debug("POST请求添加的用户数据是:" + addVo);
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		// 盐-随机数
		String salt = UUIDUtil.getUUID();
		// 密码加盐
		String pwd = passwordHash.toHex(addVo.getPassword(), salt);
		addVo.setRemark(addVo.getPassword());
		addVo.setSalt(salt);
		addVo.setPassword(pwd);
		boolean flag = userService.insertUser(addVo);
		if (!flag) {
			return renderError("添加失败！");
		}
		return renderSuccess("添加成功！");
	}
	
	/**
	 * 绑定角色跳转页
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/bindpage/{id}")
	public String bindPage(Model model, @PathVariable String id) {
		List<Map<String, Object>> rolelist = roleService.queryUserRoleByUserId(id);
		model.addAttribute("id", id);
		model.addAttribute("rolelist", rolelist);
		return "admin/user/userBindRole";
	}
	
	/**
	 * 返回角色绑定列表
	 * @param text
	 * @return
	 */
	@PostMapping("/searchrole")
	@ResponseBody
	public Object searchrole() {
		Map<String, Object> param = new HashMap<String, Object>();
		return roleService.queryUserRole(param);
	}

	/**
	 * 绑定角色
	 * @param vo
	 * @param result
	 * @return
	 */
	@PostMapping("/bindrole")
	@ResponseBody
	public Object bindrole(@Validated UserRoleVO vo, BindingResult result) {
		logger.debug("post请求绑定的角色信息是:" + vo);
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		boolean flag = userService.bindrole(vo);
		if (!flag) {
			return renderError("绑定失败！");
		}
		return renderSuccess("绑定成功！");
	}

	/**
	 * 编辑用户页
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/editpage/{id}")
	public String editPage(Model model, @PathVariable String id) {
		UserPO user = userService.queryUser(id);
		model.addAttribute("user", user);
		return "admin/user/userEdit";
	}
	
	/**
	 * 编辑用户
	 * @param editVo
	 * @param result
	 * @return
	 */
	@PostMapping("/edit")
	@ResponseBody
	public Object updateUser(@Validated UserVO editVo, BindingResult result) {
		logger.debug("post请求编辑的用户数据是:" + editVo);
		// 框架层面的校验
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			StringBuffer sb = new StringBuffer();
			for (ObjectError error : allErrors) {
				sb.append(error.getDefaultMessage() + ", ");
			}
			throw new CommonException(sb.toString());
		}
		// 盐
		String salt = UUIDUtil.getUUID();
		// 密码加密？
		// String pwd = passwordHash.toHex(userVo.getPassword(), salt);
		editVo.setSalt(salt);
		boolean flag = userService.updateUser(editVo);
		if (!flag) {
			return renderError("更新失败！");
		}
		return renderSuccess("更新成功！");
	}

	/**
	 * 删除用户
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public Object delete(@PathVariable("id") String userId) {
		String currentUserId = WebUtil.getUserId(); 
		if (Objects.equals(currentUserId, userId)) {
			return renderError("不可以删除自己！"); 
		}
		boolean flag = userService.deleteByPrimaryKey(userId);
		if (!flag) {
			return renderError("删除失败！");
		}
		return renderSuccess("删除成功！");
	}

	/**
	 * 导出
	 * @param param
	 * @param exportName
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("export")
	public void export(UserParam param, String exportName,
			HttpServletResponse response) throws Exception {
		userService.export(param, exportName, response);
	}
	
	// 模板文件下载
	@GetMapping("download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FileUtil.download(request, response, "用户管理导入模板.xls");
	}
	
	// 批量导入页面
	@GetMapping("importpage")
	public String importpage() {
		logger.debug("GET请求导入用户页...");
		return "admin/user/userImport";
	}
	
	// 批量导入数据解析
	@PostMapping("import")
	@ResponseBody
	public Object batchImport(HttpServletRequest request) {
		logger.debug("Post请求批量导入数据解析...");
		boolean flag = userService.batchImport(request);
		if (!flag) {
			return renderError("导入失败！");
		}
		return renderSuccess("导入成功！");
	}
}
