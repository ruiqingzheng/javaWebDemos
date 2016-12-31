package com.jdbcdemo.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jdbcdemo.beans.Goddess;
import com.jdbcdemo.dao.GoddessDao;
import com.jdbcdemo.dao.ProduceDao;

public class GoddessService {
	
	
	public static List<Goddess> executeFilterProcedure(String parm) throws SQLException{
		return ProduceDao.select_filter(parm);
	}
	
	

	public static List<Goddess> executeNoFilterProcedure() throws SQLException {
		return ProduceDao.select_nofilter();
	}

	
	public static int executeCountProcedure() throws SQLException {
		return ProduceDao.select_count() ;

				
	}
	
	
	/**
	 * 这个方法里面用来测试 Dao 操作数据库的增删改查是否正确
	 * @throws SQLException
	 */
//	@Test
	public void testService() throws SQLException{
		
		GoddessDao gDao = new GoddessDao();
//		List<Goddess> gList = gDao.find() ;
		Goddess g = new Goddess();
		
		g.setUser_name("angelababy");
		g.setSex(0);
		g.setAge(21);
		g.setBirthday(new Date());
		g.setEmail("jadslfj@sadlkfj.com");
		g.setMobile("13888888888");
		g.setCreate_user("asdfdsaf");
		g.setIsdel(0);
		gDao.addGoddess(g);
		
//		Goddess g = gDao.findOne(3) ;
//		g.setUser_name("baby");
//		g.setMobile("13524428888");
//		gDao.updateGoddess(g);
		
//		gDao.delGoddess(3);
		
//		Goddess g = gDao.findOne(2) ;
//		
//		System.out.println(g.getEmail()  + g.getUser_name());
		
		
	}

}
