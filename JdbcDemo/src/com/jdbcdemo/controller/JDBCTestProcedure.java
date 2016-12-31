package com.jdbcdemo.controller;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.jdbcdemo.beans.Goddess;
import com.jdbcdemo.dao.ProduceDao;
import com.jdbcdemo.services.GoddessService;



public class JDBCTestProcedure {
	
	@Test
	public void testProcedure() {
		try {
			List<Goddess> gList = GoddessService.executeFilterProcedure("13888888888");
			
			for(Goddess g : gList) {
				System.out.println(g.toString());
			}
			
			System.out.println("------------------------\n");
			
			List<Goddess> gList2 = GoddessService.executeNoFilterProcedure();
			for(Goddess g : gList2) {
				System.out.println(g.toString());
			}
			
			System.out.println("------------------------\n");
			
			
			int rowCount = GoddessService.executeCountProcedure();
			System.out.printf("row count: %d", rowCount);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	
	
//	@Test
	public void testNoFilterProcedure() {
		try {
			List<Goddess> gList = ProduceDao.select_nofilter();
			
			for( Goddess g : gList ) {
				System.out.println(g.toString());
			}
		} catch (SQLException e) {
			// 这里是controller , 所以这里再抛出异常就没有地方处理了 ,这里就不能throw了只能try..catch
			// 存储过程执行错误
			e.printStackTrace();
		}

	}
	
	
	
//	@Test
	public void testFilterProcedure() {
		String parm = "";
		
		try {
			List<Goddess> gList = ProduceDao.select_filter(parm);
			
			for( Goddess g : gList ) {
				System.out.println(g.toString());
			}
		} catch (SQLException e) {
			// 这里是controller , 所以这里再抛出异常就没有地方处理了 ,这里就不能throw了只能try..catch
			// 存储过程执行错误
			e.printStackTrace();
		}

	}

}
