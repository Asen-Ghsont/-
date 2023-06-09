# 文档v1.0

## 1 基本信息

### 1.1 模式

-   项目模式：MVC
-   数据库框架：ORM
-   导包方式：下载jar包，手动导包

### 1.2 版本

-   Tomcat 10.1.8（tip：你同样可以使用较低版本，但是请确保src/web/lib包中的servlet-api.jar的版本满足你的Tomcat版本的最低要求）
-   JDK 20（tip：你同样可以使用较低版本，但确保满足你的Tomcat版本要求）

### 1.3 结构

项目分为了两个包，分别是src包和web包。

src包：

-   entity：放置实例类，例如Student、Instructor等，用于对应一个对象。
-   service：放置服务类，例如对于学生的添加、对于学生的修改等。用来实现具体操作逻辑。
-   servlet：放置servlet，用来接收浏览器的url访问。
-   test：放置测试类，用来测试某些功能。
-   util：放置工具类，里面放置了两个工具，一个是log工具：NoticeAction；一个是解析JSON对象工具：GetJSONObject。这两个后面会细讲。

web包：

-   lib：放置了依赖。
    -   json：该项目对于前后端交互使用的传输JSON对象，该依赖封装了对JSON的解析。
    -   mysql-connector-java：该依赖用户计息orm-dao包。但原本orm-dao-2.0就下有该依赖，但是不添加会报错，后面会修改。
    -   orm-dao：笔者手搓的使用ORM框架的数据库使用包，配合@Column注解和Table类可以实现对一个实例类的解析并在对应的数据库中创建该类的表。
    -   servlet-api：该依赖用于导入servlet。
-   login：存放登录界面的前端信息。
-   management：存放对信息管理的界面信息，分别有clazz、instructor、student的子文件，子文件下有前端信息。
-   META-INF：没用。
-   WEB-INF：部署信息，里面有web.xml，用于设置部署的信息，这是老式做法，我们使用的是配合注解设置部署信息。但是

### 1.4 思路

-   前端实现用户的操作逻辑，后端实现接收前端的请求，以及数据的crud（create, read, update, delete）。
-   前端使用HTML、CSS、JS实现逻辑界面。
-   后端使用Java的Filter和Servlet实现逻辑控制。
-   数据库使用JDBC和ORM框架实现对数据库的修改，ORM框架实现了可以通过properties文件实现对JDBC的配置。ORM封装了对数据库的操作。
-   前后端使用JSON传输数据，前端使用XMLHttpRequest对象方法，后端使用JSON获取Httprequest的数据。

### 1.5 时序

1.  用户点击按钮；
2.  前端读取用户输入信息；
3.  JS格式化信息，放入http请求体；
4.  设置http请求方式、url；
5.  发送请求给服务器即该地址：localhost:8080；
6.  Tomcat容器接收到请求包；
7.  分析请求包协议：http；
8.  交给对应url的HttpFilter、HttpServlet；
9.  Filter和Servlet依次处理请求，并生成http响应包；
10.  Tomcat得到最终生成的http响应包；
11.  返回给客户端；
12.  客户端获取http响应包；
13.  做出对应的处理；
14.  假如请求没有被处理，失败404；
15.  请求成功，得到http响应包；
16.  分析响应包内容；
17.  通过js，展示出来；

## 2 如何配置

### 2.1 配置方法

1.  在官网下载Tomcat 10.1.8，不需要配置环境变量。

2.  在IDEA启动按钮的选择框内，配置Tomcat：

    1.  在应用服务器（Application sever）下选择你所下载的Tomcat地址。
    2.  将JRE设置为JDK20。
    3.  将Deployment下的应用内容（Application context）清空。

3.  确认自己的mysql用户名和密码。

4.  在service包下的ServiceFactory的此段代码下：

    ```java
    static {
        Properties properties = new Properties();
        properties.put("databaseName", "person");
        TableFactory.setConnectConfig(properties);
    }
    ```

    在这段代码中，通过properties.put()放置所需要的信息，类似于HashMap放入你的数据库账户和密码，以及对应的数据库的名字（该数据库需要先创建）。例如：

    ```java
    static {
        Properties properties = new Properties();
        properties.put("databaseName", "person");
    	properties.put("username", "root");
        properties.put("password", "123456");
        TableFactory.setConnectConfig(properties);
    }
    ```

    注意：orm项目设置了默认账户名密码（root，123456），若你密码如此相同，则不需要设置。

### 2.2 出现问题解决方案

**1. Tomcat启动后404，网址不响应**

此时为配置问题：

-   确保你所打开的是CampusPersonnelManagementSystem项目。

-   确保你的JDK满足需求。

-   Tomcat启动配置确保URL为：

    ```java
    http://localhost:8080/login/index.html
    ```

-   Tomcat启动配置中的Deployment确定有对应的war exploded包，并确定Application context中没有任何内容。

-   若仍然失败，联系邮箱ghsont@qq.com。

**2. 启动成功，无法注册登录**

此时为数据库问题：

-   确保账户、密码正确。
-   确定该数据库是否存在。

**3. 测试存在问题**

请读者自身排查原因，本人测试正常。

















