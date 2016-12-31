<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	//项目路径 , 或者叫项目上下文路径
	String path = request.getContextPath();

	//url基本路径 , 这个路径一般用来引用静态资源 , myeclipse 会自动生成这段代码
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/" ;
	
%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Servlet Demo</title>
</head>
<body>

<h2>使用servlet的例子</h2>

<hr>
<a href="servlet/HelloServletTest">get 请求servlet</a>

<form action="servlet/HelloServletTest" method="post">
	<input type="submit" value="post 请求servlet" />
</form>

</body>
</html>