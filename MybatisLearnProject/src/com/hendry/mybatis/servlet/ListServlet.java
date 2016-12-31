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


@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		
		//odbc连接数据库
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.222:3306/mybatis_learn?"  
					+"user=mybatis_learn_f&password=mybatis_learn_f321&useUnicode=true&characterEncoding=UTF8"); 
					
			PreparedStatement prepareStatement = conn.prepareStatement("SELECT ID,COMMAND,DESCRIPTION,CONTENT from message");
			ResultSet rs = prepareStatement.executeQuery();
			//新建实体类 , 然后新建实体类列表用来存放查询结果
			List<Message> messageList = new ArrayList<Message>();
			while(rs.next()) {
				Message message = new Message();
				messageList.add(message);
				message.setId(rs.getString("id"));
				message.setCommand(rs.getString("command"));
				message.setDescription(rs.getString("description"));
				message.setContent(rs.getString("CONTENT"));
			}
			
			//将查询结果放到request对象的属性里面
			req.setAttribute("messageList", messageList);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/jsp/admin/list.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

}
