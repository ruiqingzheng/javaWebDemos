<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<h2>page指令中的pageEncoding必须指定为utf-8 或其他支持的中文字符集, 否则JSP文件中的中文无法输入保存</h2>

	contentType 也应该在page指令中指定输出的html字符集为utf-8
	<hr />
	<h3>jsp 中的注释</h3>

	查看源码 HTML 注释客户端浏览器可见  
	
	JSP注释, 在客户端浏览器不可见  <%-- jsp 注释 --%>
	<!--  HTML 注释客户端浏览器可见 -->
	<%-- JSP注释, 在客户端浏览器不可见 --%>
	
	<hr />
	<h3>jsp 中的声明</h3>
	
	jsp声明见jsp代码 , 调用.


	<%!//jsp 声明标签的使用 , 声明变量, 声明方法
	String name = "张三";

	int add(int x, int y) {
		return x + y;
	}
	
	%>

	<%
		// JSP 脚本中的单行注释 , jsp脚本注释, 浏览器不可见, 凡是jsp注释都不可见
		/*
			jsp脚本中的多行注释
			jsp脚本中的多行注释
			jsp脚本中的多行注释
		*/

		out.println("大家好, 欢迎学习java ee开发" + "name:" + name + "3+4 = " + add(3, 4));
	%>
	
	
	<hr/>
	<h3>jsp 表达式的使用</h3>
	
	调用表达式, 读取变量name = <%=name %>
	

</body>
</html>