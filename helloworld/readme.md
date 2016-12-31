
这是一个jsp的最简单例子, 不使用IDE, 了解JSP页面和tomcat服务器的关系

最基本的JSP项目, 就是把包含jsp页面的项目目录, 放到tomcat的网页根目录下即可, 比如项目名称helloworld :

1. 在tomcat的web根目录(webapps)下新建项目目录, /webapps/helloworld

2. 创建jsp页面文件, 新建helloworld/index.jsp

3. 创建项目必须的目录和文件: 
    新建helloworld项目目录下的WEB-INF目录, 该目录下需要有web.xml文件, 可以从tomcat的样例目录拷贝一份
    修改web.xml,   只保留web-app根标签
    创建WEB-INF/classes 和 WEB-INF/lib   , classes目录用来放编译后的字节码文件, lib目录存放需要的jar包


访问http://localhost:8080/helloworld/


所有的java web项目 , 都包含这种最基本的目录结构, 包括后面的servlet , 还有其他框架, 比如strust , springMVC 等

项目在本地的目录结构可以各不相同, 比如eclipse 项目结构, maven项目结构,  这些本地源码的目录组织结构不同, 但是, 

当项目部署到服务器端, 那么就必须是遵循容器的目录结构,  也就是上面提到的, 项目目录下必须有WEB-INF , 

/WEB-INF
    -- web.xml     指定项目相关的信息 比如入口servlet 等
    --lib/         依赖的包都放在这个目录下面,   当部署项目的时候, jar包拷贝到这个目录,  服务器才知道到这里找依赖的类
    --classes/        我们写的servlet 等源码最终要编译成class文件 ,   就是放在这个目录下

/WEB-INF 是项目最重要的目录, 这个目录下的资源都是不能被直接访问到的, 它是提供给容器tomcat访问的 ,  模板文件一般也放在这个目录下

而其他的静态资源, 就直接放在项目根目录下 , 可以被直接访问

