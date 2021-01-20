# CH01 Spring起步

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [CH01 Spring起步](#ch01-spring%E8%B5%B7%E6%AD%A5)
  - [1.1 Spring介绍](#11-spring%E4%BB%8B%E7%BB%8D)
  - [1.2 初始化Spring应用](#12-%E5%88%9D%E5%A7%8B%E5%8C%96spring%E5%BA%94%E7%94%A8)
    - [1.2.1 使用IDE初始化Spring项目](#121-%E4%BD%BF%E7%94%A8ide%E5%88%9D%E5%A7%8B%E5%8C%96spring%E9%A1%B9%E7%9B%AE)
    - [1.2.1 查看Spring项目结构](#121-%E6%9F%A5%E7%9C%8Bspring%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84)
      - [(1) `SpringBootApplication`注解](#1-springbootapplication%E6%B3%A8%E8%A7%A3)
      - [(2) 测试上下文初始化](#2-%E6%B5%8B%E8%AF%95%E4%B8%8A%E4%B8%8B%E6%96%87%E5%88%9D%E5%A7%8B%E5%8C%96)
  - [1.3 编写Spring应用](#13-%E7%BC%96%E5%86%99spring%E5%BA%94%E7%94%A8)
    - [1.3.1 处理Web请求](#131-%E5%A4%84%E7%90%86web%E8%AF%B7%E6%B1%82)
    - [1.3.2 定义视图](#132-%E5%AE%9A%E4%B9%89%E8%A7%86%E5%9B%BE)
    - [1.3.3 测试Controller](#133-%E6%B5%8B%E8%AF%95controller)
    - [1.3.4 构建和运行](#134-%E6%9E%84%E5%BB%BA%E5%92%8C%E8%BF%90%E8%A1%8C)
    - [1.3.5 了解Spring Boot DevTools (STS)](#135-%E4%BA%86%E8%A7%A3spring-boot-devtools-sts)
      - [(1) 功能特点](#1-%E5%8A%9F%E8%83%BD%E7%89%B9%E7%82%B9)
    - [1.3.6 小节回顾](#136-%E5%B0%8F%E8%8A%82%E5%9B%9E%E9%A1%BE)
  - [1.4 俯瞰Spring风景线](#14-%E4%BF%AF%E7%9E%B0spring%E9%A3%8E%E6%99%AF%E7%BA%BF)
  - [1.5 小结](#15-%E5%B0%8F%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 本章Demo项目的位置：[../ch01/taco-cloud/](../ch01/taco-cloud/)
> * 格式形如`1.2.1`的章节序号为原书的章节序号、大部分内容用一两句话简单概括
> * 格式形如`(1)/(2)/(3)`的序号为在笔记中重点展开的内容

## 1.1 Spring介绍

> 关于Spring容器，和依赖注入（DI），旧式的XML配置和现在优先选用的Java配置及自动装配等

## 1.2 初始化Spring应用

### 1.2.1 使用IDE初始化Spring项目

> 用Spring Tool Suite，或者IntelliJ IDEA，NEtBeans，start.spring.io，命令行、元框架、等创建Spring应用。以及如何在命令行构建和运行项目。具体内容略。

### 1.2.1 查看Spring项目结构

> Spring项目创建后，各个目录和组成部分的用途。摘选下面两部分，其他内容略。

#### (1) `SpringBootApplication`注解

> ~~~java
> ...
> @SpringBootConfiguration
> @EnableAutoConfiguration
> @ComponentScan(
>     excludeFilters = {@Filter(
>     type = FilterType.CUSTOM,
>     classes = {TypeExcludeFilter.class}
> ), @Filter(
>     type = FilterType.CUSTOM,
>     classes = {AutoConfigurationExcludeFilter.class}
> )}
> )
> public @interface SpringBootApplication {
>     ...
> }
> ~~~
>
> @SpringBootApplication是一个组合注解，它组合了3个其他的注解。
>
> * `@SpringBootConfiguration`：将该类声明为配置类、是@Configuration注解的特殊形式。
> * `@EnableAutoConfiguration`：启用SpringBoot的自动配置。
> * `@ComponentScan`：启用组件扫描，能够自动发现并注册哪些、被@Component、@Controller、@Service等注解的类

#### (2) 测试上下文初始化

> 代码（Spring Boot 2.4.1）：[/src/test/.../TacoCloudApplicationTests.java](../ch01/taco-cloud/src/test/java/tacos/TacoCloudApplicationTests.java)
>
> ~~~java
> @SpringBootTest                 // <1>
> public class TacoCloudApplicationTests {
>   @Test                         // <2>
>   public void contextLoads() {
>       // 空白的测试方法虽然没有内容，但它可以检查Spring上下文是否可以成功加载
>   }
> }
> ~~~
>
> `@SpringBootTest`：用来告诉JUnit在启动测试时要加上Spring Boot的功能，会为测试加载Spring应用上下文。
>
> 对比更早版本的代码（Spring Boot 2.0.4）
>
> ~~~java
> @RunWith(SpringRunner.class)    // <1>
> @SpringBootTest                 // <2>
> public class TacoCloudApplicationTests {
>   @Test                         // <3>
>   public void contextLoads() {
>   }
> }
> // 代码地址：https://github.com/fangkun119/spring-in-action-5-samples/blob/master/ch01/tacos/src/test/java/tacos/TacoCloudApplicationTests.java
> ~~~
>
> 新版的Spring Boot 2.4.0不再需要使用`@RunWith(SpringRunner.class)`，
>
> * `@RunWith`是JUnit的注解，`SpringRunner.class`是提供给Junit的运行器用来创建Spring应用上下文
> * 新版的Spring Boot 2.4.0虽然不使用这个注解，但仍然能够创建Spring上下文

## 1.3 编写Spring应用

### 1.3.1 处理Web请求

> 将url映射到Controller的方法，如何在返回值中指定Thymeleaf模板
>
> 代码：[/src/main/java/tacos/HomeController.java](../ch01/taco-cloud/src/main/java/tacos/HomeController.java)

### 1.3.2 定义视图

> Thymeleaf模板，其中`<img th:src="@{/images/TacoCloud.png}"/>`中涉及到的上下文路径相对引用
>
> 代码：[/src/main/resources/templates/home.html](../ch01/taco-cloud/src/main/resources/templates/home.html)

### 1.3.3 测试Controller和Thymeleaf模板

#### (1) 测试Controller

> 代码：[/src/test/java/tacos/HomeControllerTest.java](../ch01/taco-cloud/src/test/java/tacos/HomeControllerTest.java)
>
> ```java
> @WebMvcTest(HomeController.class)   // <1> 让测试在Spring MVC的上下文中执行
> public class HomeControllerTest {
> 
>   @Autowired
>   private MockMvc mockMvc;   // <2> Mock MVC所需要的服务器
> 
>   @Test
>   public void testHomePage() throws Exception {
>     mockMvc.perform(get("/"))    // <3> 发起对"/"的GET请求
>       .andExpect(status().isOk())  // <4> 期望得到HTTP 200
>       .andExpect(view().name("home"))  // <5> 期望得到视图的逻辑名是”home“
>       .andExpect(content().string(           // <6> 期望包含“Welcome to..."
>           containsString("Welcome to...")));
>   }
> }
> ```

#### (2) 测试Thymeleaf模板

> 代码：[/src/test/java/tacos/HomePageBrowserTest.java](/src/test/java/tacos/HomePageBrowserTest.java)

### 1.3.4 构建和运行

> 在Spring Tool Suite中构建和运行程序，书本附录中有使用其他IDE的运行方法

### 1.3.5 了解Spring Boot DevTools (STS)

> 是Spring官网上提供的IDE

#### (1) 功能特点

> * 代码变更后应用会自动重启（重新加载项目代码并重启Spring容器，JVM和其他类保持不变）
> * 当面向浏览器的资源（如Thymeleaf、FreeMarker模板、JavaScript、样式表）等发生变化时，会自动刷新浏览器；
> * 自动禁用模板缓存；
> * 如果使用H2数据库的话，内置了H2控制台（http://localhost:8080/h2console）

### 1.3.6 小节回顾

> 略

## 1.4 俯瞰Spring风景线

> * Spring核心框架：MVC、REST、WebFlus（反应式Web框架）
> * Spring Boot：starter、自动装配、actuator（监控）、测试支持、基于Groovy的命令行接口（CLI）
> * Spring Data：整合各种数据库
> * Spring Security：身份验证、授权、API安全性
> * Spring Integration和Spring Batch：解决实时集成、以及批处理集成的问题
> * Spring Cloud：微服务及云原生应用程序

## 1.5 小结

> 略