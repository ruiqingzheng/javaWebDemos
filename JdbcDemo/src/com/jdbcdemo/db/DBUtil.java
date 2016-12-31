package com.jdbcdemo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;



public class DBUtil {
	
	
	private static final String URL="jdbc:mysql://192.168.1.222:3306/mybatis_learn?useUnicode=true&characterEncoding=UTF8&noAccessToProcedureBodies=true";
	private static final String USER="mybatis_learn_f";
	private static final String PASSWORD="mybatis_learn_f321";
	
	private static Connection conn ;
	
	
	static {

		try {
			//1. 加载mysql 驱动, 用反射加载一个类到本身
			Class.forName("com.mysql.jdbc.Driver");
			//2. 获取jdbc数据库连接
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
		
		} catch (SQLException e) {
			// 获取数据库连接失败
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//加载驱动类失败
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection(){
		return conn;
	}
			
	@Test
	public void connectionTest() throws ClassNotFoundException{
		
		//1. 加载mysql 驱动, 用反射加载一个类到本身
		Class.forName("com.mysql.jdbc.Driver");
		//2. 获取jdbc数据库连接
		try {
			Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select user_name , age from imooc_goddess");
			while(rs.next()){
				System.out.println("goddess name:" + rs.getString("user_name") + rs.getInt("age"));
			}
			
		} catch (SQLException e) {
			// 获取数据库连接失败
			e.printStackTrace();
		}
		
	}
}
