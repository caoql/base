package com.cal.base.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;

import com.cal.base.common.cache.RedisClient;
import com.cal.base.system.dao.IUserDao;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping(value = "/home")
	public String home() {
		return "home";
	}
	
	@Autowired
	private IUserDao userDao;
	
	@GetMapping(value = "/welcome")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		userDao.selectByPrimaryKey("12");
		//1、收集参数、验证参数  
       //2、绑定参数到命令对象  
       //3、将命令对象传入业务对象进行业务处理  
       //4、选择下一个页面  
       ModelAndView mv = new ModelAndView();  
       //添加模型数据 可以是任意的POJO对象  
       mv.addObject("message", "weclome!!!");  
       //设置逻辑视图名，视图解析器会根据该名字解析到具体的视图页面  
       mv.setViewName("welcome"); 
       Jedis jedis =RedisClient.getJedis();
       System.out.println(jedis.getDB());
       System.out.println("服务正在运行: "+jedis.ping());
       return mv;  
    }  
	
	@PostMapping(value="/test")
	public String test(@Validated Test test, BindingResult result, Model model) {
		System.out.println("..........test............");
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError error: allErrors) {
				System.out.println(error.getDefaultMessage());
			}
			model.addAttribute("allErrors", allErrors);
			return "error";
		}
		System.out.println(test.getName());
		System.out.println(test.getAge());
		model.addAttribute("message", test);
		return "test";
	}
	
	@GetMapping(value="/handle") 
    public ModelAndView test(HttpServletRequest req, HttpServletResponse resp) throws Exception {  
        System.out.println("===========TestController");  
        return new ModelAndView("test");  
    }  

}
