package com.hendry.mybatis.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hendry.mybatis.beans.Message;
import com.hendry.mybatis.service.MessageService;


@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		// 获取提交的参数
		String command = req.getParameter("command");
		String description = req.getParameter("description");
		
		//向跳转页面传参, 做为显示用
		//attribute , 和 页面get, post传递的参数并不相同
		
		req.setAttribute("command", command);
		req.setAttribute("description", description);
		
		//通过service层来调用DAO获取数据
		
		MessageService msgService = new MessageService();
		
		//传参
		req.setAttribute("messageList", msgService.listService(command, description));
		
		//显示页面		
		req.getRequestDispatcher("/WEB-INF/jsp/admin/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
