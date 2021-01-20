<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH02 开发Web应用](#ch02-%E5%BC%80%E5%8F%91web%E5%BA%94%E7%94%A8)
  - [2.1 展现信息](#21-%E5%B1%95%E7%8E%B0%E4%BF%A1%E6%81%AF)
    - [2.1.1 构建Domain Object](#211-%E6%9E%84%E5%BB%BAdomain-object)
      - [(1) 添加`lombok`编译依赖](#1-%E6%B7%BB%E5%8A%A0lombok%E7%BC%96%E8%AF%91%E4%BE%9D%E8%B5%96)
      - [(2) 使用`lombok`注解编写Domain Object](#2-%E4%BD%BF%E7%94%A8lombok%E6%B3%A8%E8%A7%A3%E7%BC%96%E5%86%99domain-object)
    - [2.1.2 创建Controller](#212-%E5%88%9B%E5%BB%BAcontroller)
      - [(1) 代码](#1-%E4%BB%A3%E7%A0%81)
      - [(2) `@Slf4j`](#2-slf4j)
      - [(3) Spring MVC的Request Mapping注解](#3-spring-mvc%E7%9A%84request-mapping%E6%B3%A8%E8%A7%A3)
    - [2.1.3 设计视图](#213-%E8%AE%BE%E8%AE%A1%E8%A7%86%E5%9B%BE)
      - [(1) Thymeleaf工作原理](#1-thymeleaf%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86)
  - [2.2 表单提交处理](#22-%E8%A1%A8%E5%8D%95%E6%8F%90%E4%BA%A4%E5%A4%84%E7%90%86)
      - [(1) 表单提交](#1-%E8%A1%A8%E5%8D%95%E6%8F%90%E4%BA%A4)
      - [(2) 处理表单提交](#2-%E5%A4%84%E7%90%86%E8%A1%A8%E5%8D%95%E6%8F%90%E4%BA%A4)
  - [2.3 校验表单输入](#23-%E6%A0%A1%E9%AA%8C%E8%A1%A8%E5%8D%95%E8%BE%93%E5%85%A5)
      - [(1) 依赖](#1-%E4%BE%9D%E8%B5%96)
      - [(2) 步骤](#2-%E6%AD%A5%E9%AA%A4)
    - [2.3.1 声明校验规则](#231-%E5%A3%B0%E6%98%8E%E6%A0%A1%E9%AA%8C%E8%A7%84%E5%88%99)
      - [(1) 代码例子](#1-%E4%BB%A3%E7%A0%81%E4%BE%8B%E5%AD%90)
      - [(2) 常用注解](#2-%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3)
    - [2.3.2 在Controller声明要对哪个表单对象进行校验](#232-%E5%9C%A8controller%E5%A3%B0%E6%98%8E%E8%A6%81%E5%AF%B9%E5%93%AA%E4%B8%AA%E8%A1%A8%E5%8D%95%E5%AF%B9%E8%B1%A1%E8%BF%9B%E8%A1%8C%E6%A0%A1%E9%AA%8C)
      - [(1) `@Valid`及获得校验结果](#1-valid%E5%8F%8A%E8%8E%B7%E5%BE%97%E6%A0%A1%E9%AA%8C%E7%BB%93%E6%9E%9C)
    - [2.3.3 展现校验错误](#233-%E5%B1%95%E7%8E%B0%E6%A0%A1%E9%AA%8C%E9%94%99%E8%AF%AF)
      - [(1) `th:errors`](#1-therrors)
  - [2.4 使用View Controller（视图控制器）](#24-%E4%BD%BF%E7%94%A8view-controller%E8%A7%86%E5%9B%BE%E6%8E%A7%E5%88%B6%E5%99%A8)
      - [(1)  声明视图控制器](#1--%E5%A3%B0%E6%98%8E%E8%A7%86%E5%9B%BE%E6%8E%A7%E5%88%B6%E5%99%A8)
  - [2.5 选择视图模板库](#25-%E9%80%89%E6%8B%A9%E8%A7%86%E5%9B%BE%E6%A8%A1%E6%9D%BF%E5%BA%93)
      - [(1) Spring支持的模板方案](#1-spring%E6%94%AF%E6%8C%81%E7%9A%84%E6%A8%A1%E6%9D%BF%E6%96%B9%E6%A1%88)
    - [2.5.1 缓存模板](#251-%E7%BC%93%E5%AD%98%E6%A8%A1%E6%9D%BF)
  - [2.6 小结](#26-%E5%B0%8F%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 本章Demo项目的位置：[../ch02/taco-cloud/](../ch02/taco-cloud/)
> * 格式形如`1.2.1`的章节序号为原书的章节序号、大部分内容用一两句话简单概括
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH02 开发Web应用

> 内容涉及：（1）在浏览器中展现模型数据；（2）处理和校验表单输入；（3）选择视图模板库

## 2.1 展现信息

> 内容涉及：（1）定义Domain Object；（2）将其传递给Controller；（3）视图渲染

### 2.1.1 构建Domain Object

> 使用lambok编写Domain Object，可以自动生成所需的方法，避免手工编写，让代码简洁

#### (1) 添加`lombok`编译依赖

> 可以在创建项目选择Starter时，查找和勾选lombok；也可以在[pom.xml](../ch02/taco-cloud/pom.xml)中手动添加，该依赖会在开发阶段提供Lombok注解（例如`@Data`），运行时自动生成方法
>
> ```xml
> <dependency>
>    <groupId>org.projectlombok</groupId>
>    <artifactId>lombok</artifactId>
> </dependency>
> ```
>
> 如果IDE报错（例如提示缺少方法、或者final属性没有赋值等，可导Lombok官网查阅并安装相关的IDE插件

#### (2) 使用`lombok`注解编写Domain Object

代码：

> * [/src/main/java/.../Ingredient.java](../ch02/taco-cloud/src/main/java/tacos/Ingredient.java)
> * [/src/main/java/.../Taco.java](../ch02/taco-cloud/src/main/java/tacos/Taco.java)
> * [/src/main/java/.../TacoOrder.java](../ch02/taco-cloud/src/main/java/tacos/TacoOrder.java)

例如：

> ```java
> @Data
> public class Ingredient {
>   private final String id;
>   private final String name;
>   private final Type type;
>   
>   public enum Type {
>     WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
>   }
> }
> ```

### 2.1.2 创建Controller

> 处理Web页面用的Controller（提供Rest API的Controller见第6章）

#### (1) 代码

>  [/src/main/java/.../DesignTacoController.java](../ch02/taco-cloud/src/main/java/tacos/web/DesignTacoController.java)
>
> ```java
> @Slf4j // 为这个类自动生成一个名为log的Logger成员对象
> @Controller // 是@Component的一个特殊形式，因此也是IOC组件扫描的候选者
> @RequestMapping("/design") // 作用域这个类的所有方法
> @SessionAttributes("tacoOrder")
> public class DesignTacoController {
>     // 将Domain Objects（卷饼材料）添加到model中
>     @ModelAttribute
>     public void addIngredientsToModel(Model model) {
>         List<Ingredient> ingredients = Arrays.asList(
>                 new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
>                 ...
>                 new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
>         );
>         Type[] types = Ingredient.Type.values();
>         for (Type type : types) {
>             model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
>         }
>     }
> 
>     private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
>         return ingredients
>                 .stream()
>                 .filter(x -> x.getType().equals(type))
>                 .collect(Collectors.toList());
>     }
> 
>     // 处理"GET /design"请求
>     @GetMapping // 相当于@RequestMapping(method=RequestMethod.GET)
>     public String showDesignForm(Model model) {
>         model.addAttribute("taco", new Taco());
>         return "design"; // 指向名为”design“的thymeleaf模板
>     }
> 
> 	// 处理"POST /desgin"请求
>     @PostMapping
>     public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors) {
>         if (errors.hasErrors()) {
>             return "design";
>         }
>         // Save the taco...
>         // We'll do this in chapter 3
>         log.info("Processing taco: " + taco);
>         return "redirect:/orders/current";
>     }
> }
> ```

#### (2) `@Slf4j`

> `@Slf4j`：Lombok提供的注解、运行时会在这个类中自动生成一个SLF4J（Simple Logging Facade for Java）Logger，其效果等同于在代码中显式地添加
>
> ~~~java
> private static final org.slf4j.Logger log = 
>     org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);
> ~~~
>
> 添加`@Slf4j`之后，就可以直接调用生成的log对象来打印日志
>
> ~~~java
> log.info("Processing taco: " + taco);
> ~~~

#### (3) Spring MVC的Request Mapping注解

> @RequestMapping，@GetMapping，@PostMapping，@PutMapping，@DeleteMapping，@PatchMapping

### 2.1.3 设计视图

thymeleaf标签部分略去，章节介绍非常简单，更多内容可参考 

> [https://github.com/fangkun119/java_proj_ref/blob/master/002_spring_boot/springboot_note/02_thymeleaf.md](https://github.com/fangkun119/java_proj_ref/blob/master/002_spring_boot/springboot_note/02_thymeleaf.md)

代码

> [/src/main/resources/templates/design.html](../ch02/taco-cloud/src/main/resources/templates/design.html)

#### (1) Thymeleaf工作原理

> 引入thymeleaf starter后，Spring Boot的自动配置功能会发现Thymeleaf在类路径中，因此会为Spring MVC创建支持Thymeleaf视图的bean
>
> ```xml
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-thymeleaf</artifactId>
> </dependency>
> ```
>
> Thymeleaf与Web框架是解耦的，因此无法感知到Spring的Controller和Model等。Spring将model放入Servlet的request属性中、供Thymeleaf或其他视图模板使用

## 2.2 表单提交处理

#### (1) 表单提交

Taco制作页面的表单提交

> ~~~html
> <form method="POST" th:object="${taco}">
>     ...
> </form>
> ~~~
>
> * `<form>`没有声明action属性，因此post请求将按默认值发送到与GET请求相同的路径上，也就是"/design"
> * 完整代码：[/src/main/resources/templates/design.html](../ch02/taco-cloud/src/main/resources/templates/design.html)

Tock订单页面的表单提交

> ~~~xml
> <form method="POST" th:action="@{/orders}" th:object="${tacoOrder}">
>     ...
> </form>
> ~~~
>
> * `<form>`通过` th:action="@{/orders}" `指定了处理请求的url路径
>
> * 完整代码：[/src/main/resources/templates/orderForm.html](../ch02/taco-cloud/src/main/resources/templates/orderForm.html)

#### (2) 处理表单提交

> ```java
> // 处理"POST /desgin"请求
> @PostMapping
> public String processTaco(
>     // 将thymeleaf模板中的`th:object="${taco}"`绑定到Model对象`Taco taco`上
>     // 而thymeleaf模板中对"${taco}"属性的使用，也与Domain Object Taco的属性一一对应
>     @Valid @ModelAttribute("taco") Taco taco, 
>     Errors errors) {
>   if (errors.hasErrors()) {
>     return "design";
>   }
>   // Save the taco...
>   // We'll do this in chapter 3
>   log.info("Processing taco: " + taco);
>   // 加上"redirect:“前缀表示将触发浏览器重定向
>   // 将会由负责该url的OrderController.orderForm方法来处理
>   return "redirect:/orders/current";
> }
> ```
>
> 完整代码：[/src/main/java/.../DesignTacoController.java](../ch02/taco-cloud/src/main/java/tacos/web/DesignTacoController.java)
>
> ```java
> @Slf4j
> @Controller
> @RequestMapping("/orders")
> public class OrderController {
>   // 处理”GET /orders/current"请求的方法
>   @GetMapping("/current")
>   public String orderForm(Model model) {
>     model.addAttribute("tacoOrder", new TacoOrder());
>     // 对应的thymeleaf模板为orderForm
>     return "orderForm";
>   }
> 
>   // 处理“POST /orders"请求的方法
>   @PostMapping
>   public String processOrder(@Valid TacoOrder order, Errors errors) {
>     // 
>     if (errors.hasErrors()) {
>       return "orderForm";
>     }
>     log.info("Order submitted: " + order);
>     return "redirect:/";
>   }
> }
> ```
>
> 代码：[/src/main/java/.../web/OrderController.java](../ch02/taco-cloud/src/main/java/tacos/web/OrderController.java)

## 2.3 校验表单输入

#### (1) 依赖

> 无需额外引入，Spring Boot Web Starter会自动将Validation API及其Hibernate实现引入到项目中

#### (2) 步骤

> * 在被校验的类上声明校验规则：
> * 在Controller的方法上声明要对哪个对象进行校验：
> * 修改表单视图以展现校验错误：

### 2.3.1 声明校验规则

#### (1) 代码例子

> ```java
> @Data
> public class TacoOrder {
>   @NotBlank(message="Delivery name is required")
>   private String deliveryName;
> 
>   ...
> 
>   @CreditCardNumber(message="Not a valid credit card number")
>   private String ccNumber;
> 
>   @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
>            message="Must be formatted MM/YY")
>   private String ccExpiration;
> 
>   @Digits(integer=3, fraction=0, message="Invalid CVV")
>   private String ccCVV;
> 
>   ...
> }
> ```
>
> 完整代码：
>
> * [/src/main/java/.../Taco.java](../ch02/taco-cloud/src/main/java/tacos/Taco.java)
>
> * [/src/main/java/.../TacoOrder.java](../ch02/taco-cloud/src/main/java/tacos/TacoOrder.java)

#### (2) 常用注解

> 参考：https://github.com/fangkun119/java_proj_ref/blob/master/002_spring_boot/springboot_note/01_spring_boot_demos.md

### 2.3.2 在Controller声明要对哪个表单对象进行校验

#### (1) `@Valid`及获得校验结果

> ```java
> @PostMapping
> public String processTaco(
>     // @Valid表示对taco对象进行参数校验，校验规则注解在Taco类的代码中
>     // 如果校验通过，`errors.hasErrors()`将为false
>     // 如果校验错误，`errors.hasErrors()`为true，并且表单视图可以知道具体的错误以便提示用户
>     @Valid @ModelAttribute("taco") Taco taco, 
>     Errors errors) {
>   if (errors.hasErrors()) {
>     return "design";
>   }
>   
>   ...
>   
>   log.info("Processing taco: " + taco);
>   return "redirect:/orders/current";
> }
> ```
>
> 完整代码：[/src/main/java/.../DesignTacoController.java](../ch02/taco-cloud/src/main/java/tacos/web/DesignTacoController.java)

### 2.3.3 展现校验错误

#### (1) `th:errors`

>```java
>@CreditCardNumber(message="Not a valid credit card number")
>//tag::allButValidation[]
>private String ccNumber;
>```
>
>完整代码：[/src/main/java/.../TacoOrder.java](../ch02/taco-cloud/src/main/java/tacos/TacoOrder.java)
>
>```html
><div th:if="${#fields.hasErrors()}">
>    <span class="validationError">Please correct the problems below and resubmit.</span>
></div>
>
>...
>
><h3>Here's how I'll pay...</h3>
><label for="ccNumber">Credit Card #: </label>
><input type="text" th:field="*{ccNumber}"/>
><!--当ccNumber格式错误时、渲染一个span来提示错误，并用后端传来的message替换占位符CC Num Error-->
><span class="validationError"
>      th:if="${#fields.hasErrors('ccNumber')}"
>      th:errors="*{ccNumber}">CC Num Error</span>
><br/>
>```
>
>完整代码：[/src/main/resources/templates/orderForm.html](/src/main/resources/templates/orderForm.html)
>
>`class="validationError"`：css样式
>
>`th:errors`：builds a list with all the errors for the specified selector, separated by `<br />`

## 2.4 使用View Controller（视图控制器）

#### (1)  声明视图控制器 

视图控制器：只是简单地将请求转发给View而不做其他事情

> 与[`DesignTacoController`](../ch02/taco-cloud/src/main/java/tacos/web/DesignTacoController.java)，[`OrderController`](../ch02/taco-cloud/src/main/java/tacos/web/OrderController.java)这样的普通Controller不同
>
> 视图控制器不需要填充model、也不需要处理输入

声明视图控制器的代码：[/src/main/java/.../web/WebConfig.java](../ch02/taco-cloud/src/main/java/tacos/web/WebConfig.java)

> ```java
> // WebMvcConfigurer接口为配置Spring MVC的方法提供了默认实现，实现该接口后只需要覆盖所需的方法即可
> @Configuration
> public class WebConfig implements WebMvcConfigurer { 
>   @Override
>   public void addViewControllers(ViewControllerRegistry registry) {
>     // 将对uri "/"的请求转发到视图"home"
>     // 对应的thymeleaf模板为/src/main/resources/templates/home.html
>     registry.addViewController("/").setViewName("home");
>   }
> }
> ```

测试代码：[/src/test/java/.../HomeControllerTest.java](../ch02/taco-cloud/src/test/java/tacos/HomeControllerTest.java)

> ```java
> @WebMvcTest // <1>与普通Controller的测试类相比，不需要@WebMvcTest(HomeController.class)，注解参数留空
> public class HomeControllerTest {
>   @Autowired
>   private MockMvc mockMvc;   // <2> Mock MVC所需要的服务器
> 
>   @Test
>   public void testHomePage() throws Exception {
>     mockMvc.perform(get("/"))    // <3> 发起对"/"的GET请求
>       .andExpect(status().isOk())  // <4> 期望得到HTTP 200
>       .andExpect(view().name("home"))  // <5> 期望得到视图的逻辑名是”home“
>       .andExpect(content().string(containsString("Welcome to..."))); // <6> 期望包含“Welcome to..."
>   }
> }
> ```

## 2.5 选择视图模板库

#### (1) Spring支持的模板方案 

> | 模板             | Spring  Boot  Starter依赖            |
> | ---------------- | ------------------------------------ |
> | FreeMarker       | spring-boot-starter-freemarker       |
> | Groovy Templates | spring-boot-starter-groovy-templates |
> | JSP              | 无（由Tomcat或 Jetty提供）           |
> | Mustache         | spring-boot-starter-mustache         |
> | Thymeleaf        | spring-boot-starter-thymeleaf        |
>
> * 只需将starter依赖添加到pom.xml中（或gradle的构建文件中），spring boot就可以探测到所选择的模板库，并生成所需的各种组件 
> * 不论使用哪种模板方案 ，模板都存放在templates目录下
> * 只有在将应用构建为WAR文件并部署到传统的Servlet容器中时，才能选择JSP方案（会在“/WEBINF”目录下寻找JSP，而 JAR包部署无法满足这个需求）
>

### 2.5.1 缓存模板

> 模板只在第一次使用时解析，之后只使用缓存的解析结果，用来提升性能
>
> 但开发时，需要频繁修改模板，需要禁用模板缓存，配置项如下
>
> | 模板            | 配置项                       |
> | --------------- | ---------------------------- |
> | FreeMarker      | spring.freemarker.cache      |
> | Groovy Template | spring.groovy.template.cache |
> | Mustache        | spring.mustache.cache        |
> | Thymeleaf       | spring.thymeleaf.cache       |
>
> 默认情况下这些属性这是为true以启用缓存 ，在dev环境下将其设置为false以禁用缓存 （借助profile）

## 2.6 小结

> 略