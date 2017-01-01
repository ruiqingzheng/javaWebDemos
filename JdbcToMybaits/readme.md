# 测试数据库

mybatis_learn

表结构见资源里面的message.sql

一步步先从直接使用jdbc  , 到分层使用jdbc , 到最后把jdbc替换为mybatis.


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





# 代码重构分层

1. 先分离DAO层出来

DAO :   和什么表相关的操作, 就都放在`表名DAO`这个类里面 , 比如Message表, 那么操作这个表的类就是`MessageDAO`


2. 分离出service层:
    暂时这里service层非常简单就是实例化dao , 调用dao
service : 控制层一般不直接调用DAO , 而是通过service层,  service层会根据控制层的需求来组织一些查询参数, 甚至是一些逻辑代码运行


