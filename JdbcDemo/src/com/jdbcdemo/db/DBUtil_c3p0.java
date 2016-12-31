package com.jdbcdemo.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil_c3p0 {
	
	private static Connection connection;
	
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	
	public static Connection getConnection(){
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
