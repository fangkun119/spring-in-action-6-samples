<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH05 Security](#ch05-security)
  - [5.1 引入Spring Security](#51-%E5%BC%95%E5%85%A5spring-security)
    - [(1) 添加spring-boot-starter-security所带来的变化](#1-%E6%B7%BB%E5%8A%A0spring-boot-starter-security%E6%89%80%E5%B8%A6%E6%9D%A5%E7%9A%84%E5%8F%98%E5%8C%96)
  - [5.2 配置spring Security](#52-%E9%85%8D%E7%BD%AEspring-security)
    - [(1) 配置：`PasswordEncoder`，`UserDetailsService`部分](#1-%E9%85%8D%E7%BD%AEpasswordencoderuserdetailsservice%E9%83%A8%E5%88%86)
    - [(2) 相关的接口](#2-%E7%9B%B8%E5%85%B3%E7%9A%84%E6%8E%A5%E5%8F%A3)
    - [5.2.1 基于内存的User Store](#521-%E5%9F%BA%E4%BA%8E%E5%86%85%E5%AD%98%E7%9A%84user-store)
    - [5.2.2 实现认证和注册方法](#522-%E5%AE%9E%E7%8E%B0%E8%AE%A4%E8%AF%81%E5%92%8C%E6%B3%A8%E5%86%8C%E6%96%B9%E6%B3%95)
      - [(1) 定义用户信息的Domain Object](#1-%E5%AE%9A%E4%B9%89%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%E7%9A%84domain-object)
      - [(2) 定义用户Domain Object的持久化：`UserRepository`](#2-%E5%AE%9A%E4%B9%89%E7%94%A8%E6%88%B7domain-object%E7%9A%84%E6%8C%81%E4%B9%85%E5%8C%96userrepository)
      - [(3) 用户注册](#3-%E7%94%A8%E6%88%B7%E6%B3%A8%E5%86%8C)
  - [5.3 指定哪些HTTP请求需要认证](#53-%E6%8C%87%E5%AE%9A%E5%93%AA%E4%BA%9Bhttp%E8%AF%B7%E6%B1%82%E9%9C%80%E8%A6%81%E8%AE%A4%E8%AF%81)
    - [(1) 配置的内容](#1-%E9%85%8D%E7%BD%AE%E7%9A%84%E5%86%85%E5%AE%B9)
    - [5.3.1 HTTP请求检查和过滤](#531-http%E8%AF%B7%E6%B1%82%E6%A3%80%E6%9F%A5%E5%92%8C%E8%BF%87%E6%BB%A4)
      - [(1) `SecurityFilterChain`](#1-securityfilterchain)
      - [(2) 路径处理规则：`hasRole(...)`、`permitAll()`、`hasAnyRole(...)`](#2-%E8%B7%AF%E5%BE%84%E5%A4%84%E7%90%86%E8%A7%84%E5%88%99hasrolepermitallhasanyrole)
      - [(3) 使用SpEL表达式灵活地自定义路径处理规则](#3-%E4%BD%BF%E7%94%A8spel%E8%A1%A8%E8%BE%BE%E5%BC%8F%E7%81%B5%E6%B4%BB%E5%9C%B0%E8%87%AA%E5%AE%9A%E4%B9%89%E8%B7%AF%E5%BE%84%E5%A4%84%E7%90%86%E8%A7%84%E5%88%99)
    - [5.2.3 登录页面](#523-%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2)
      - [(1) 登录页权限配置](#1-%E7%99%BB%E5%BD%95%E9%A1%B5%E6%9D%83%E9%99%90%E9%85%8D%E7%BD%AE)
      - [(2) ViewController](#2-viewcontroller)
      - [(3) View](#3-view)
    - [5.3.3 使用第三方认证](#533-%E4%BD%BF%E7%94%A8%E7%AC%AC%E4%B8%89%E6%96%B9%E8%AE%A4%E8%AF%81)
      - [(1) 引入spring-boot-starter-oauth2-client](#1-%E5%BC%95%E5%85%A5spring-boot-starter-oauth2-client)
      - [(2) 配置OAuth2或OpenID Connect Servers](#2-%E9%85%8D%E7%BD%AEoauth2%E6%88%96openid-connect-servers)
    - [5.3.4 退出](#534-%E9%80%80%E5%87%BA)
      - [(1) 退出页配置](#1-%E9%80%80%E5%87%BA%E9%A1%B5%E9%85%8D%E7%BD%AE)
    - [5.3.5 防止跨站请求伪造 （Cross-Site Request Forgery，CSRF）攻击](#535-%E9%98%B2%E6%AD%A2%E8%B7%A8%E7%AB%99%E8%AF%B7%E6%B1%82%E4%BC%AA%E9%80%A0-cross-site-request-forgerycsrf%E6%94%BB%E5%87%BB)
      - [(1) CSRF攻击](#1-csrf%E6%94%BB%E5%87%BB)
      - [(2) 防止CSRF攻击](#2-%E9%98%B2%E6%AD%A2csrf%E6%94%BB%E5%87%BB)
      - [(3) 关闭Spring Security的CSRF防护（不建议）](#3-%E5%85%B3%E9%97%ADspring-security%E7%9A%84csrf%E9%98%B2%E6%8A%A4%E4%B8%8D%E5%BB%BA%E8%AE%AE)
  - [5.4 方法粒度的Secruity配置](#54-%E6%96%B9%E6%B3%95%E7%B2%92%E5%BA%A6%E7%9A%84secruity%E9%85%8D%E7%BD%AE)
    - [(1) 为部分路径设置不同的权限（场景 1）](#1-%E4%B8%BA%E9%83%A8%E5%88%86%E8%B7%AF%E5%BE%84%E8%AE%BE%E7%BD%AE%E4%B8%8D%E5%90%8C%E7%9A%84%E6%9D%83%E9%99%90%E5%9C%BA%E6%99%AF-1)
    - [(2) 为某些方法设置访问权限（场景2：@PreAuthorize）](#2-%E4%B8%BA%E6%9F%90%E4%BA%9B%E6%96%B9%E6%B3%95%E8%AE%BE%E7%BD%AE%E8%AE%BF%E9%97%AE%E6%9D%83%E9%99%90%E5%9C%BA%E6%99%AF2preauthorize)
    - [(3) 根据方法的执行结果判断当前用户是否有权限（场景3：@PostAuthorize）](#3-%E6%A0%B9%E6%8D%AE%E6%96%B9%E6%B3%95%E7%9A%84%E6%89%A7%E8%A1%8C%E7%BB%93%E6%9E%9C%E5%88%A4%E6%96%AD%E5%BD%93%E5%89%8D%E7%94%A8%E6%88%B7%E6%98%AF%E5%90%A6%E6%9C%89%E6%9D%83%E9%99%90%E5%9C%BA%E6%99%AF3postauthorize)
  - [5.5 获取用户信息以优化用户体验](#55-%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF%E4%BB%A5%E4%BC%98%E5%8C%96%E7%94%A8%E6%88%B7%E4%BD%93%E9%AA%8C)
    - [(1) 在TacoOrder类中添加User属性](#1-%E5%9C%A8tacoorder%E7%B1%BB%E4%B8%AD%E6%B7%BB%E5%8A%A0user%E5%B1%9E%E6%80%A7)
      - [(2) 获取当前用户并设置给TacoOrder对象](#2-%E8%8E%B7%E5%8F%96%E5%BD%93%E5%89%8D%E7%94%A8%E6%88%B7%E5%B9%B6%E8%AE%BE%E7%BD%AE%E7%BB%99tacoorder%E5%AF%B9%E8%B1%A1)
  - [5.6 小结](#56-%E5%B0%8F%E7%BB%93)
  - [附录](#%E9%99%84%E5%BD%95)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH05 Security

Demo项目

> * [../ch05/taco-cloud/](../ch05/taco-cloud/)：与上一版（Spring In Action第5版）相配套的代码，其[SecurityConfig.java](../ch05/taco-cloud/src/main/java/tacos/security/SecurityConfig.java)为
>
>     ```java
>     @Configuration
>     public class SecurityConfig extends WebSecurityConfigurerAdapter {
>         @Autowired
>     	private UserDetailsService userDetailsService;
>     	@Bean
>     	public PasswordEncoder encoder() {...}    
>     	
>     	//继承WebSecurityConfigurerAdapter并覆盖configure方法
>         @Override
>     	protected void configure(HttpSecurity http) throws Exception {...}
>     	@Override
>     	protected void configure(AuthenticationManagerBuilder auth) throws Exception {... }
>     }
>     ```
>
> * [../ch05/taco-cloud-sfc/](../ch05/taco-cloud-sfc/)：与当前（Spring In Action第6版）相配套的代码，其[SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)为
>
>     ```java
>     @Configuration
>     public class SecurityConfig {
>     	@Bean
>     	public PasswordEncoder passwordEncoder() {...}
>     	@Bean
>     	public UserDetailsService userDetailsService(UserRepository userRepo) { ... }
>     
>         // 创建SecurityFilterChain Bean
>         @Bean
>     	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { ... }
>     }
>     ```
>     
>     具体说明见接下来的小节

## 5.1 引入Spring Security

### (1) 添加spring-boot-starter-security所带来的变化

> 在[pom.xml](../ch05/taco-cloud-sfc/pom.xml)中添加`spring-boot-starter-security`依赖
>
> ~~~xml
> <dependency>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-security</artifactId>
> </dependency>
> ~~~
>
> 之后Spring Boot会进行自动配置，应用程序会包括
>
> * 所有的HTTP请求路径都开启了认证
> * 没有特定的角色和权限、没有登录页面、认证通过HTTP basic认证对话框实现
> * 系统只有一个用户，用户名为user
>
> 接下来还需要修改程序、使其具有完整的权限、注册、登录认证功能

## 5.2 配置spring Security

### (1) 配置`PasswordEncoder`和`UserDetailsService`

用来对密码明文进行编码、以及从后端用户存储获取用户信息

> [/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)
>
> ~~~java
> @Configuration
> public class SecurityConfig {
> 	// 会在User注册以及登录认证时都被使用，用于对密码编码，防止在数据库中存储密码明文
> 	// 具体操作通过PasswordEncoder接口的matches方法来进行
> 	@Bean
> 	public PasswordEncoder passwordEncoder() {
> 		return new BCryptPasswordEncoder();
> 		// 可选的Password编码器包括
> 		// * BCryptPasswordEncoder：bccrypt Strong Hashing Encryption
> 		// * NoOpPasswordEncoder：无编码，会在数据库中存储密码明文
> 		// * Pbkdf2PasswordEncoder：PBKDF2 Encryption
> 		// * SCryptPasswordEncoder：Scrypt Hashing Encryption
> 		// * StandardPasswordEncoder：SHA-256 Hashing Encryption
> 	}
> 
> 	// 用户认证时需要获取用户信息，因此定义一个Service Bean来执行该操作
> 	@Bean
> 	public UserDetailsService userDetailsService(UserRepository userRepo) {
> 		// 这个Lambda表达式实现了函数式接口UserDetailsService
> 		return username -> {
> 			// User是UserDetails接口的实现类
> 			// userRepo：可以使用JDBC、LDAP等实现
> 			User user = userRepo.findByUsername(username);
> 			if (user != null) {
> 				return user;
> 			}
> 			// UserDetailsService.loadUserByUsername要求永远不返回null
> 			// 因此当user为null时，抛出UsernameNotFoundException
> 			throw new UsernameNotFoundException("User '" + username + "' not found");
> 		};
> 	}
> 
> 	@Bean
> 	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
> 		// 指定哪些http请求需要安全认证
> 		...
> 	}
> }
> ~~~
>
> 相关的接口如下
>
> `UserDetailsService`接口
>
> ~~~java
> package org.springframework.security.core.userdetails;
> public interface UserDetailsService {
> 	// Params: username – the username identifying the user whose data is required.
> 	// Returns: a fully populated user record (never null)
> 	// Throws: UsernameNotFoundException – if the user could not be found or the user has no GrantedAuthorit
> 	UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
> }
> ~~~
>
> `UserDetails`接口
>
> ~~~java
> public interface UserDetails extends Serializable {
> 	// 用户权限，GrantedAuthority接口只有一个String getAuthority()方法
> 	Collection<? extends GrantedAuthority> getAuthorities();
> 	String getPassword();   			// 密码
> 	String getUsername();   			// 用户名
> 	boolean isAccountNonExpired();  	// 用户没有过期
> 	boolean isAccountNonLocked();   	// 用户没有处于锁定状态
> 	boolean isCredentialsNonExpired();  // 用户的密码没有过期
> 	boolean isEnabled();    			// 用户是否被enable，未被enable的用户不能参与认证
> }
> ~~~

### 5.2.1 基于内存的User Store

> 略，作者是在说基于内存的User Store只能读不能写，不能满足”注册“等功能，因此没有在Demo中使用

### 5.2.2 实现认证和注册方法

> 使用Spring Data Repository，以数据库作为用户信息存储

#### (1) Domain Object：`UserDetails`

> Domain Object：上面`UserDetailsService Bean`的方法`getAutorities()`返回的`UserDetails`对象
>
> ~~~java
> @Entity //JPA注解
> @Data   //lambok注解
> @NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
> @RequiredArgsConstructor
> public class User implements UserDetails {
> 	private static final long serialVersionUID = 1L;
> 
> 	@Id
> 	@GeneratedValue(strategy=GenerationType.AUTO)  //使用database自增ID
> 	private Long id;
> 
> 	private final String username;
> 	private final String password;
> 	private final String fullname;
> 	private final String street;
> 	private final String city;
> 	private final String state;
> 	private final String zip;
> 	private final String phoneNumber;
> 
> 	// 返回授予该用户的权限
> 	@Override
> 	public Collection<? extends GrantedAuthority> getAuthorities() {
> 		// org.springframework.security.core.authority.SimpleGrantedAuthority
> 		// * @Override 
> 		// * public String getAuthority() {return this.role;}
> 		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
> 	}
> 
> 	@Override
> 	public boolean isAccountNonExpired() { return true;}
> 	@Override
> 	public boolean isAccountNonLocked() {return true;}
> 	@Override
> 	public boolean isCredentialsNonExpired() {return true;}
> 	@Override
> 	public boolean isEnabled() {return true;}
> }
> ~~~

#### (2) Domain Object的持久化：`UserRepository`

持久化上面的UserDetails对象

> 代码：[/src/main/java/.../data/UserRepository.java](../ch05/taco-cloud-sfc/src/main/java/tacos/data/UserRepository.java)
>
> ~~~java
>// 使用Spring Data JPA，定义repository接口后，框架会自动实现该接口
> public interface UserRepository extends CrudRepository<User, Long> {
> 	// 只提供一个自定义方法 ，框架使用方法名推断来提供实现
>    	// 其他的方法，例如save(User)，来自父接口CrudRepository<User, Long>    
>    	User findByUsername(String username);
>    }
> ~~~
> 
>关于Service层，没有专门编写Service类，而是如`5.2.(1)`小所述，节采用`使用lambda表达式实现函数式接口`的方式，配置在 [/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)中

#### (3) 用户注册

> View：[/src/main/resources/templates/registration.html](../ch05/taco-cloud-sfc/src/main/resources/templates/registration.html)
>
> ```html
> ...
> <form id="registerForm" method="POST" th:action="@{/register}">
>         <label for="username">Username: </label><input name="username" type="text"/><br/>
>         ...
>         <input type="submit" value="Register"/>
> </form>
> ...
> ```
>
> Controller：[/src/main/java/.../security/RegistrationController.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/RegistrationController.java)
>
> ~~~java
> @Controller
> @RequestMapping("/register")
> public class RegistrationController {
>    	...
>    	@GetMapping
>    	public String registerForm() {return "registration";}
>   
>    	@PostMapping
>    	public String processRegistration(RegistrationForm form) {
>    		// 密码被编码后存储
>    		userRepo.save(form.toUser(passwordEncoder));
>    		return "redirect:/login";
>    	}
> }
> ~~~
>
> html表单的数据会被反序列化为[RegistrationForm](../ch05/taco-cloud-sfc/src/main/java/tacos/security/RegistrationForm.java)对象注入到`RegistrationController.processRegistration`的方法参数中
>
> ```java
> @Data
> public class RegistrationForm {
>    	private String username;
>    	private String password;
>    	...
>    	// 密码转码：从明文密码转成加密后的字符串、以避免在数据库中存储密码明文
>    	public User toUser(PasswordEncoder passwordEncoder) {
>    		return new User(
>    			username, passwordEncoder.encode(password), 
>    			fullname, street, city, state, zip, phone);
>    	}
> }
> ```

## 5.3 指定哪些HTTP请求需要认证

### (1) 配置的内容

> 需要配置如下内容
>
> * 要求进行用户身份认证的HTTP Request
> * 登录页
> * 支持用户退出
> * 语法跨站请求伪造

### 5.3.1 HTTP请求检查和过滤

#### (1) `SecurityFilterChain`

> 同样配置在先前的SecurityConfig类中：[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)
>
> ```java
> @Configuration
> public class SecurityConfig {
>   @Bean
>   public PasswordEncoder passwordEncoder() {
>    	...
>   }
>   
>   @Bean
>   public UserDetailsService userDetailsService(UserRepository userRepo) {
>    	...
>   }
>   
>   @Bean
>   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>        return http
>            // 请求处理规则
>            .authorizeRequests()
>            // 规则1：对/design和/order的请求，需要用户具有"ROLE_USER"权限
>            .mvcMatchers("/design", "/orders").hasRole("USER")
>            // 规则2：如果前面的规则不能匹配，会使用接下来的规则来进行判断
>            .anyRequest().permitAll()
>            // 登录页、登出页、豁免页配置
>            .and().formLogin().loginPage("/login")
>            .and().logout().logoutSuccessUrl("/")
>            .and().csrf().ignoringAntMatchers("/h2-console/**") // Make H2-Console non-secured; for debug purposes
>            .and().headers().frameOptions().sameOrigin() // Allow pages to be loaded in frames from the same origin; needed for H2-Console
>            .and().build();
>       }
> }
> ```

#### (2) 路径处理规则：`hasRole(...)`、`permitAll()`、`hasAnyRole(...)`

> 除了上面例子中的`hasRole("USER")`和`permitAll()`，还有很多方法可以用来定义该如何处理某个路径上的HTTP请求，具体如下
>
> | Method                     | What it does                                                 |
> | -------------------------- | ------------------------------------------------------------ |
> | `access(String)`           | Allows access if the given SpEL expression evaluates to `true` |
> | `anonymous()`              | Allows access to anonymous users                             |
> | `authenticated()`          | Allows access to authenticated users                         |
> | `denyAll()`                | Denies access unconditionally                                |
> | `fullyAuthenticated()`     | Allows access if the user is fully authenticated (not remembered) |
> | `hasAnyAuthority(String…)` | Allows access if the user has any of the given authorities   |
> | `hasAnyRole(String…)`      | Allows access if the user has any of the given roles         |
> | `hasAuthority(String)`     | Allows access if the user has the given authority            |
> | `hasIpAddress(String)`     | Allows access if the request comes from the given IP address |
> | `hasRole(String)`          | Allows access if the user has the given role                 |
> | `not()`                    | Negates the effect of any of the other access methods        |
> | `permitAll()`              | Allows access unconditionally                                |
> | `rememberMe()`             | Allows access for users who are authenticated via remember-me |

#### (3) 使用SpEL表达式灵活地自定义路径处理规则

> 上面大部分方法的功能比较固定，除了`access(String)`，可以向它传入`SpEL(Spring Expression Language)`表达式、灵活地配置处理规则，例如
>
> ~~~java
> @Bean
> public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>       return http
>           .authorizeRequests()
>           .antMatchers("/design", "/orders").access("hasRole('USER')")
>           .antMatchers("/", "/**").access("permitAll()")
>           .and().build();
> }
> ~~~
>
> 以及
>
> ~~~java
> @Bean
> public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>   return http
>        .authorizeRequests()
>            .antMatchers("/design", "/orders")
>                .access("hasRole('USER') && " +
>                    "T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) == T(java.util.Calendar).TUESDAY")
>            .antMatchers("/", "/**").access("permitAll")
>        .and()
>        .build();
> }
> ~~~
>
> 除了类似`"T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) == T(java.util.Calendar).TUESDAY"`的代码以外，以下`Spring Security extensions`也可以放在SpEL表达式中 
>
> | Security expression                                          | What it evaluates to                                         |
> | ------------------------------------------------------------ | ------------------------------------------------------------ |
> | `authentication`                                             | The user’s authentication object                             |
> | `denyAll`                                                    | Always evaluates to `false`                                  |
> | `hasAnyAuthority(String… authorities)`                       | `true` if the user has been granted any of the given authorities |
> | `hasAnyRole(String… roles)`                                  | `true` if the user has any of the given roles                |
> | `hasAuthority(String authority)`                             | `true` if the user has been granted the specified authority  |
> | `hasPermission(Object target, Object permission)`            | `true` if the user has access to the provided target for the given permission |
> | `hasPermission(Object target, String targetType, Object permission)` | `true` if the user has access to the provided target for the given permission |
> | `hasRole(String role)`                                       | `true` if the user has the given role                        |
> | `hasIpAddress(String ipAddress)`                             | `true` if the request comes from the given IP address        |
> | `isAnonymous()`                                              | `true` if the user is anonymous                              |
> | `isAuthenticated()`                                          | `true` if the user is authenticated                          |
> | `isFullyAuthenticated()`                                     | `true` if the user is fully authenticated (not authenticated with remember-me) |
> | `isRememberMe()`                                             | `true` if the user was authenticated via remember-me         |
> | `permitAll`                                                  | Always evaluates to `true`                                   |
> | `principal`                                                  | The user’s principal object                                  |

### 5.2.3 登录页面

> 编写一个功能完整的登录页面、需要如下内容 

#### (1) 登录页权限配置

> 同样配置在先前的SecurityConfig类中：[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)
>
> ~~~java
> @Configuration
> public class SecurityConfig {
>   ...
> 
>   @Bean
>   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>    	return http
>    		...
>    		// 登录页为/login，其它使用了默认配置
>    		// * 用户名和密码使用约定的参数名username和password
>    		// * 处理登录POST请求的url路径为同名路径/login
>    		// * 登录成功后跳转回触发登录的页面，如果直接在浏览器输入/login登录则跳转到首页"/"
>    		// 视图模板/src/main/resources/templates/login.html的代码也必须准寻相同的命名约定
>    		.and().formLogin().loginPage("/login")
>    		...
>    		.and().build();
>       }
> }
> ~~~
>
> 也可以明确地进行配置，例如
>
> ~~~java
> .and().formLogin()
>    .loginPage("/login") 				  // 登录页地址为/login
>    .loginProcessingUrl("/authenticate")  // 处理从登录页发来的登录请求的url地址为/authenticate
>    .usernameParameter("user")			  // 用户名的参数为user
>    .passwordParameter("pwd")			  // 密码的参数为pwd
>    .defaultSuccessUrl("/design")		  // 如果直接在浏览器输入/login对应的地址并登录、登录成功后跳转地址为/design
>    //.defaultSuccessUrl("/design", true) // 不论从哪个页面触发、所有登录成功后都跳转到/design页面
> ~~~

#### (2) ViewController

> 因为后端只执行一个简单的url跳转，没有其他业务逻辑，因此不必专门编写一个Controller类
>
> 简单配置一个视图控制器（View Controller）即可
>
> 代码：[/src/main/java/.../web/WebConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/web/WebConfig.java)
>
> ~~~java
> @Override
> public void addViewControllers(ViewControllerRegistry registry) {
>         // 一行一个小朋友
>         // 对"/"的请求跳转到视图模板"/src/main/resources/templates/home.html"生成的页面上
>         registry.addViewController("/").setViewName("home");
>         // 对"/login"的请求跳转到同名视图模板（即"src/main/resources/templates/login.html"）生成的页面上
>         registry.addViewController("/login");
> }
> ~~~

#### (3) View

> 代码：[/src/main/resources/templates/login.html](../ch05/taco-cloud-sfc/src/main/resources/templates/login.html)
>
> ```html
> ...
> <div th:if="${error}">Unable to login. Check your username and password.</div>
> <p>New here? Click <a th:href="@{/register}">here</a> to register.</p>
> <form id="loginForm" method="POST" th:action="@{/login}">
>         <label for="username">Username: </label><input id="username" name="username" type="text"/><br/>
>         <label for="password">Password: </label><input id="password" name="password" type="password"/><br/>
>         <input type="submit" value="Login"/>
> </form>
> ...
> ```

### 5.3.3 使用第三方认证 

> 例如”Sign In with Facebook“，”Login with Twitter“等，使用OAuth2、OIDC（OpenID Connect）等来实现，OIDC是一个构建在OAuth2基础上的第三方登录验证机制。本节介绍如何使用这些技术，在第九章介绍REST API时会再次用到它

#### (1) 引入spring-boot-starter-oauth2-client

> ~~~xml
> <dependency>
>   	<groupId>org.springframework.boot</groupId>
>   	<artifactId>spring-boot-starter-oauth2-client</artifactId>
> </dependency>
> ~~~

#### (2) 配置OAuth2或OpenID Connect Servers

> Spring Security支持Facebook、Google、GitHub以及Okta，也可以通过自定义配置的方式来支持其他登录方
>
> ~~~yml
> spring:
>   	security:
>    		oauth2:
>    			client:
>    				registration:
>    					<oauth2 or openid provider name>:
>    						clientId: <client id>
>    						clientSecret: <client secret>
>    						scope: <comma-separated list of requested scopes>
> ~~~
>
> 例如，如果想通过Facebook来登录，可以在application.yml做如下配置
>
> ~~~yml
> spring:
>   	security:
>    		oauth2:
>    			client:
>    				registration:
>    					facebook:
>    						# 通过https://developers.facebook.com/来获取clientId和clientSecret
>    						clientId: <facebook client id>
>    						clientSecret: <facebook client secret>
>    						# 允许应用在用户登录访问他们的email和profile信息
>    						scope: email, public_profile
> ~~~
>
> 对于使用了`SecurityFilterChain`的应用，还需要把oauth2Login配置在filter chain中，例如
>
> ~~~java
> @Bean
> public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>   	return http
>            .authorizeRequests()
>                .mvcMatchers("/design", "/orders").hasRole("USER")
>                .anyRequest().permitAll()
>            .and().formLogin().loginPage("/login")
>            .and().oauth2Login().loginPage("/login")
>            ...
>            .and().build();
> }
> ~~~
>
> 在这里，不论何时用户需要登录时，都会跳转到/login页面。在/login页面即可以提供普通的用户名密码登录，也可以在页面中添加一个OAuth2链接、让用户选择使用OAuth2登录
>
> ~~~html
> <a th:href="/oauth2/authorization/facebook">Sign in with Facebook</a>
> ~~~

### 5.3.4 退出  

#### (1) 退出页配置

> 同样配置在先前的SecurityConfig类中：[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)
>
> ~~~java
> @Configuration
> public class SecurityConfig {
>   	...
>   	@Bean
>   	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>        	return http
>               .authorizeRequests()
>                 .mvcMatchers("/design", "/orders").hasRole("USER")
>                 .anyRequest().permitAll()
>               .and().formLogin().loginPage("/login")
>               // 登出页配置
>               .and()
>                 .logout()
>                 .logoutSuccessUrl("/") //退出后重定向到"/"，如果不配置会重定向到默认的"/login"页
>               .and().csrf().ignoringAntMatchers("/h2-console/**") // Make H2-Console non-secured; for debug purposes
>               .and().headers().frameOptions().sameOrigin() // Allow pages to be loaded in frames from the same origin; needed for H2-Console
>               .and().build();
>   	}
> }
> ~~~
>
> `.and().logout().logoutSuccessUrl("/")`配置会创建一个安全过滤器、拦截对"/logout"的请求，收到该请求时会清空Session并跳转到 "/"页面。前端页面可以采用类似如下的方式发送`登出请求`
>
> ~~~html
> <form method="POST" th:action="@{/logout}">
>   	<input type="submit" value="Logout"/>
> </form>
> ~~~

###  5.3.5 防止跨站请求伪造 （Cross-Site Request Forgery，CSRF）攻击

#### (1) CSRF攻击 

> 例如用户在攻击者伪造的页面上填写表单、然后这个伪造页面将请求POST到银行Web站点的URL上（假定该银行无法抵御这种攻击）实现转账

#### (2) 防止CSRF攻击

> Spring Security内置CSRF保护、并且默认开启：
>
> * 唯一需要做的事情是确保应用中的每一个表单都要有一个名为"_csrf"的字段以便能够持有CSRF token
>
> * 另一种更加简化的配置方式是在表单中加一个隐藏字段，例如下面例子（用thymeleaf编写）
>
>     ~~~html
>     <input type="hidden" name="_csrf" th:value="${_csrf.token}"/>
>     ~~~
>
> * 其实如果使用的是`Thymeleaf`或`Spring MVC's JSP tag library`，甚至可以不添加上面的隐藏域，视图引擎会在渲染时自动加上"_csrf"字段 。以Thymeleaf为例、唯一要做的是，表单中至少有一个字段使用了Thymeleaf的`th:`前缀，例如：
>
>     ~~~html
>     <form method="POST" th:action="@{/login}" id="loginForm">
>     ~~~

#### (3) 关闭Spring Security的CSRF防护（不建议）

> 在先前的SecurityConfig类中：[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)添加如下配置 
>
> ~~~java
> .and().csrf().disable()
> ~~~

## 5.4 方法粒度的Secruity配置 

> 场景：1. 某些路径只希望一部分用户能访问；2. 某些方法，不论从什么url访问，都希望它只能为部分用户服务；3. 某些方法，希望在他们执行完毕后，根据计算结果决定是否容许他们为当前用户服务

### (1) 为部分路径设置不同的权限（场景 1）

> 例如对于/admin/*下面的url，只希望具有ROLE_ADMIN的用户能访问
>
> 在SecurityConfig类（形如[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)）中添加配置
>
> ~~~java
> @Configuration
> public class SecurityConfig {
>    ...
>    @Bean
>    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
>      return http
>          .authorizeRequests()
>          // design，orders路径开发给具有USER权限的用户
>          .mvcMatchers("/design", "/orders").hasRole("USER")
>          // admin路径只开放给具有ADMIN权限的用户
>          .antMatchers(HttpMethod.POST, "/admin/**").access("hasRole('ADMIN')")
>          .anyRequest().permitAll()
>          .and().formLogin().loginPage("/login")
>          .and()
>          .logout()
>          .logoutSuccessUrl("/") 
>          .and().csrf().ignoringAntMatchers("/h2-console/**") // Make H2-Console non-secured; for debug purposes
>          .and().headers().frameOptions().sameOrigin() // Allow pages to be loaded in frames from the same origin; needed for H2-Console
>          .and().build();
>    }
> }
> ~~~
>
> 但是这并不能彻底解决问题

###  (2) 为某些方法设置访问权限（场景2：@PreAuthorize）

> 例如service层的deleteAllOrder()方法，希望只开放给具有ADMIN_ROLE的用户，不论该方法调用来自哪个controller哪个url。对代码的改动如下：
>
> 步骤1：在SecurityConfig类（形如[/src/main/java/.../security/SecurityConfig.java](../ch05/taco-cloud-sfc/src/main/java/tacos/security/SecurityConfig.java)）上使用`@EnableGlobalMethodSecurity`注解开启方法权限设置功能
>
> ~~~java
> @Configuration
> @EnableGlobalMethodSecurity
> public class SecurityConfig extends WebSecurityConfigurerAdapter {
>   	...
> }
> ~~~
>
> 步骤2：在该方法上使用@PreAuthorize注解
>
> ~~~java
> @PreAuthorize("hasRole('ADMIN')")
> public void deleteAllOrders() {
>   	orderRepository.deleteAll();
> }
> ~~~
>
> 注解中传入的是一个SpEL表达式（如前面的章节所示），该表达式的值
>
> * 为true时容许该方法执行
> * 为false会抛出AccessDeniedException（是一个unchecked  exception），在没有代码捕捉该异常的情况下，最终会返回HTTP 403错误给客户端、或跳转到登录页（当用户没有登录时）

### (3) 根据方法的执行结果判断当前用户是否有权限（场景3：@PostAuthorize）

> 例如对于getOrder方法 ，希望他们只为具有ADMIN_ROLE权限的用户，以及order的主人能够访问 。但是order的主人是谁，只有在方法执行完毕拿到order时才能知道
>
> 步骤1：在SecurityConfig类上使用`@EnableGlobalMethodSecurity`注解开启方法权限设置功能（同上一小节）
>
> 步骤2：在该方法上使用@PostAuthorize注解
>
> ~~~java
> @PostAuthorize("hasRole('ADMIN') || returnObject.user.username == authentication.name")
> public TacoOrder getOrder(long id) {
>   	...
> }
> ~~~
>
> 同样注解内的SpEL表达式返回true时容许方法返回结果，否则抛出AccessDeniedException

## 5.5 获取用户信息以优化用户体验 

> 例如在用户获取订单时，如果同时知道用户是谁，就可以在订单中填充用户姓名、地址等以优化体验。
>
> 步骤如下

### (1) 在TacoOrder类中添加User属性

> [/src/main/java/.../Order.java](../ch05/taco-cloud-sfc/src/main/java/tacos/Order.java) 
>
> ~~~java
> @Data
> @Entity
> @Table(name="Taco_Order")
> public class TacoOrder implements Serializable {
> 	...
> 	@ManyToOne
> 	private User user;
> 	...
> }
> ~~~

### (2) 获取当前用户并设置给TacoOrder对象

> [/src/main/java/.../web/OrderController.java](../ch05/taco-cloud-sfc/src/main/java/tacos/web/OrderController.java)
>
> OrderController的processOrder方法负责获取当前的用户，并将它设置给TacoOrder对象 
>
> ~~~java
> @PostMapping
> public String processOrder(
> 	// @Valid注解开启对Order内字段的校验 
> 	@Valid 
> 	Order order, 
>     Errors errors, 
> 	SessionStatus sessionStatus, 
> 	// @AuthenticationPrincipal让框架注入当前用户
> 	@AuthenticationPrincipal 
>     User user
> ) {
>      // @Valid校验异常时，返回到orderForm并提示设置在Order类中的错误提示信息，让用户修改
>      if (errors.hasErrors()) {
> 		return "orderForm";
>      }
>      // 把当前用户设置在Order对象中
>      order.setUser(user);
>      orderRepo.save(order);
>      // 标记session为completed并容许清理session数据
>      sessionStatus.setComplete();
>      return "redirect:/";
> }
> ~~~
>
> ##### Controller获取当前用户的几种方法
>
> * 注入一个`Principal`对象 
>
>     ~~~java
>     @PostMapping
>     public String processOrder(@Valid TacoOrder order, Errors errors,
>         SessionStatus sessionStatus, Principal principal /*引入了Security相关的代码*/) {
>     	...
>     	User user = userRepository.findByUsername(principal.getName()); //需要查数据库
>     	order.setUser(user);
>     	...
>     }
>     ~~~
>
> * 注入一个`Authentication`对象
>
>     ~~~java
>     @PostMapping
>     public String processOrder(@Valid TacoOrder order, Errors errors,
>         SessionStatus sessionStatus, Authentication authentication /*引入了Security相关的代码*/) {
>     	...
>     	User user = (User) authentication.getPrincipal(); // 需要把Object转型为User对象 
>     	order.setUser(user);
>     	...
>     }
>     ~~~
>
> * 注入一个`SecurityContextHolder`对象
>
>     ~~~java
>     /*同样引入了Security相关的代码*/
>     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
>     User user = (User) authentication.getPrincipal(); // 需要把Object转型为User对象 
>     ~~~
>
> * 使用`@AuthenticationPrincipal`对注解（上面例子所采用的方法），它是最干净的方法：
>
>    * 将security-specific code缩小到@AuthenticationPrincipal注解（想一想有一天Spring Security的内部实现变了）
>     * 没有Object到User的转型，也不需要查数据库

##  5.6 小结

> 略 

## 附录

> 《Spring In Action第6版》删减的内容
>
> *  2.3以ldap为后端的用户存储