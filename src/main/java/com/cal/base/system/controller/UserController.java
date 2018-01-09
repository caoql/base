package com.cal.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cal.base.common.info.ResponseInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.cal.base.system.entity.po.User;
import com.cal.base.system.entity.query.UserParam;
import com.cal.base.system.service.IUserService;

/**
 * 
 * @author andyc
 *
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/page")
	public ModelAndView showPage() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user", "曹大爷");
		mv.setViewName("admin/user");
		return mv;
	}
	
	/**
	 * 
	 * @param param
	 * @return
	 * 注意必须加@ResponseBody注解，否则报404错误
	 */
	@PostMapping("/list")
	@ResponseBody
	public ResponsePageInfo list(UserParam param) {
		return userService.listAll(param);
	}
	
	@GetMapping("/addpage")
	public ModelAndView showAddPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/adduser");
		return mv;
	}
	
	@PostMapping("/adduser")
	@ResponseBody
	public ResponseInfo insertUser(User addVo) {
		return userService.insertUser(addVo);
	}

}
