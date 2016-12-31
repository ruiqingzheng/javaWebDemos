# JAVA web 相关的代码


# JSP 项目demo:

## helloworld  
    -- 理解java web 项目的目录结构, 部署到服务器的结构和本地代码结构的关系

## helloworld_jsp_eclipse 
    -- 学习JSP语法知识 , jsp代码页面并不是放在src目录, 而是放在WebContent根目录, 
        部署到服务器后, 就在项目根目录, 可以被直接访问 如 `\mywebapps\helloworld_jsp_eclipse`
        而jsp 都会被解析为java文件, 然后再编译成class文件 , 存放在`work`目录 , 对tomcat而言, `work`目录是个很重要的目录
        这个目录下存放着每个项目对应生成的class文件

## JspBaseElementDemo 
    -- JSP页面元素 ,   jsp 四大指令 (page , tablib, include )    , import属性是page指令用来引用类包的
        指令, 表达式, 声明, 注释, 脚本


## JspLifeCycleDemo  
    -- jsp 的生命周期
        1.服务器在启动的时候会编译jsp页面 , 让修改生效.
        2. jsp代码只是被修改, 不会马上被重新编译, 但是当用户访问, 服务器监测到代码有修改, 就会在用户访问时重新编译jsp.





# servlet

    -- ServletDemo , 与jsp的几点不同
        1. 部署目录不同
        和jsp不同, servlet源码是放在src目录, 不再是直接放在webcontent根目录
        servlet 代码是被编译后都是被部署到项目根目录下WEB-INF\classes ,  而jsp编译后是被放在tomcat根目录下的works目录下 .

        2. 访问路径
        我们以前学习jsp源码都是放在 webcontent 根目录 , 访问路径就是 项目根路径, 这个是对应的, 看起来就像是直接访问, (实际并不是, 实际上jsp还是被编译成class放在work目录)
        而servlet是放在src目录, 被编译后被放在WEB-INF\classes 目录, 而访问url 是必须写配置文件 web.xml 来设置 ,  注册servlet ,  servlet-mapping后才能访问

        3. 侧重点不同
        jsp 侧重html混合, 适合写模板
        servlet侧重逻辑功能, 适合做控制层

        4. jsp 页面地址,只能处理get请求   , 而servlet 可以同时实现get, post , 同一个页面地址处理get,post...
          



# jdbc


# 框架