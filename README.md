# JAVA web 相关的代码


# JSP 
helloworld  -- 理解java web 项目的目录结构, 部署到服务器的结构和本地代码结构的关系

helloworld_jsp_eclipse  -- 学习JSP语法知识 , jsp代码页面并不是放在src目录, 而是放在WebContent根目录, 
                        部署到服务器后, 就在项目根目录, 可以被直接访问 如 `\mywebapps\helloworld_jsp_eclipse`
                        而jsp 都会被解析为java文件, 然后再编译成class文件 , 存放在`work`目录 , 对tomcat而言, `work`目录是个很重要的目录
                        这个目录下存放着每个项目对应生成的class文件

JspBaseElementDemo -- JSP页面元素 ,   jsp 四大指令 (page , tablib, include )    , import属性是page指令用来引用类包的
                    指令, 表达式, 声明, 注释, 脚本


JspLifeCycleDemo  -- jsp 的生命周期
                    1.服务器在启动的时候会编译jsp页面 , 让修改生效.
                    2. jsp代码只是被修改, 不会马上被重新编译, 但是当用户访问, 服务器监测到代码有修改, 就会在用户访问时重新编译jsp.





# servlet


# jdbc


# 框架