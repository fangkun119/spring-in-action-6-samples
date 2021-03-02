<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH06 使用配置属性（Configuration Properties）](#ch06-%E4%BD%BF%E7%94%A8%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7configuration-properties)
  - [6.0 bean装配及属性注入的局限](#60-bean%E8%A3%85%E9%85%8D%E5%8F%8A%E5%B1%9E%E6%80%A7%E6%B3%A8%E5%85%A5%E7%9A%84%E5%B1%80%E9%99%90)
  - [6.1 多配置源细粒度配置](#61-%E5%A4%9A%E9%85%8D%E7%BD%AE%E6%BA%90%E7%BB%86%E7%B2%92%E5%BA%A6%E9%85%8D%E7%BD%AE)
    - [6.1.1 理解Spring的环境抽象](#611-%E7%90%86%E8%A7%A3spring%E7%9A%84%E7%8E%AF%E5%A2%83%E6%8A%BD%E8%B1%A1)
      - [(1) Spring的多配置源聚合](#1-spring%E7%9A%84%E5%A4%9A%E9%85%8D%E7%BD%AE%E6%BA%90%E8%81%9A%E5%90%88)
    - [6.1.2 配置DataSource](#612-%E9%85%8D%E7%BD%AEdatasource)
      - [(1) 为生产环境增加MySQL DataSource配置](#1-%E4%B8%BA%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E5%A2%9E%E5%8A%A0mysql-datasource%E9%85%8D%E7%BD%AE)
      - [(2) 声明应用程序启动时要执行的数据库初始化脚本](#2-%E5%A3%B0%E6%98%8E%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F%E5%90%AF%E5%8A%A8%E6%97%B6%E8%A6%81%E6%89%A7%E8%A1%8C%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9D%E5%A7%8B%E5%8C%96%E8%84%9A%E6%9C%AC)
      - [(3) 使用存储在JNDI中的数据库配置](#3-%E4%BD%BF%E7%94%A8%E5%AD%98%E5%82%A8%E5%9C%A8jndi%E4%B8%AD%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE)
    - [6.1.3 服务器配置](#613-%E6%9C%8D%E5%8A%A1%E5%99%A8%E9%85%8D%E7%BD%AE)
      - [(1) 随机端口配置](#1-%E9%9A%8F%E6%9C%BA%E7%AB%AF%E5%8F%A3%E9%85%8D%E7%BD%AE)
      - [(2) 让程序支持HTTPS](#2-%E8%AE%A9%E7%A8%8B%E5%BA%8F%E6%94%AF%E6%8C%81https)
    - [6.1.4 日志配置](#614-%E6%97%A5%E5%BF%97%E9%85%8D%E7%BD%AE)
      - [(1) Spring的默认配置](#1-spring%E7%9A%84%E9%BB%98%E8%AE%A4%E9%85%8D%E7%BD%AE)
      - [(2) 细粒度日志等级](#2-%E7%BB%86%E7%B2%92%E5%BA%A6%E6%97%A5%E5%BF%97%E7%AD%89%E7%BA%A7)
      - [(3) 配置日志输出文件](#3-%E9%85%8D%E7%BD%AE%E6%97%A5%E5%BF%97%E8%BE%93%E5%87%BA%E6%96%87%E4%BB%B6)
    - [6.1.5 使用特定的属性值](#615-%E4%BD%BF%E7%94%A8%E7%89%B9%E5%AE%9A%E7%9A%84%E5%B1%9E%E6%80%A7%E5%80%BC)
      - [(1) 属性值引用](#1-%E5%B1%9E%E6%80%A7%E5%80%BC%E5%BC%95%E7%94%A8)
  - [6.2 创建自己的Configuration Properties](#62-%E5%88%9B%E5%BB%BA%E8%87%AA%E5%B7%B1%E7%9A%84configuration-properties)
    - [6.2.1 定义Configuration Properties的持有者](#621-%E5%AE%9A%E4%B9%89configuration-properties%E7%9A%84%E6%8C%81%E6%9C%89%E8%80%85)
      - [(1) 添加dependency和exclusion](#1-%E6%B7%BB%E5%8A%A0dependency%E5%92%8Cexclusion)
      - [(2) 编写Configuration Properties](#2-%E7%BC%96%E5%86%99configuration-properties)
      - [(3) 使用Configuration Properties](#3-%E4%BD%BF%E7%94%A8configuration-properties)
      - [(4) 给Configuration Properties填写配置值](#4-%E7%BB%99configuration-properties%E5%A1%AB%E5%86%99%E9%85%8D%E7%BD%AE%E5%80%BC)
    - [6.2.2 声明Configuration  Properties的元数据](#622-%E5%A3%B0%E6%98%8Econfiguration--properties%E7%9A%84%E5%85%83%E6%95%B0%E6%8D%AE)
      - [(1) 编写元数据声明（可选）](#1-%E7%BC%96%E5%86%99%E5%85%83%E6%95%B0%E6%8D%AE%E5%A3%B0%E6%98%8E%E5%8F%AF%E9%80%89)
      - [(2) 用途](#2-%E7%94%A8%E9%80%94)
  - [6.3  使用profile进行配置](#63--%E4%BD%BF%E7%94%A8profile%E8%BF%9B%E8%A1%8C%E9%85%8D%E7%BD%AE)
    - [6.3.1 定义特定profile的属性](#631-%E5%AE%9A%E4%B9%89%E7%89%B9%E5%AE%9Aprofile%E7%9A%84%E5%B1%9E%E6%80%A7)
      - [(1) 方法1：通过文件名约定为每个profile编写一个配置文件](#1-%E6%96%B9%E6%B3%951%E9%80%9A%E8%BF%87%E6%96%87%E4%BB%B6%E5%90%8D%E7%BA%A6%E5%AE%9A%E4%B8%BA%E6%AF%8F%E4%B8%AAprofile%E7%BC%96%E5%86%99%E4%B8%80%E4%B8%AA%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)
      - [(2) 方法2：在一个yml配置文件中配置多套环境](#2-%E6%96%B9%E6%B3%952%E5%9C%A8%E4%B8%80%E4%B8%AAyml%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E4%B8%AD%E9%85%8D%E7%BD%AE%E5%A4%9A%E5%A5%97%E7%8E%AF%E5%A2%83)
    - [6.3.2  使用`pring.profiles.active`配置激活profile](#632--%E4%BD%BF%E7%94%A8pringprofilesactive%E9%85%8D%E7%BD%AE%E6%BF%80%E6%B4%BBprofile)
      - [(1) 激活某个环境](#1-%E6%BF%80%E6%B4%BB%E6%9F%90%E4%B8%AA%E7%8E%AF%E5%A2%83)
      - [(2) 激活多个环境](#2-%E6%BF%80%E6%B4%BB%E5%A4%9A%E4%B8%AA%E7%8E%AF%E5%A2%83)
    - [6.3.3  使用profile条件化地创建bean](#633--%E4%BD%BF%E7%94%A8profile%E6%9D%A1%E4%BB%B6%E5%8C%96%E5%9C%B0%E5%88%9B%E5%BB%BAbean)
      - [(1) 用途及例子](#1-%E7%94%A8%E9%80%94%E5%8F%8A%E4%BE%8B%E5%AD%90)
  - [6.4 小结](#64-%E5%B0%8F%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH06 使用配置属性（Configuration Properties）

> 对于使用`@ConfigurationProperties`注解的bean，它的属性被称为`Configuration Propertie`，Spring将从多个数据源为这些属性进行注入，包括JVM属性、命令行参数、环境变量等
>
> Demo项目位置：[../ch06/taco-cloud](../ch06/taco-cloud/)

##  6.0 bean装配及属性注入的局限

> Configuration Properties以外的两种配置方式：
>
> * `bean装配`（`bean wiring`）
> * `属性注入`（`property injection`)
>
> 例子和局限：
>
> ~~~java
> @Bean
> public DataSource dataSource() {
> 	return new EmbeddedDatabaseBuilder()
> 		.setType(H2)
> 		.addScript("taco_schema.sql")
> 		.addScripts("user_data.sql", "ingredient_data.sql")
> 		.build();
> }
> ~~~
>
> 上面使用bean装配、配置了程序的DataSource以及SQL脚本，然而当程序需要给SQL脚本指定别名、或者要指定两个以上的SQL脚本时又该怎么办呢？这可以通过Configuration Properties来完成

## 6.1 多配置源细粒度配置

> Spring框架中的很多功能、就是使用Configuration Properties编写的，他们可以从多个数据源中加载细粒度的配置：
>
> * 6.1小节介绍这些功能的使用
> * 6.2小节介绍如何实现类似的功能

### 6.1.1 理解Spring的环境抽象

####  (1) Spring的多配置源聚合 

为了提供一站式配置属性的服务，Spring会拉取多个数据源将其聚合到一起，以便注入到Spring的bean中，具体包括：

> * JVM系统属性
> * 操作系统环境变量
> * 命令行参数
> * 应用属性配置文件（application.properties，application.yml，……）
> * 微服务中的config service

例如想要更改程序的默认端口，以下4种方法都可以

> src/main/resource/application.properties
>
> ~~~properties
> server.port=9090
> ~~~
>
> src/main/resource/application.yml
>
> ~~~yml
> server:
> 	port: 9090
> ~~~
>
> 命令行参数
>
> ~~~bash
> $ java -jar tacocloud-0.0.5-SNAPSHOT.jar --server.port=9090
> ~~~
>
> 环境变量（格式略有不同，Spring会自动把SERVER_PORT转换成server.port
>
> ~~~bash
> $ export SERVER_PORT=9090
> ~~~
>
> 微服务的config service（第14章介绍）

### 6.1.2 配置DataSource

#### (1) 为生产环境增加MySQL DataSource配置

> 之前的代码，在开发环境下使用嵌入式内存数据库，现在需要为生产环境配置MySQL数据库
>
> 代码位置：[/src/main/resources/application.yml](../ch06/taco-cloud/src/main/resources/application.yml)
>
> ~~~yml
> ---
> spring:
>   	profiles: prod
>   	datasource:
>    		url: jdbc:mysql://localhost/tacocloud
>    		username: tacouser
>    		password: tacopassword
>    		# 没有配置driver-class-name时、Spring根据url自动推断并在class path中查找可用的driver class
> 		# * 首选HikariCP Connection Pool
>		# * 次选Tomcat JDBC Connection Pool
> 		# * 以及Apache Commons DBCP2
>		# 如果需要、也可以自己指定，例如下面的代码
> 		# driver-class-name: com.mysql.jdbc.Driver
> ~~~
> 

#### (2) 声明应用程序启动时要执行的数据库初始化脚本

> ~~~yml
> spring:
>   	datasource:
>    		schema:
> 		- order-schema.sql
> 		- ingredient-schema.sql
> 		- taco-schema.sql
> 		- user-schema.sql
> 		data:
> 		- ingredients.sql
> ~~~

#### (3) 使用存储在JNDI中的数据库配置

> ~~~yml
> spring:
>   	datasource:
>    		jndi-name: java:/comp/env/jdbc/tacoCloudDS
> ~~~
>
> 一旦设置了`spring.datasource.jndi-name`，其他的DB Connection配置会被忽略

### 6.1.3 服务器配置

#### (1) 随机端口配置

> 将`server.port`配置为0时：
>
> * 程序会使用随机端口，通常用在自动化集测试中以同时运行多个测试 
>
> * 另外在微服务中有时也会使用随机端口、因为可以通过service registry查找端口，所以并不会在意配置了什么端口

#### (2) 让程序支持HTTPS

> 步骤1：创建keystore，会提示输入一些内容、其中输入password时需要记住输入内容
>
> ~~~bash
> $ keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA
> ~~~
>
> 步骤2：
>
> ~~~yml
> server:
>   	port: 8443
>   	ssl:
>    		# key-store指向上一步生成的文件，如果该文件打包在jar包中，需要把file:改成classpath:
>    		key-store: file:///path/to/mykeys.jks 
>    		# 下面的两个password为生成jks文件时输入的密码  
>    		key-store-password: letmein
>    		key-password: letmein
> ~~~

### 6.1.4 日志配置 

#### (1) Spring的默认配置 

> Spring默认通过Logback([http://logback.qos.ch](http://logback.qos.ch/)) 日志以INFO级别写入到Console中
>
> * 如需触及完整的配置(full control)，可在/src/main/resources/中创建logback.xml文件
>
>     ~~~xml
>     <configuration>
>       <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
>         <encoder>
>           <pattern>
>             %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
>           </pattern>
>         </encoder>
>       </appender>
>       <logger name="root" level="INFO"/>
>       <root level="INFO">
>         <appender-ref ref="STDOUT" />
>       </root>
>     </configuration>
>     ~~~
>
> * 对于常用的配置修改，可以通过Spring配置来完成，

#### (2) 细粒度日志等级

> ~~~yml
> logging:
>   	level:
>    		root: WARN
>    		org:
>    			springframework:
>    				security: DEBUG
> ~~~
>
> 或者下面可读性更强的书写方式 
>
> ~~~yml
> logging:
>   	level:
>    		root: WARN  # 默认日志等级为WARN
>    		org.springframework.security: DEBUG # 来自security包的日志等级为DEBUG
> ~~~

#### (3) 配置日志输出文件

> ~~~yml
> logging:
>   	file:
>    		path: /var/logs/
>    		file: TacoCloud.log
>   	level:
>    		root: WARN
>    		org:
>    			springframework:
>    				security: DEBUG
> ~~~
>
> 该配置会把日志写入到/var/logs/TacoCloud.log（注意确保程序有权限写这个目录），使用默认的10MB日志文件rotate size

### 6.1.5 使用特定的属性值

#### (1) 属性值引用

> ~~~yml
> greeting:
>   	welcome: You are using ${spring.application.name}.
> ~~~

## 6.2 创建自己的Configuration Properties

> 如上一节中的一些例子、借助configuration properties，SpringBoot使得属性注入变得非常容易，并实现细粒度配置。其实在自己写的代码中，同样也可以使用configuration  properties 

### 6.2.1 定义Configuration Properties的持有者

#### (1) 添加dependency和exclusion

需要在[pom.xml](../ch06/taco-cloud/pom.xml)中添加dependency和exclusion以支持configuration properties（可能在后续Spring Boot版本中将不再需要）

添加内容：

> https://github.com/fangkun119/spring-in-action-6-samples/commit/df2da636abebe1b8c8f023d0dbe00d75b808ba95?branch=df2da636abebe1b8c8f023d0dbe00d75b808ba95&diff=unified

相关文档：

> https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/appendix-configuration-metadata.html#configuration-metadata-annotation-processor

#### (2) 编写Configuration Properties

> 如本章开头所说，编写Configuration Properties只需要给一个bean加上@ConfigurationProperties注解，例如
>
> [/src/main/java/tacos/web/OrderProps.java](../ch06/taco-cloud/src/main/java/tacos/web/OrderProps.java)
>
> ```java
> @Component
> // 指明这个bean的属性值来自于Spring Environemnt
> // prefix="taco.orders"表示
> //   如果想要给pageSize属性设置取值
> //   需要在为Spring配置"taco.orders.pageSize"属性
> @ConfigurationProperties(prefix="taco.orders")
> @Data
> @Validated
> public class OrderProps {
>   	@Min(value=5,  message="must be between 5 and 25")
>   	@Max(value=25, message="must be between 5 and 25")
>   	private int pageSize = 20; // 默认值20，可以被小节(3)的配置覆盖
> }
> ```

#### (3) 使用Configuration Properties

> [/src/main/java/.../web/OrderController.java](../ch06/taco-cloud/src/main/java/tacos/web/OrderController.java)
>
> ```java
> @Controller
> @RequestMapping("/orders")
> @SessionAttributes("order")
> public class OrderController {
>    	// 注入OrderProps props
>    	private OrderRepository orderRepo;
>    	private OrderProps props;
>    	public OrderController(OrderRepository orderRepo, OrderProps props) {
>    		this.orderRepo = orderRepo;
>    		this.props = props;
>    	}
> 	...
>    	@GetMapping
> 	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
> 		// 使用OrderProps props
>    		Pageable pageable = PageRequest.of(0, props.getPageSize());
>    		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
>    		return "orderList";
>    	}
>    }
>    ```

#### (4) 给Configuration Properties填写配置值

> 使用配置文件、例如：[/src/main/resources/application.yml](../ch06/taco-cloud/src/main/resources/application.yml)
>
> ```java
> taco:
>   	orders:
>    		pageSize: 10
> ```
>
> 或者使用环境变量
>
> ~~~bash
> $ export TACO_ORDERS_PAGESIZE=10
> ~~~
>
> 或者命令行参数等都可以

### 6.2.2 声明Configuration  Properties的元数据

#### (1) 编写元数据声明（可选）

> 代码：[/src/main/resources/META-INF/additional-spring-configuration-metadata.json](../ch06/taco-cloud/src/main/resources/META-INF/additional-spring-configuration-metadata.json)
>
> ~~~json
> {
>   	"properties": [{
>    		"name": "taco.orders.pageSize",
>    		"type": "java.lang.String",
>    		"description": "Sets the maximum number of orders to display in a list."
>    	}, {
>    		"name": "taco.discount.codes",
>    		"type": "java.util.Map<String, Integer>",
>    		"description": "A map of discount codes to a discount percentage."
>    	}]
>    }
>    ~~~
>   
> 如果使用STS（Spring Tool Suite）、在缺少元数据声明时，IDE会提示并提供quick-fix提示框来快速跳转到配置文件上

#### (2) 用途

> * 元数据声明是可选的，不添加程序也可以正常运行
> * 但是如果不添加，某些IDE会报警，提示“unknown property taco”等
> * 并且添加之后
>     * IDE可以识别诸如“taco.orders.pageSize”、"taco.discount.codes"之类的配置项
>     * 在代码编写过程中 ，IDE可以给出提示
>     * 同时元数据也可以起到文档的作用，并且在一定程度上预防配置出错

## 6.3  使用profile进行配置

> 例如
>
> * 在开发和调试时、使用嵌入式H2数据库，日志级别为DEBUG
>
> * 在生产环境中、使用外部的MySQL数据库并将日志级别设置为WARN

### 6.3.1 定义特定profile的属性

#### (1) 方法1：通过文件名约定为每个profile编写一个配置文件

> 例如`application-{profile_name}.yml`或者`application-{profile_name}.properties`

#### (2) 方法2：在一个yml配置文件中配置多套环境 

> 例子：[/src/main/resources/application.yml](../ch06/taco-cloud/src/main/resources/application.yml)
>
> ~~~yml
> # 默认配置
> spring:
>   	security:
>    		user:
>    			name: buzz
>    			password: infinity
>   	datasource:
>    		generate-unique-name: false
>    		name: tacocloud
> taco:
>   	orders:
>    		pageSize: 10
>   	discount:
>    		codes:
>    			abcdef: 10
> 
> # 环境之间的分割线
> ---
> spring:
>   	# 环境名称
>   	profiles: prod
>   	datasource:
>    		url: jdbc:mysql://localhost/tacocloud
>    		username: tacouser
>    		password: tacopassword
> 	logging:
>   		level:
>     			tacos: WARN
> ~~~

### 6.3.2  使用`pring.profiles.active`配置激活profile

#### (1) 激活某个环境

> 例如，在application.yml中配置
>
> ~~~yml
> spring:
>   	profiles:
>    		active:
> 		- prod
> ~~~
>
> 或者使用环境变量
>
> ~~~bash
> % export SPRING_PROFILES_ACTIVE=prod
> ~~~
>
> 或者命令行参数
>
> ~~~bash
> % java -jar taco-cloud.jar --spring.profiles.active=prod
> ~~~

#### (2) 激活多个环境

> 例如
>
> ~~~bash
> % export SPRING_PROFILES_ACTIVE=prod,audit,ha
> ~~~
>
> 或者在application.yml中配置
>
> ~~~yml
> spring:
>   	profiles:
>    		active:
> 		- prod
> 		- audit
> 		- ha
> ~~~
>
> 这样可以在部署到特定环境时激活一些特定配置，例如部署在Cloud Foundry时一个名为cloud的profile会被自动激活

### 6.3.3  使用profile条件化地创建bean

#### (1) 用途及例子

> 当某个bean，或者某个Java Config类里所包含的所有bean
>
> 例子：[/src/main/java/.../DevelopmentConfig.java](../ch06/taco-cloud/src/main/java/tacos/DevelopmentConfig.java)
>
> ```java
> // 只在非生产环境下初始化该Java Config Class中配置的bean
> @Profile("!prod")
> @Configuration
> public class DevelopmentConfig {
>    	// CommandLineRunner Bean
>    	// 用来在启动应用时加载嵌入式数据库和填充数据库
>    	@Bean
>    	// @Profile({"!prod", "!qa"})
>    	// @Profile({"dev",    "qa"}) 
>    	// 上面两个注解不需要了，已经在Config Class上添加了@Profile注解
>    	public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
>    		return new CommandLineRunner() {
>    			@Override
>    			public void run(String... args) throws Exception {
>    				repo.deleteAll();
>    				userRepo.deleteAll();
>    				repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
>    				repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
>    				repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
>    				repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
>    				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
>    				repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
>    				repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
>    				repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
>    				repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
>    				repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
>    				userRepo.save(new User("habuma", encoder.encode("password"),
>                         "Craig Walls", "123 North Street", "Cross Roads", "TX",
>                         "76227", "123-123-1234"));
>    			}
>    		};
>    	}
> }
> ```

## 6.4 小结

> 略