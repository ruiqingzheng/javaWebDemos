package com.hendry.mybatis.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.hendry.mybatis.beans.Message;
import com.hendry.mybatis.db.DBAccess;

public class MessageDao {
	
	
	//重构 mybatis 访问数据库
	public List<Message> queryMessage(String command, String description) {
		List<Message> messageList = new ArrayList<Message>();
		SqlSession sqlSession = null;
		Message msg = new Message();
		msg.setCommand(command);
		msg.setDescription(description);

		try {
			sqlSession = DBAccess.getConnection();
			//调用sql , 且传递参数
			messageList = sqlSession.selectList("Message.queryMessageList",msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
		return messageList;
	}
	
	

	@Test
	public void testSqlSession() {
		List<Message> messageList = this.queryMessage(null, null);
		for(Message msg: messageList) {
			System.out.println(msg.toString());
		}
		System.out.println("------------------------------------------");
		System.out.println("测试传递参数调用");
		messageList = this.queryMessage(null, "精彩");
		for(Message msg: messageList) {
			System.out.println(msg.toString());
		}
	}
	
	

	public List<Message> queryMessageByJdbc(String command, String description) {
		List<Message> messageList = new ArrayList<Message>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.222:3306/mybatis_learn?"
					+ "user=mybatis_learn_f&password=mybatis_learn_f321&useUnicode=true&characterEncoding=UTF8");

			StringBuilder sql = new StringBuilder("SELECT ID,COMMAND,DESCRIPTION,CONTENT from message where 1=1 ");

			List<String> params = new ArrayList<>();
			// command 参数不为null, 且不为空
			if (command != null && !"".equals(command.trim())) {
				sql.append(" and command = ? ");
				params.add(command);
			}

			if (description != null && !"".equals(description.trim())) {
				sql.append(" and DESCRIPTION like '%' ? '%'");
				params.add(description);
			}

			PreparedStatement statement = conn.prepareStatement(sql.toString());

			// statement 的setString方法 将问号替换成实际变量
			// 如果没有设置参数的话, params.size为0 , 这段代码正好就不执行
			for (int i = 0; i < params.size(); i++) {
				statement.setString(i+1, params.get(i));
			}

			ResultSet rs = statement.executeQuery();
			
			// 新建实体类 , 然后新建实体类列表用来存放查询结果

			while (rs.next()) {
				Message message = new Message();
				messageList.add(message);
				message.setId(rs.getString("id"));
				message.setCommand(rs.getString("command"));
				message.setDescription(rs.getString("description"));
				message.setContent(rs.getString("CONTENT"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return messageList;

	}

}
