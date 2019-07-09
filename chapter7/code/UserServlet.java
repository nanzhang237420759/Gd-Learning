package com.foodworld.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter(); // 获取writer方法，用于将数据返回给ajax
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		if ("admin".equals(account) && "admin".equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("login", account);
			out.println(true); // 返回true，表示登录成功
		} else {
			req.setAttribute("msg", "登录错误");// 返回登录错误的信息
			out.println(false);// 返回false，表示登录失败
		}
		out.flush();
		out.close();
	}

}
