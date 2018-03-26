package com.cal.base.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务器端对form表单提交上来的验证码处理
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = -1388498472220166602L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String clientCheckcode = request.getParameter("validateCode"); // 接收客户端浏览器提交上来的验证码
		String serverCheckcode = (String) request.getSession().getAttribute("checkcode"); // 从服务器端的session中取出验证码
		String msg = "验证码验证通过";
		if (serverCheckcode !=null && serverCheckcode.equals(clientCheckcode)) {// 将客户端验证码和服务器端验证比较，如果相等，则表示验证通过
			System.out.println(msg);
		} else {
			msg = "验证码验证失败";
			System.out.println(msg);
		}
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.getWriter().write(msg);
	}

}
