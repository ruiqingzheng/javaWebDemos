按MVC构架逐步分层来使用jdbc


com.jdbcdemo.db  负责jdbc驱动和连接

com.jdbcdemo.beans
    实体类 , 每个实体类对应一个数据库中的表

com.jdbcdemo.dao
    有了数据库连接和实体类, 就能把数据库中的数据装载到对象中, 此时,就能进行dao操作了,  
    在这个包里面, 每个Dao, java文件对应一个数据库的表的CRUD操作, 
    具体的sql操作都放在这个包里的Dao文件中 , 执行sql并把结果封装为对象进行返回

com.jdbcdemo.services
    dao都已经装载好对象, 但dao并不直接提供给应用使用, 而是通过service 
    service来负责调用DAO , 提供服务


com.jdbcdemo.controller
    或者是action , 这个控制层负责调用service 和 调用模板 来结合输出


---

jdbc 中调用存储过程 procedure

phpmyadmin 里面定义存储过程老是失败

用navicat 连接数据库 定义一个没有参数的存储过程 
> `sp_select_nofilter`
```sql
BEGIN
	select * from imooc_goddess;
END
```

1. 要调用存储过程首先要修改jdbc连接url , 否则提示没有权限  `noAccessToProcedureBodies=true`

	`private static final String URL="jdbc:mysql://192.168.1.222:3306/mybatis_learn?useUnicode=true&characterEncoding=UTF8&noAccessToProcedureBodies=true";`

	//调用存储过程, 定义方法名, 和存储过程名一致
	//存储过程名为    sql procedure    , sp_select_nofilter 
	//所以方法名为 select_nofilter

```java
		//1. 同样的先获取连接
		Connection connection = DBUtil.getConnection();
		//2. 获取statement对象 , CallableStatement
		 CallableStatement cs = connection.prepareCall("call sp_select_nofilter()");
		//3. 调用
		cs.execute();
		
		//4. 存储过程不是直接获取结果集, 需要通过存储过程回参获得结果集
		ResultSet rs = cs.getResultSet();
		
		//遍历结果集
		
```

---- 
带参的存储过程 sp_select_filter

如果参数为空那么查询全表
如果参数11位且第一位是1, 那么参数作为电话, 查询全表电话号码为该参数的行

否则把参数作为用户名来搜索


```SQL
DROP PROCEDURE IF EXISTS `sp_select_filter`;

CREATE DEFINER = `root`@`%` PROCEDURE `sp_select_filter`(IN `sp_name` varchar(20))
    MODIFIES SQL DATA
BEGIN
IF (sp_name IS NULL or sp_name ='' ) THEN  
	SELECT * FROM imooc_goddess;
ELSE
	if(LENGTH(sp_name)>=11 and SUBSTRING(sp_name FROM 1 FOR 1) = 1) THEN
		select * from imooc_goddess where mobile=sp_name;
	ELSE
		SELECT * FROM imooc_goddess WHERE user_name LIKE concat('%',sp_name,'%');  
	end if;
END IF; 
END;
```


mysql 存储过程语法要点:

1. if 必须用括号括起来 
2. if 的结束必须是 `end if;`  分号不能少
3. SQL语句必须是以 `;` 结束
4. 带参数的话, varchar 必须要指定长度


带输出参数的存储过程 , 比如 , 返回的参数是count , 那么在存储过程里面对这个参数进行修改, 
调用该存储过程就能得到返回值

```SQL
DROP PROCEDURE IF EXISTS `sp_select_count`;

CREATE DEFINER = `root`@`%` PROCEDURE `sp_select_count`(OUT `count` int(10))
BEGIN
	#Routine body goes here...
	select count(*) into count from imooc_goddess;
END ;


```

这样定义的out 参数的存储过程, 在navicat里面不知道怎么调用, 但是用java代码调用是成功的



输出参数 , 这里调用的参数的设置是 cs.registerOutParameter , 当中第二个参数, 需要指定数据库中的数据类型,  Types 是jdbc封装的数据库数据类型 , java.sql.Types.INTEGER 对应的就是数据库的int类型

```java
	public static Integer select_count() throws SQLException {
		int count =0;
		
		Connection connection = DBUtil.getConnection();
		CallableStatement cs = connection.prepareCall("call sp_select_count(?)");
		cs.registerOutParameter(1, Types.INTEGER);
		cs.execute();
		count = cs.getInt(1);
		return count;
	}
```

# 连接池

常用开源数据库连接池

1. dbcp
2. c3p0


dependency:

c3p0 依赖  mchange-commons-java

maven 里面只需要c3p0 这一个依赖即可

使用方法比较简单 , 看这篇:  步骤就是修改配置文件, 然后把我们原来的获取连接的代码改成c3p0的方式即可

http://www.cnblogs.com/fingerboy/p/5184398.html

> `/src/c3p0-config.xml`
```
<?xml version="1.0" encoding="UTF-8" ?>
<c3p0-config>
    <default-config> 
        <property name="jdbcUrl">
            <![CDATA[
                jdbc:mysql://192.168.1.222:3306/mybatis_learn?useUnicode=true&characterEncoding=UTF8&useServerPrepStmts=true&prepStmtCacheSqlLimit=256&cachePrepStmts=true&prepStmtCacheSize=256&rewriteBatchedStatements=true&noAccessToProcedureBodies=true
            ]]>
        </property>
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="user">user</property>
        <property name="password">password</property> 
　　     <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 -->
        <property name="acquireIncrement">3</property>
　　     <!-- 初始化数据库连接池时连接的数量 -->
        <property name="initialPoolSize">10</property>
        <!-- 数据库连接池中的最小的数据库连接数 -->
        <property name="minPoolSize">2</property>
        <!-- 数据库连接池中的最大的数据库连接数 -->
        <property name="maxPoolSize">10</property>
    </default-config>
</c3p0-config>
```


```java
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
```

jdbc方式

```java

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
```