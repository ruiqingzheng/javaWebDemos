# 重构使用mybatis 

##获取mybatis 获取Mybatis的 , SqlSession 对象

1. 添加mybatis 依赖 ,    maven.aliyun.com
```
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.4.1</version>
</dependency>
```
加入依赖后, 正常的话, 本地仓库会马上下载mybatis库文件 , 而且tomcat会自动重启, 重启后项目目录的lib目录会部署mybatis库文件


2. 拷贝mybatis 主配置文件
如果不是用maven , 直接下载的话,  解压出来有个示例代码`test`目录, 从这个目录里可以获取到主配置文件
那么在eclipse , 点击pom.xml 可以看到mybatis的  github 地址, 我们可以到github上去找这个主配置文件 
http://github.com/mybatis/mybatis-3

文件名是   configuration.xml   , 打开项目仓库后, 按两下TT, 或者点击Find File , 输入configuration , 然后看哪个是在test目录下的

可见 `src/test/java/org/apache/ibatis/submitted/complex_property/Configuration.xml` 

拷贝该文件到项目config 目录


3. 修改该配置文件

按原来的jdbc代码, 修改配置文件,  数据库jdbc url ,  username , password
```xml
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC">
        <property name="" value=""/>
      </transactionManager>
      <dataSource type="UNPOOLED">
        <property name="driver" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://192.168.1.222:3306/mybatis_learn?characterEncoding=utf-8"/>
        <property name="username" value="mybatis_learn_f"/>
        <property name="password" value="password"/>
      </dataSource>
    </environment>
  </environments>

```



4. 修改代码获取 mybatis , sqlSession功能: sql传参 , 执行sql , 获取sql执行结果, 事务控制
    读取配置文件
    构建sqlSessionFactory
    打开会话
    
```java
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
```


##  修改配置文件, 使用SqlSession调用配置文件中的sql语句

mybatis 把sql 语句都放在配置文件中 , 配置文件中的每条sql语句 都设置有id , 且每个配置文件都有namespace, 用来保证namespace.id 不至于重复

且配置文件中的namespace 不可省略

默认的sql语句配置文件在github上 test 目录同样可以找到`User.xml`

配置文件中 `<select>` `<update>` 等标签对应数据库语句

`<resultMap>` 标签对应结果集, type对应类名, id不重复即可
下面的子标签, id对应主键, 其他字段对应result标签

```xml

<mapper namespace="Message">
<!-- 
	1. type 对应实体类 , id提供给配置文件中使用, 比如后面的sql语句返回resultMap
	2. resultMap 中的子标签, id对应主键id , result 对应字段
	3. result子标签中, column 对应数据库的column , jdbctype对应的就是 java.sql.Types 
		property 对应的是实体类的属性字段.
	 -->
	 
  <resultMap type="com.hendry.mybatis.beans.Message" id="MessageResult">
    <id column="id" jdbcType="INTEGER" property="id"/>
    <result column="COMMAND" jdbcType="VARCHAR" property="command"/>
    <result column="DESCRIPTION" jdbcType="VARCHAR" property="description"/>
    <result column="CONTENT" jdbcType="VARCHAR" property="content"/>
  </resultMap>

  <select id="queryMessageList" parameterType="long" resultMap="MessageResult">
 	SELECT ID,COMMAND,DESCRIPTION,CONTENT from message where 1=1 
  </select>
</mapper>
```

java.sql.Types

java代码中使用SqlSession.selectList(sql_id) 来调用sql语句, DAO类中

```java
	//重构 mybatis 访问数据库
	public List<Message> queryMessage(String command, String description) {
		List<Message> messageList = new ArrayList<Message>();
		SqlSession sqlSession = null;

		try {
			sqlSession = DBAccess.getConnection();
			messageList = sqlSession.selectList("Message.queryMessageList");
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
```


主配置文件中调用sql配置文件
```
  <mappers>
    <mapper resource="com/hendry/mybatis/config/sqlxml/Message.xml"/>
  </mappers>
```

# sql配置文件传递参数


1. 设置sql配置文件中parameterType , 设置为需要传递的完整类名
    传递对象, new一个需要传递的对象, 初始化属性设置为需要传递的值

```java
		Message msg = new Message();
		msg.setCommand(command);
		msg.setDescription(description);
        ...
        messageList = sqlSession.selectList("Message.queryMessageList",msg);
        ...
```


2. 该配置文件
在sql配置文件中的判断语句 可以使用ignl表达式,   该表达式, 可以直接使用java代码, 包括变量,   但在双引号下需要转义时, 需要把特殊符号按html规则转义

`#{变量名}` 这种传递变量, 会被解析成`?` 

```
  <select id="queryMessageList" parameterType="com.hendry.mybatis.beans.Message" resultMap="MessageResult">
 	SELECT ID,COMMAND,DESCRIPTION,CONTENT from message where 1=1 
 	<!-- 等同 java 语句 'command != null && !"".equals(command.trim())'
 		但两边本来就有双引号, 双引号冲突转义需要按html规则, 比较麻烦, 所以把java的 && 换成 表达式的 and
 		而双引号, 按转义换成 &quto
 	 -->
 	<if test="command != null and !&quot;&quot;.equals(command.trim())">
 		and command = #{command}
 	</if>
 	<!-- 注意 '%' 后必须要有空格 否则结果错误   -->
 	 <if test="description != null and !&quot;&quot;.equals(description.trim())">
 		and DESCRIPTION like '%' #{description} '%'
 	</if>
  </select>
```



----------------


# 直接使用jdbc

涉及到技术点:

1. jstl:  jsp 模板页面中需要使用 jstl来进行foreach循环

2. jdbc获取到数据后, 如何传递给jsp页面 , 把数据库中获取到的数据, 用属性的方式放到`request`对象的属性里
    然后用`request`的`forward`方法, 转到`jsp`模板页面, 且把含有结果属性的`request`传递给该页面
    主要代码如下: 

```java
    List<Message> messageList = new ArrayList<Message>();

    PreparedStatement prepareStatement = conn.prepareStatement("SELECT ID,COMMAND,DESCRIPTION,CONTENT from message");
    ResultSet rs = prepareStatement.executeQuery();

    while(rs.next()) {
        Message message = new Message();
        messageList.add(message);
        message.setId(rs.getString("id"));
        message.setCommand(rs.getString("command"));
        message.setDescription(rs.getString("description"));
        message.setContent(rs.getString("CONTENT"));
    }


    req.setAttribute("messageList", messageList);

    req.getRequestDispatcher("/WEB-INF/jsp/admin/list.jsp").forward(req, resp);
```





jdbc 设置查询参数

注意: 因为所有加的参数都希望格式是 " and key=value" 或 " or key = value"

但是sql 语句, 对于第一个条件是不需要 , `and`, `or` 这样的连接关键字的 

于是, 我们在原始的sql语句最后加上一句`where 1=1` ,   这样后面的条件格式就一致了, 就可以用 `and` , `or` 

```java


    StringBuilder sql = new StringBuilder("SELECT ID,COMMAND,DESCRIPTION,CONTENT from message where 1=1 ");

    List<String> params = new ArrayList<>();
    // command 参数不为null, 且不为空
    if (command != null && !"".equals(command.trim())) {
        sql.append(" and COMMAND=?");
        params.add(command);
    }

    if (description != null && !"".equals(description.trim())) {
        sql.append(" and DESCRIPTION like '%' ? '%'");
        params.add(description);
    }

    PreparedStatement statement = conn.prepareStatement(sql.toString());

    // statement 的setString方法 将问号替换成实际变量

    for (int i = 0; i < params.size(); i++) {
        statement.setString(i+1, params.get(i));
    }

    ResultSet rs = statement.executeQuery();
```






