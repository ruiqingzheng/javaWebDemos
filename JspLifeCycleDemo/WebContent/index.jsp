<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp生命周期</title>
</head>
<body>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	String dateString = sdf.format(new Date());
	
%>

<h2>今天是<%=dateString %></h2>

<p>
	当项目部署到tomcat后, 第一次访问index.jsp后, 
	会在tomcat的\work\Catalina\localhost\项目名称\org\apache\jsp目录下生成index_jsp.java文件
	打开该文件可见是一个severlet文件, 里面有_jspInit方法和_jspService方法
	并且还有对应的index_jsp.class文件
	
	
	如果eclipse部署不是直接部署到tomcat的话, 那么生成的工作文件, 存放的路径是在eclipse的工作目录
	
	C:\WORKSPACE\JSP_workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\work\Catalina\localhost
	
	这个目录下就存放着class文件java文件, 而不是在tomcat的work目录下
	
	这个部署目录是可以设置的 , 具体查看
	http://www.cnblogs.com/xing901022/p/4352999.html
</p>


<pre>
一、生命周期：
1、生成字节码文件，执行jspInit()只在第一次请求时执行，重复请求仅只执行一次。生成的文件在work里，【每次修改会重新编译，生成新的字节码文件】
2、jspService()用于处理用户请求，对于每一个请求，JSP引擎会创建一个新的线程来处理该请求【JSP多线程：降低资源需求，提高系统并发量及响应时间，但注意同步问题】。
3、由于该Servlet常驻于内存里，所以响应速度非常快。
</pre>

<h2><% out.println("页面被修改, 不重启服务器, 会重新编译吗? 只要被修改就会重新编译吗"); %></h2>
<h4>当修改代码后, 重新查看.java文件会发现 , 代码里面多出很多out.write  说明修改代码后 重新生成了java文件</h4>

<ul>
<li>1.服务器在启动的时候会编译jsp页面 , 让修改生效.</li>
<li>2. jsp代码只是被修改, 不会马上被重新编译, 但是当用户访问, 服务器监测到代码有修改, 就会在用户访问时重新编译jsp.</li>
</ul>
<%!
	//jsp声明方法, 实现乘法表给后面表达式调用
	
	String printMulti(){
		String content = "";
		
		for(int i= 1; i<=9 ; i++) {
			for(int j=1; j<=i; j++) {
				content += i + "*" + j + " = " + i*j + "&nbsp;&nbsp;&nbsp;" ;
			}
			
			content += "<br/>";
		}
				
		return content ;
	}


	//使用内置对象来打印
	
	void printTable(JspWriter out) throws Exception {
		for(int i= 1; i<=9 ; i++) {
			for(int j=1; j<=i; j++) {
				out.println(i + "*" + j + " = " + i*j + "&nbsp;&nbsp;&nbsp;") ;
			}
				out.println("<br/>");
		}
	}
%>


<h4>阶段练习, 乘法表</h4>

<p>
	<%=printMulti() %>
	
	<hr>
	<%
		//这里是使用脚本的方式,就不能用表达式 , 而且;不能掉了
	printTable(out); %>
</p>
</body>
</html>