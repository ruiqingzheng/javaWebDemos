package com.hendry.mybatis.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBAccess {
	public static SqlSession getConnection() throws IOException {
		// 1. 用mybatis resource 类获取reader对象, 读取配置文件,参数是配置文件路径
		Reader reader = Resources.getResourceAsReader("com/hendry/mybatis/config/Configuration.xml");

		// 2. 传参reader 获取工厂对象
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		// 3. 工厂对象打开数据库会话 mybatis 的SqlSession对象
		SqlSession openSession = sqlSessionFactory.openSession();
		return openSession;
	}
}
