package com.jdbcdemo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbcdemo.beans.Goddess;
import com.jdbcdemo.db.DBUtil;

/***
 * 是不是应该考虑把表名提取出来
 * 
 * @author hendry-code
 *
 */
public class GoddessDao {

	public void addGoddess(Goddess g) throws SQLException {

		// 1.获取数据库连接

		Connection conn = DBUtil.getConnection();
		// 2.预编译sql语句
		// create_date , update_date 默认值为当前时间, 设置为sql语句
		String sql = "INSERT INTO `imooc_goddess` " + "(`user_name`, `sex`, `age`, `birthday`, `email`, `mobile`, "
				+ "`create_user`, `create_date`, `update_user`, `update_date`, `isdel`)" + "VALUES "
				+ "( ?,? ,?,? ,?,? ,?,current_date() ,?,current_date() ,? );";
		PreparedStatement ps = conn.prepareStatement(sql);
		// 3.给预编译sql传递参数
		ps.setString(1, g.getUser_name());
		ps.setInt(2, g.getSex());
		ps.setInt(3, g.getAge());
		ps.setDate(4, new Date(g.getBirthday().getTime()));
		ps.setString(5, g.getEmail());
		ps.setString(6, g.getMobile());
		ps.setString(7, g.getCreate_user());
		ps.setString(8, g.getUpdate_user());
		ps.setInt(9, g.getIsdel());

		// 4.执行sql
		ps.execute();

		// return null;
	}

	public void updateGoddess(Goddess g) throws SQLException {

		// 1.获取数据库连接
		Connection conn = DBUtil.getConnection();
		// 2.预编译sql语句
		// create_date , update_date 默认值为当前时间, 设置为sql语句
		String sql = "update imooc_goddess" + " set user_name =? , sex =?, age =?, birthday=?, email =?, mobile = ?,"
				+ " update_user=?, update_date=current_date(), isdel=?" + " where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		// 3.给预编译sql传递参数
		ps.setString(1, g.getUser_name());
		ps.setInt(2, g.getSex());
		ps.setInt(3, g.getAge());
		ps.setDate(4, new Date(g.getBirthday().getTime()));
		ps.setString(5, g.getEmail());
		ps.setString(6, g.getMobile());
		ps.setString(7, g.getUpdate_user());
		ps.setInt(8, g.getIsdel());
		ps.setInt(9, g.getId());
		// 4.执行sql
		ps.execute();

	}

	public void delGoddess(Integer id) throws SQLException {
		// 1.获取数据库连接
		Connection conn = DBUtil.getConnection();
		// 2.预编译sql语句
		// create_date , update_date 默认值为当前时间, 设置为sql语句
		String sql = "delete from imooc_goddess" + " where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		// 3.给预编译sql传递参数
		ps.setInt(1, id);
		// 4.执行sql
		ps.execute();
	}

	public List<Goddess> find() throws SQLException {
		Connection conn = DBUtil.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select id,user_name ,email, age , birthday from imooc_goddess");
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

	public Goddess findOne(int id) throws SQLException {

		Connection conn = DBUtil.getConnection();
		// 2.预编译sql语句
		// create_date , update_date 默认值为当前时间, 设置为sql语句
		String sql = "select * from imooc_goddess where id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		// 3.给预编译sql传递参数
		ps.setInt(1, id);

		// 4.执行sql
		ResultSet rs = ps.executeQuery();
		Goddess g = null;

		if (rs.next()) {
			g = new Goddess();
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
		}

		return g; 

	}
}
