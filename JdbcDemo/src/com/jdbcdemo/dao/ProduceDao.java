package com.jdbcdemo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.jdbcdemo.beans.Goddess;
import com.jdbcdemo.db.DBUtil;
import com.jdbcdemo.db.DBUtil_c3p0;

/***
 * 要调用存储过程首先要修改jdbc连接url , 否则提示没有权限
 * 
 * &noAccessToProcedureBodies=true
 * 
 * @author hendry-code
 */
public class ProduceDao {
	
	private static Connection connection =  DBUtil_c3p0.getConnection();
//	private static Connection connection = DBUtil.getConnection();

	
	
	// 调用存储过程, 定义方法名, 和存储过程名一致
	// 存储过程名为 sql procedure , sp_select_nofilter
	// 所以方法名为 select_nofilter
	public static List<Goddess> select_nofilter() throws SQLException {
		// 1. 同样的先获取连接
//		
		// 2. 获取statement对象 , CallableStatement
		CallableStatement cs = connection.prepareCall("call sp_select_nofilter()");
		// 3. 调用
		cs.execute();

		// 4. 存储过程不是直接获取结果集, 需要通过存储过程回参获得结果集
		ResultSet rs = cs.getResultSet();

		// 遍历结果集

		List<Goddess> resultList = new ArrayList<Goddess>();
		while (rs.next()) {
			Goddess g = new Goddess();
			resultList.add(g);
			g.setId(rs.getInt("id"));
			g.setAge(rs.getInt("age"));
			g.setBirthday(rs.getDate("birthday"));
			g.setCreate_date(rs.getDate("create_date"));
			g.setCreate_user(rs.getString("create_user"));
			g.setEmail(rs.getString("email"));
			g.setIsdel(rs.getInt("isdel"));
			g.setMobile(rs.getString("mobile"));
			g.setSex(rs.getInt("sex"));
			g.setUpdate_date(rs.getDate("update_date"));
			g.setUpdate_user(rs.getString("update_user"));
			g.setUser_name(rs.getString("user_name"));

			// System.out.println("goddess name:" + rs.getString("user_name") +
			// rs.getInt("age"));
		}
		return resultList;
	}

	// 调用带参数的存储过程
	// 和不带参数是一致的, 只是多出传参这个步骤
	public static List<Goddess> select_filter(String sp_name) throws SQLException {
		// String sql_procedure = "call sp_select_filter(?)";

		// 1. 获取jdbc连接
//		Connection conn = DBUtil.getConnection();
		// 2. 获取callAbleStatement
		CallableStatement cs = connection.prepareCall("call sp_select_filter(?)");
		// 3. 设置参数
		cs.setString(1, sp_name);
		// 4. 调用
		cs.execute();
		// 5. 获取结果集
		ResultSet rs = cs.getResultSet();
		// 6. 返回对象
		List<Goddess> resultList = new ArrayList<Goddess>();
		while (rs.next()) {
			Goddess g = new Goddess();
			resultList.add(g);
			g.setId(rs.getInt("id"));
			g.setAge(rs.getInt("age"));
			g.setBirthday(rs.getDate("birthday"));
			g.setCreate_date(rs.getDate("create_date"));
			g.setCreate_user(rs.getString("create_user"));
			g.setEmail(rs.getString("email"));
			g.setIsdel(rs.getInt("isdel"));
			g.setMobile(rs.getString("mobile"));
			g.setSex(rs.getInt("sex"));
			g.setUpdate_date(rs.getDate("update_date"));
			g.setUpdate_user(rs.getString("update_user"));
			g.setUser_name(rs.getString("user_name"));

			// System.out.println("goddess name:" + rs.getString("user_name") +
			// rs.getInt("age"));
		}
		return resultList;

	}

	public static Integer select_count() throws SQLException {
		int count =0;
		
//		Connection connection = DBUtil.getConnection();
		CallableStatement cs = connection.prepareCall("call sp_select_count(?)");
		cs.registerOutParameter(1, Types.INTEGER);
		cs.execute();
		count = cs.getInt(1);
		return count;
	}

}
