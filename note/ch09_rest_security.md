<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH09 Spring Security](#ch09-spring-security)
  - [9.1 OAuth2介绍](#91-oauth2%E4%BB%8B%E7%BB%8D)
    - [(1) 需求](#1-%E9%9C%80%E6%B1%82)
    - [(2) `HTTP Basic authentication`及缺点](#2-http-basic-authentication%E5%8F%8A%E7%BC%BA%E7%82%B9)
      - [(a) 声明对端点的保护](#a-%E5%A3%B0%E6%98%8E%E5%AF%B9%E7%AB%AF%E7%82%B9%E7%9A%84%E4%BF%9D%E6%8A%A4)
      - [(b) 请求REST端点](#b-%E8%AF%B7%E6%B1%82rest%E7%AB%AF%E7%82%B9)
      - [(c) 缺陷](#c-%E7%BC%BA%E9%99%B7)
      - [(d) 解决](#d-%E8%A7%A3%E5%86%B3)
    - [(3) OAuth2授权流程](#3-oauth2%E6%8E%88%E6%9D%83%E6%B5%81%E7%A8%8B)
      - [(a) `Authorization Code Grant`：授权代码授予（常规流程）](#a-authorization-code-grant%E6%8E%88%E6%9D%83%E4%BB%A3%E7%A0%81%E6%8E%88%E4%BA%88%E5%B8%B8%E8%A7%84%E6%B5%81%E7%A8%8B)
      - [(b) `Implicit Grant`：隐式授权（为浏览器运行JS Client设计、不推荐）](#b-implicit-grant%E9%9A%90%E5%BC%8F%E6%8E%88%E6%9D%83%E4%B8%BA%E6%B5%8F%E8%A7%88%E5%99%A8%E8%BF%90%E8%A1%8Cjs-client%E8%AE%BE%E8%AE%A1%E4%B8%8D%E6%8E%A8%E8%8D%90)
      - [(c) `User Credentials (or Password) Grant`：用户凭据（密码）授予](#c-user-credentials-or-password-grant%E7%94%A8%E6%88%B7%E5%87%AD%E6%8D%AE%E5%AF%86%E7%A0%81%E6%8E%88%E4%BA%88)
      - [(d) `Client Credentials Grant`：客户端凭据授予](#d-client-credentials-grant%E5%AE%A2%E6%88%B7%E7%AB%AF%E5%87%AD%E6%8D%AE%E6%8E%88%E4%BA%88)
    - [(4) 本章例子：JWT（Json Web Token） based Authorization Code Grant](#4-%E6%9C%AC%E7%AB%A0%E4%BE%8B%E5%AD%90jwtjson-web-token-based-authorization-code-grant)
      - [(a) 相关角色](#a-%E7%9B%B8%E5%85%B3%E8%A7%92%E8%89%B2)
      - [(b) 授权流程](#b-%E6%8E%88%E6%9D%83%E6%B5%81%E7%A8%8B)
    - [(5) 扩展阅读](#5-%E6%89%A9%E5%B1%95%E9%98%85%E8%AF%BB)
    - [(6) Spring Security与OAuth2](#6-spring-security%E4%B8%8Eoauth2)
  - [9.2 创建Authorization Server](#92-%E5%88%9B%E5%BB%BAauthorization-server)
    - [(1) 功能](#1-%E5%8A%9F%E8%83%BD)
    - [(2) Pom文件](#2-pom%E6%96%87%E4%BB%B6)
    - [(3) 程序配置](#3-%E7%A8%8B%E5%BA%8F%E9%85%8D%E7%BD%AE)
    - [(4) 安全配置](#4-%E5%AE%89%E5%85%A8%E9%85%8D%E7%BD%AE)
    - [(5) Auth Server配置](#5-auth-server%E9%85%8D%E7%BD%AE)
    - [(6) 使用浏览器替代"Client Application"进行演示](#6-%E4%BD%BF%E7%94%A8%E6%B5%8F%E8%A7%88%E5%99%A8%E6%9B%BF%E4%BB%A3client-application%E8%BF%9B%E8%A1%8C%E6%BC%94%E7%A4%BA)
    - [(7) 授权过程演示](#7-%E6%8E%88%E6%9D%83%E8%BF%87%E7%A8%8B%E6%BC%94%E7%A4%BA)
      - [(a) 访问登录页，输入用户名密码登录](#a-%E8%AE%BF%E9%97%AE%E7%99%BB%E5%BD%95%E9%A1%B5%E8%BE%93%E5%85%A5%E7%94%A8%E6%88%B7%E5%90%8D%E5%AF%86%E7%A0%81%E7%99%BB%E5%BD%95)
      - [(b) 跳转到授权服务器同意页面，对其授权](#b-%E8%B7%B3%E8%BD%AC%E5%88%B0%E6%8E%88%E6%9D%83%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%90%8C%E6%84%8F%E9%A1%B5%E9%9D%A2%E5%AF%B9%E5%85%B6%E6%8E%88%E6%9D%83)
      - [(c) 使用授权码交换Access Token](#c-%E4%BD%BF%E7%94%A8%E6%8E%88%E6%9D%83%E7%A0%81%E4%BA%A4%E6%8D%A2access-token)
      - [(d) 使用Access Token访问资源](#d-%E4%BD%BF%E7%94%A8access-token%E8%AE%BF%E9%97%AE%E8%B5%84%E6%BA%90)
      - [(e) 使用https://jwt.io来解码Access Token](#e-%E4%BD%BF%E7%94%A8httpsjwtio%E6%9D%A5%E8%A7%A3%E7%A0%81access-token)
      - [(f) 使用Tefresh Token来更新Access Token](#f-%E4%BD%BF%E7%94%A8tefresh-token%E6%9D%A5%E6%9B%B4%E6%96%B0access-token)
  - [9.3 使用Resource Server保护API](#93-%E4%BD%BF%E7%94%A8resource-server%E4%BF%9D%E6%8A%A4api)
    - [(1) Demo项目位置](#1-demo%E9%A1%B9%E7%9B%AE%E4%BD%8D%E7%BD%AE)
    - [(2) 依赖项](#2-%E4%BE%9D%E8%B5%96%E9%A1%B9)
    - [(3) 开启Resource Server功能并配置需要权限的url路径](#3-%E5%BC%80%E5%90%AFresource-server%E5%8A%9F%E8%83%BD%E5%B9%B6%E9%85%8D%E7%BD%AE%E9%9C%80%E8%A6%81%E6%9D%83%E9%99%90%E7%9A%84url%E8%B7%AF%E5%BE%84)
    - [(4) 配置Authorization公钥获取路径](#4-%E9%85%8D%E7%BD%AEauthorization%E5%85%AC%E9%92%A5%E8%8E%B7%E5%8F%96%E8%B7%AF%E5%BE%84)
    - [(5) 使用curl测试](#5-%E4%BD%BF%E7%94%A8curl%E6%B5%8B%E8%AF%95)
  - [9.4 开发Client Application](#94-%E5%BC%80%E5%8F%91client-application)
    - [(1) 功能](#1-%E5%8A%9F%E8%83%BD-1)
    - [(2) 依赖项](#2-%E4%BE%9D%E8%B5%96%E9%A1%B9-1)
    - [(3) 配置Security Config中的Filter Chain](#3-%E9%85%8D%E7%BD%AEsecurity-config%E4%B8%AD%E7%9A%84filter-chain)
    - [(4) 配置访问Authorization Server所需的信息](#4-%E9%85%8D%E7%BD%AE%E8%AE%BF%E9%97%AEauthorization-server%E6%89%80%E9%9C%80%E7%9A%84%E4%BF%A1%E6%81%AF)
    - [(5) 使用Access Token：向HTTP请求中添加Access Token](#5-%E4%BD%BF%E7%94%A8access-token%E5%90%91http%E8%AF%B7%E6%B1%82%E4%B8%AD%E6%B7%BB%E5%8A%A0access-token)
    - [(6) 获取Access Token](#6-%E8%8E%B7%E5%8F%96access-token)
  - [9.5 总结](#95-%E6%80%BB%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# CH09 Spring Security

内容来源：[https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-9](https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-9)（限时预览、超过时限后要购买才能继续阅读）

Demo项目：[m1/ch09/](m1/ch09/)

> [m1/ch09/auth-server](m1/ch09/auth-server)
> [m1/ch09/tacocloud](m1/ch09/tacocloud)
> [m1/ch09/tacocloud-admin](m1/ch09/tacocloud-admin)

## 9.1 OAuth2介绍

### (1) 需求

需要被保护的Rest端点如下

> [/tacocloud/tacocloud-api/src/main/java/tacos/web/api/IngredientController.java](/m1/ch09/tacocloud/tacocloud-api/src/main/java/tacos/web/api/IngredientController.java)
>
> ~~~java
> package tacos.web.api;
> ...
> 
> @RestController
> @RequestMapping(path="/ingredients", produces="application/json")
> @CrossOrigin(origins="*")
> public class IngredientController {
> 	private IngredientRepository repo;
> 
> 	@Autowired
> 	public IngredientController(IngredientRepository repo) {
> 		this.repo = repo;
> 	}
> 
> 	@GetMapping
> 	public Iterable<Ingredient> allIngredients(@AuthenticationPrincipal User u, HttpServletRequest req) {
> 		if (u != null) {
> 			Collection<GrantedAuthority> authorities = u.getAuthorities();
> 			for (GrantedAuthority grantedAuthority : authorities) {
> 				System.out.println("       --- AUTHORITY:  " + grantedAuthority.getAuthority());
> 			}
> 		} else {
> 			System.out.println("           --- NULL USER?");
> 			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
> 			for (GrantedAuthority grantedAuthority : authorities) {
> 				System.out.println("       --- AUTHORITY:  " + grantedAuthority.getAuthority());
> 				String header = req.getHeader("Authorization");
> 				System.out.println("AUTH HEADER:  " + header);
> 			}
> 		}
> 		return repo.findAll();
> 	}
> 
> 	@PostMapping
> 	@ResponseStatus(HttpStatus.CREATED)
> 	public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
> 		return repo.save(ingredient);
> 	}
> 
> 	@DeleteMapping("/{id}")
> 	@ResponseStatus(HttpStatus.NO_CONTENT)
> 	public void deleteIngredient(@PathVariable("id") String ingredientId) {
> 		repo.deleteById(ingredientId);
> 	}
> }
> ~~~

遇到的问题是：如果没有安全措施，任何人都可以通过curl来访问这些端点，例如

> ~~~bash
> $ curl localhost:8080/api/ingredients \
>   -H"Content-type: application/json" \
>   -d'{"id":"FISH","name":"Stinky Fish", "type":"PROTEIN"}'
> $ curl localhost:8080/api/ingredients/GRBF -X DELETE
> ~~~

### (2) `HTTP Basic authentication`及缺点

#### (a) 声明对端点的保护

方法(1)：使用`@PreAuthorize`注解

> ~~~java
> @PostMapping
> @PreAuthorize("#{hasRole('ADMIN')}")
> public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
> 	return repo.save(ingredient);
> }
> 
> @DeleteMapping("/{id}")
> @PreAuthorize("#{hasRole('ADMIN')}")
> public void deleteIngredient(@PathVariable("id") String ingredientId) {
> 	repo.deleteById(ingredientId);
> }
> ~~~

方法(2)：在secrity configuration中配置

> ~~~java
> @Override
> protected void configure(HttpSecurity http) throws Exception {
> 	http
> 		.authorizeRequests()
> 			.antMatchers(HttpMethod.POST, "/api/ingredients").hasRole("ADMIN")
> 			.antMatchers(HttpMethod.DELETE, "/api/ingredients/**").hasRole("ADMIN")
> 			...
> }
> ~~~

#### (b) 请求REST端点

不论使用的是那种方法，请求这两个API时都需要加上带有`ROLE_ADMIN`权限的秘钥，例如下面curl命令中的`-u`参数

> ~~~bash
> $ curl localhost:8080/api/ingredients \
> 	-H"Content-type: application/json" \
> 	-d'{"id":"FISH","name":"Stinky Fish", "type":"PROTEIN"}' \
> 	-u admin:l3tm31n
> ~~~

#### (c) 缺陷

> 用来认证的Credential，只是以Base64编码的方式，存储在HTTP Header中
>
> 黑客可以拦截并获取该凭据，进行身份盗用

#### (d) 解决

> 不传送Credential，改为传送Access Token，这也是OAuth2的工作原理

### (3) OAuth2授权流程

> OAuth2规范支持多种授权流程，包括如下几种

#### (a) `Authorization Code Grant`：授权代码授予（常规流程）

> <div align="left"><img src="https://raw.githubusercontent.com/kenfang119/pics/main/001_spring_ioc/sia6_oauth2_work_flow.jpg" width="600" /></div>
>
> 步骤如下：
>
> (1,2)：用户（User）本人访问Client Application，请求被重定向到Auth Server
>
> (3,4)：Auth Server征得用户同意（User Consent）并重定向到Client Application
>
> (5,6)：重定向URL中会包含consent code，Client Application使用这个code与Auth Server交换秘钥获得Access Token

#### (b) `Implicit Grant`：隐式授权（为浏览器运行JS Client设计、不推荐）

> 步骤1、2：与`Authorization Code Grant`一样，重定向用户请求到Auth Server
>
> 后续步骤直接隐式授予Access Token，而不需要获得获得User Consent

#### (c) `User Credentials (or Password) Grant`：用户凭据（密码）授予

> 不发生重定向，Client Application直接获取User Credential并用做Access Token
>
> 适用于非基于浏览器的Client Application、现代应用通常不符合

#### (d) `Client Credentials Grant`：客户端凭据授予

> 类似方法`(c)`，差别是使用的不是User Credential、而是Client Application的Credential。仅适用于不以用户为中心的操作、不能代表用户进行操作

### (4) 本章例子：JWT（Json Web Token） based Authorization Code Grant

#### (a) 相关角色

Authorization Server（授权服务器）

> 代表Client Application从User获得许可，并提供Access Token给Client Application
>
> Access Token用于访问Resource Server的API

Resource Server（资源服务器）

> 提供受OAuth2保护的API，验证权限并限制对API的访问

Client Application（客户端应用程序）

> 想要使用API的客户端应用程序

User（用户）

> 使用Client Application的人

#### (b) 授权流程

> Client Application将User请求重定向到Authorization Server
>
> Authorization Server要求用户登录并同意授予所请求的权限
>
> 用户同意后Authorization Server将浏览器重定向回Client Application，并携带一个代码
>
> Client Application使用这个代码交换访问令牌

### (5) 扩展阅读

> OAuth 2规范：[https://oauth.net/2/](https://oauth.net/2/)
>
> OAuth 2 实战：[https://www.manning.com/books/oauth-2-in-action](https://www.manning.com/books/oauth-2-in-action)
>
> 微服务安全实战：[https://www.manning.com/books/microservices-security-in-action](https://www.manning.com/books/microservices-security-in-action)
>
> API 安全实战：[http://www.manning.com/books/api-security-in-action](https://www.manning.com/books/api-security-in-action)
>
> Manning Live Project：https://www.manning.com/liveproject/protecting-user-data-with-spring-security-and-oauth2

### (6) Spring Security与OAuth2

历史项目：Spring Security for OAuth

现状：OAuth2中的

> Client Application以及Resource Server已经被纳入到Spring Security中
>
> Authorization Server在一个实验性质的单独项目中中，名为Spring Authorization Server：[https://github.com/spring-projects-experimental/spring-authorization-server](https://github.com/spring-projects-experimental/spring-authorization-server)

## 9.2 创建Authorization Server

### (1) 功能

> * 代表用户发布Access Token，项目还处于实验阶段，
> * 目前只实现了`Authorization Code Grant`和`Client Credentials Grant`两种模式

### (2) Pom文件

> [/auth-server/pom.xml](m1/ch09/auth-server/pom.xml)
>
> 例子中的Authorization Server是一个Spring Boot Application，主要依赖包括
>
> | ArtifactID                                  | 说明                                |
> | ------------------------------------------- | ----------------------------------- |
> | spring-boot-starter-web                     | Spring MVC                          |
> | spring-boot-starter-security                | Spring Security                     |
> | spring-boot-starter-data-jpa                | 因为用户信息存储在数据库中          |
> | spring-security-oauth2-authorization-server | 实验阶段的类库，还没有做成starter、 |

### (3) 程序配置

> [/auth-server/src/main/resources/application.yml](m1/ch09/auth-server/src/main/resources/application.yml)
>
> ~~~yml
> server:
> 	# 防止与其他服务端口冲突
> 	port: 9000
> 
> management:
> 	endpoints:
> 		web:
> 			exposure:
> 				include: '*'
> ~~~

### (4) 安全配置

> [/auth-server/src/main/java/tacos/authorization/SecurityConfig.java](m1/ch09/auth-server/src/main/java/tacos/authorization/SecurityConfig.java)
>
> ~~~java
> package tacos.authorization;
> 
> ...
> 
> @EnableWebSecurity
> public class SecurityConfig {
> 	@Bean
> 	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
> 		return http.authorizeRequests(
> 				// 要求所有请求都要经过身份验证
> 				authorizeRequests -> authorizeRequests.anyRequest().authenticated()
> 			)
> 			.formLogin() // 采用基于表单的登录方式
> 			.and().build();
> 	}
> 
> 	@Bean
> 	UserDetailsService userDetailsService(TacoUserRepository userRepo) {
> 		// 根据username查找用户信息，具体实现在第三章中有介绍
> 		return username -> userRepo.findByUsername(username);
> 	}
> 
> 	@Bean
> 	public PasswordEncoder passwordEncoder() {
> 		return NoOpPasswordEncoder.getInstance();
> 	}
> }
> ~~~

### (5) Auth Server配置

> 配置类：[/auth-server/src/main/java/tacos/authorization/AuthorizationServerConfig.java](m1/ch09/auth-server/src/main/java/tacos/authorization/AuthorizationServerConfig.java)
>
> ~~~java
> package tacos.authorization;
> import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
> ...
> 
> @Configuration(proxyBeanMethods = false)
> public class AuthorizationServerConfig {
> 	//(1) 定义SecurityFilterChain Bean，配置过滤规则
> 	@Bean
> 	@Order(Ordered.HIGHEST_PRECEDENCE) //同类型Bean中优先级最高
> 	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
> 		// 使用默认的OAuth2配置
> 		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
> 		// 使用默认形式的登录页面
> 		return http
> 			.formLogin(Customizer.withDefaults())
> 			.build();
> 	}
> 
> 	//(2) 定义RegisteredClientRepository Bean，配置RegisteredClient存储和查找规则
> 	//    该接口提供了findById和findByClientId两个方法	
> 	@Bean
> 	public RegisteredClientRepository registeredClientRepository() {
> 		RegisteredClient registeredClient = 
> 			RegisteredClient
> 			// ID：使用随机生成的唯一标识符
> 			.withId(UUID.randomUUID().toString()) 
> 			// 客户端ID
> 			.clientId("taco-admin-client")
> 			// 客户端秘钥
> 			.clientSecret("secret")
> 			// Authorization授予类型：为这个客户端提供的OAuth2 Grant Type， 启用了Authorization Code和Refresh Token
> 			.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
> 			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
> 			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
> 			// 重定向URL：authorization granted之后跳转到该URL，防止某些application使用auth code换取access token
> 			.redirectUri("http://127.0.0.1:9090/login/oauth2/code/taco-admin-client")
> 			// OAuth2作用范围
> 			.scope("writeIngredients")	// 希望作起作用的Scope
> 			.scope("deleteIngredients")	// 
> 			.scope(OidcScopes.OPENID) 	// 后面实现Single Sign On时要用
> 			// 客户端配置：要求授权之前获得用户同意，否则将变为隐式首选（implicity grant）
> 			.clientSettings(clientSettings -> clientSettings.requireUserConsent(true))
> 			.build();
> 		return 
> 			// 为了方便演示，返回基于内存数据库的RegisteredClientRepository
> 			// 实际项目中，应该使用该接口的其他实现类，以使用真实的数据库
> 			new InMemoryRegisteredClientRepository(registeredClient);
> 	}
> 
>     // (2) 配置用于生成JWK（Json Web Key）令牌的Bean，以便能够生成JWT Access Token
> 	@Bean
> 	public JWKSource<SecurityContext> jwkSource() {
>         // 创建RSA2048位秘钥对
> 		RSAKey rsaKey = generateRsa();
> 		JWKSet jwkSet = new JWKSet(rsaKey);
> 		// 后续将会对使用私钥对令牌进行签名
> 		// 而Resource Server则可以通过从Auth Server获得的公钥来验证收到的Access Token是否有效
> 		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
> 	}
> 
> 	private static RSAKey generateRsa() {
> 		// 创建RSA2048位秘钥对的代码
> 		...
> 	}
> 
> 	@Bean
> 	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
> 		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
> 	}	
> }
> ~~~

### (6) 使用浏览器替代"Client Application"进行演示

> 用户名、密码：存储在H2内存数据库中，由配置的Application Runner在程序启动时存入
>
> ~~~java
> @SpringBootApplication
> public class AuthServerApplication {
> 	public static void main(String[] args) {
> 		SpringApplication.run(AuthServerApplication.class, args);
> 	}
> 
> 	@Bean
> 	public ApplicationRunner dataLoader(TacoUserRepository repo, PasswordEncoder encoder) {
> 		return args -> {
>             repo.save(new TacoUser("habuma", encoder.encode("password"), "ROLE_ADMIN"));
> 			repo.save(new TacoUser("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
> 		};
> 	}
> }
> ~~~

### (7) 授权过程演示

构建项目并启动程序，用浏览器替代Client Application访问`http://localhost:9000/login`

#### (a) 访问登录页，输入用户名密码登录

> <div align="left"><img src="https://raw.githubusercontent.com/kenfang119/pics/main/001_spring_ioc/sia6_oauth2_login_page.jpg" width="600" /></div>

#### (b) 跳转到授权服务器同意页面，对其授权

> <div align="left"><img src="https://raw.githubusercontent.com/kenfang119/pics/main/001_spring_ioc/sia6_oauth2_consent_page.jpg" width="800" /></div>

#### (c) 使用授权码交换Access Token

> Consent之后会跳转到Client Application页面的URL上（此时还没有启动Client Application所以页面会报错），重定向URL中的code参数就是用于与Authorization Server交换Access Token的`授权码`
>
> 因为还没有编写Client Application，使用curl命令来模拟交换Access Token的过程
>
> ~~~bash
> $ code = "abcdefghijk" # 替换成重定向URL的code参数传递的授权码
> $ curl localhost:9000/oauth2/token \
>  -H"Content-type: application/x-www-form-urlencoded" \ # Request Payload格式
>  -d"grant_type=authorization_code" \ # 授权类型
>  -d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client"\ #重定向URL 
>  -d"code=$code" \ # 授权码
>  -u taco-admin-client:secret 
> # 下面是Authorization Server的Response
> {
>   "access_token":"eyJraWQ...",
>   "refresh_token":"HOzHA5s...",
>   "scope":"deleteIngredients writeIngredients", # 资源访问范围
>   "token_type":"Bearer", # 
>   "expires_in":"299" # Access Token过期时间
> }
> ~~~

#### (d) 使用Access Token访问资源

> 在名为Authorization的HTTP Header中传入”${token_type} ${access_token}"就可以访问资源
>
> ~~~bash
> $ curl localhost:8080/api/ingredients \
>   -H"Content-type: application/json" \
>   -H"Authorization: Bearer eyJraWQ..." \
>   -d'{"id":"FISH","name":"Stinky Fish", "type":"PROTEIN"}'
> ~~~

#### (e) 使用[https://jwt.io](https://jwt.io)来解码Access Token

> Access Token被解码为三部分：Header、Payload、Verify Signature
>
> <div align="left"><img src="https://raw.githubusercontent.com/kenfang119/pics/main/001_spring_ioc/sia6_oauth2_jwt_io.jpg" width="600" /></div>
>
> 从Payload中可以看出这个Access Token是名为“tacochef”的用户发出、具有"writeIngredients"和"deleteIngredients"权限。Access Token过期后，仍然可以在jwt.io上进行解析，只是不能再用于访问资源

#### (f) 使用Tefresh Token来更新Access Token

> 使用Refresh Token可以直接刷新已经过期的Access Token，而不需要重新认证
>
> ~~~bash
> $ curl localhost:9000/oauth2/token \
>     -H"Content-type: application/x-www-form-urlencoded" \
>     -d"grant_type=refresh_token&refresh_token=HOzHA5s..." \
>     -u taco-admin-client:secret
> ~~~

## 9.3 使用Resource Server保护API

### (1) Demo项目位置

[`/tacocloud/`](m1/ch09/tacocloud/)下面包含了很多module，每一个都是项目中的服务，例如

> tacocloud-api
> tacocloud-data
> tacocloud-domain
> tacocloud-restclient
> tacocloud-ui
> tacocloud-ui-ReactJS
> tacocloud-web

在其中添加一个新的module：[/tacocloud/tacocloud-security/](m1/ch09/tacocloud/tacocloud-security/)作为Resource Server

> 它实际上是一个位于API前面的过滤器，用来确保用户访问（需要授权的）资源时，Request中能够包含对应的Access Token

### (2) 依赖项

> pom.xml: [/tacocloud/tacocloud-security/pom.xml](m1/ch09/tacocloud/tacocloud-security/pom.xml)
>
> | Artifact ID                                |      |
> | ------------------------------------------ | ---- |
> | spring-boot-starter-security               |      |
> | spring-boot-starter-oauth2-resource-server |      |

### (3) 开启Resource Server功能并配置需要权限的url路径

代码：[/tacocloud/tacocloud-security/src/main/java/tacos/security/SecurityConfig.java](m1/ch09/tacocloud/tacocloud-security/src/main/java/tacos/security/SecurityConfig.java)

> ~~~java
> @Override
> protected void configure(HttpSecurity http) throws Exception {
> 	http.authorizeRequests()
> 		...
> 		// 配置需要权限的URL路径
>         .antMatchers(HttpMethod.POST, "/ingredients").hasAuthority("SCOPE_writeIngredients")
>         .antMatchers(HttpMethod.DELETE, "/ingredients").hasAuthority("SCOPE_deleteIngredients")
> 		...
> 		// 开启Resource Server功能并使用JWT作为Access Token
> 		.and().oauth2ResourceServer(oauth2 -> oauth2.jwt())
> }
> ~~~

其中的`hasAuthority(SCOPE_xxx)`与之前配置Authorization Server时填入的scope向对象

> ~~~java
> .scope("writeIngredients")	// 希望作起作用的Scope
> .scope("deleteIngredients")	// 
> ~~~

### (4) 配置Authorization公钥获取路径

Resource将使用该路径获取Authorization公钥，并验证Request中的Access Token是否合法

代码路径：[/tacocloud/tacos/src/main/resources/application.yml](/tacocloud/tacos/src/main/resources/application.yml)

> ~~~yml
> spring:
>   security:
>     oauth2:
>       resourceserver:
>         jwt:
>           jwk-set-uri: http://localhost:9000/oauth2/jwks
> ~~~

### (5) 使用curl测试

> 下面的请求没有Access Token，访问/ingredients路径时会返回HTTP 404
>
> ~~~bash
> $ curl localhost:8080/ingredients \
> 	-H"Content-type: application/json" \
> 	-d'{"id":"CRKT", "name":"Legless Crickets", "type":"PROTEIN"}'
> ~~~
>
> 向请求中加入Access Token（将`$token`替换成真正的Access Token）后可以成功访问
>
> 但是当Access Token过期后再次访问，将得到HTTP 401 Response
>
> ~~~bash
> $ curl localhost:8080/ingredients \
>     -H"Content-type: application/json" \
>     -d'{"id":"SHMP", "name":"Coconut Shrimp", "type":"PROTEIN"}' \
>     -H"Authorization: Bearer $token"
> ~~~
>
> 使用以下的命令可以获取新的Token（用实际的Refresh Token替换`$refreshToken`）
>
> ~~~bash
> curl localhost:9000/oauth2/token \
>     -H"Content-type: application/x-www-form-urlencoded" \
>     -d"grant_type=refresh_token&refresh_token=$refreshToken" \
>     -u taco-admin-client:secret
> ~~~

## 9.4 开发Client Application

### (1) 功能

代表用户向Resource Server发出请求

> 当Client Application确定用户尚未通过身份验证时，将用户的浏览器重定向到Authorization Server，以获得用户的同意。用户同意后，Authorization Server将HTTP访问重定向到Client Application。 Client Application 使用受到的授权码（consent code）向Authorization Server交换Access Token

Demo项目：[/tacocloud-admin/](m1/ch09/tacocloud-admin/)

### (2) 依赖项

pom.xml：[tacocloud-admin/pom.xml](m1/ch09/tacocloud-admin/pom.xml)

> ~~~xml
> <dependency>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-oauth2-client</artifactId>
> </dependency>
> ~~~

### (3) 配置Security Config中的Filter Chain

代码：[/tacocloud-admin/src/main/java/tacos/config/SecurityConfig.java](m1/ch09/tacocloud-admin/src/main/java/tacos/config/SecurityConfig.java)

> ~~~java
> @Configuration
> public class SecurityConfig {
> 	@Bean
> 	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
> 		http.authorizeRequests(
> 			// 所有请求都要身份验证
> 			authorizeRequests -> authorizeRequests.anyRequest().authenticated()
> 		).oauth2Login(
> 			// 启用了OAuth2客户端
> 			// "/oauth2/authorization/taco-admin-client"
> 			// * 是使用授权码向Authorization Server换取Access Token的路径
> 			// * 也是授权之后Authorization Server重定向的路径
> 			oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/taco-admin-client")
> 		).oauth2Client(withDefaults());
> 		return http.build();
> 	}
> ~~~

### (4) 配置访问Authorization Server所需的信息

代码：[/tacocloud-admin/src/main/resources/application.yml](m1/ch09/tacocloud-admin/src/main/resources/application.yml)

> ~~~yml
> spring:
> 	security:
> 		oauth2:
> 			client:
> 				registration:
> 					# 注册一个名为taco-admin-client的OAuth2 Client Application
> 					taco-admin-client:
> 						# Authorization Server的配置项（指向下面的配置）
> 						provider: tacocloud
> 						# 客户端凭据包括id和secret
> 						client-id: taco-admin-client
> 						client-secret: secret
> 						# 授权类型
> 						authorization-grant-type: authorization_code
> 						# 使用授权码交换Access Token的URL，也是给Authorization Server进行授权后重定向的URL，与上面的配置保持一致
> 						# 其中{registrationId}将通过引用获得上面的"taco-admin-client"值
> 						redirect-uri: "http://127.0.0.1:9090/login/oauth2/code/{registrationId}"
> 						# 请求的范围
> 						scope: writeIngredients,deleteIngredients,openid
> 				provider:
> 					# 名为tococloud的Authorization Server配置项
> 					tacocloud:
> 						# Authorization Server的url和地址
> 						issuer-uri: http://authserver:9000						
> ~~~

备注：

(1) 为了能够在本地测试，在`/etc/hosts`文件中增加如下DNS配置（Mac为例）

> ~~~txt
> 127.0.0.1 authserver
> ~~~

(2) 上面关于provider的配置中，使用了不少默认配置，如果需要修改某些配置项，可以参考如下代码

> ~~~yml
> 				provider:
> 					# 名为tococloud的Authorization Server配置项
> 					tacocloud:
> 						# Authorization Server的地址和端口
> 						issuer-uri: http://authserver:9000	
> 						authorization-uri: http://authserver:9000/oauth2/authorize
> 						token-uri: http://authserver:9000/oauth2/token
> 						jwk-set-uri: http://authserver:9000/oauth2/jwks
> 						# 用来获取用户信息的URI，Authorization Server会自动创建该端点
> 						user-info-uri: http://authserver:9000/userinfo
> 						# 用于标识用户的属性
> 						user-name-attribute: sub						
> ~~~

### (5) 使用Access Token：向HTTP请求中添加Access Token

代码：[/tacocloud/tacocloud-api/src/main/java/tacos/web/api/IngredientController.java](m1/ch09/tacocloud/tacocloud-api/src/main/java/tacos/web/api/IngredientController.java)

> ~~~java
> package tacos;
> ...
> 
> public class RestIngredientService implements IngredientService {
> 	private RestTemplate restTemplate;
> 
> 	public RestIngredientService() {
> 		// Rest Template
> 		this.restTemplate = new RestTemplate();
> 		// 将用户获取Access Token的拦截器附加到Rest Template上
> 		if (accessToken != null) {
> 			this.restTemplate
> 				.getInterceptors()
> 				.add(getBearerTokenInterceptor(accessToken));
> 		}
> 	}
> 
> 	// 向HTTP Header添加Access Token的拦截器
> 	private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
> 		ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
> 			@Override
> 			public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
> 				request.getHeaders().add("Authorization", "Bearer " + accessToken);
> 				return execution.execute(request, bytes);
> 			}
> 		};
> 		return interceptor;
> 	}    
>     
> 	@Override
> 	public Iterable<Ingredient> findAll() {
> 		// 转发请求到8080端口上，调用后端真正的Rest API获取数据
> 		// 这个Rest端点不在之前配置的OAuth2 scope中，因为不论有没有Access Token都可以返回结果
> 		return Arrays.asList(restTemplate.getForObject("http://localhost:8080/ingredients", Ingredient[].class));
> 	}
> 
> 	@Override
> 	public Ingredient addIngredient(Ingredient ingredient) {
> 		// 转发请求到8080端口上，调用后端真正的Rest API执行操作
> 		// 这个Rest端点在OAuth2 scope配置中，没有Access Token会返回HTTP 401
> 		return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
> 	}
> }
> ~~~
>
> 备注1：这个类在[ManageIngredientsController](m1/ch09/tacocloud-admin/src/main/java/tacos/ManageIngredientsController.java)中被配置使用
>
> ~~~java
> @GetMapping
> public String ingredientsAdmin(Model model) {
> 	model.addAttribute("ingredients", ingredientService.findAll());
> 	return "ingredientsAdmin";
> }
>   
> @PostMapping
> public String addIngredient(Ingredient ingredient) {
> 	ingredientService.addIngredient(ingredient);
> 	return "redirect:/admin/ingredients";
> }
> ~~~
>
> 备注2：关于构造函数中传入的accessToken对象如何获取，见下一小节

### (6) 获取Access Token

代码：[/tacocloud-admin/src/main/java/tacos/config/SecurityConfig.java](m1/ch09/tacocloud-admin/src/main/java/tacos/config/SecurityConfig.java)

> ~~~java
> @Bean
> @RequestScope // Bean的生命周期是Request，即每个Request会创建一个新的Bean
> public IngredientService ingredientService(OAuth2AuthorizedClientService clientService) {
> 	// 从SecurityContext中拉取Authentication信息
> 	// 对于每一个Request，都会创建一个新的SecurityContext Bean
> 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
>     String accessToken = null;
> 	// 检查是否是OAuth2 Access Token
>     if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
> 		OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
> 		// 检查是否是用于"taco-admin-client"的Access Token（与先前配置保持一致）
> 		String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
> 		if (clientRegistrationId.equals("taco-admin-client")) {
> 			// 从Authorization Client提取Access Token 
> 			OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
> 			accessToken = client.getAccessToken().getTokenValue();
> 		}
> 	}
> 	// 将Access Token传给上面的RestIngredientService
> 	return new RestIngredientService(accessToken);
> }
> ~~~

## 9.5 总结

> 略