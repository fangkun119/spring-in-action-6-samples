<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH08 REST Client](#ch08-rest-client)
  - [8.1 使用RestTemplate来访问REST endpoint](#81-%E4%BD%BF%E7%94%A8resttemplate%E6%9D%A5%E8%AE%BF%E9%97%AErest-endpoint)
    - [(1) Spring访问REST endpoint的三种方法](#1-spring%E8%AE%BF%E9%97%AErest-endpoint%E7%9A%84%E4%B8%89%E7%A7%8D%E6%96%B9%E6%B3%95)
    - [(2) RestTemplate的作用](#2-resttemplate%E7%9A%84%E4%BD%9C%E7%94%A8)
    - [(3) RestTemplate的12个操作](#3-resttemplate%E7%9A%8412%E4%B8%AA%E6%93%8D%E4%BD%9C)
    - [(4) 创建RestTemplate](#4-%E5%88%9B%E5%BB%BAresttemplate)
    - [8.1.1 GET](#811-get)
      - [(1) `getForObject(...)`](#1-getforobject)
        - [(a) 获得Response Body](#a-%E8%8E%B7%E5%BE%97response-body)
        - [(b) 可变参数列表表示url参数](#b-%E5%8F%AF%E5%8F%98%E5%8F%82%E6%95%B0%E5%88%97%E8%A1%A8%E8%A1%A8%E7%A4%BAurl%E5%8F%82%E6%95%B0)
        - [(c) 用map封装url参数](#c-%E7%94%A8map%E5%B0%81%E8%A3%85url%E5%8F%82%E6%95%B0)
        - [(d) 用java.net.URI表示带有参数的URL](#d-%E7%94%A8javaneturi%E8%A1%A8%E7%A4%BA%E5%B8%A6%E6%9C%89%E5%8F%82%E6%95%B0%E7%9A%84url)
      - [(2) `getForEntity(...)`](#2-getforentity)
        - [(a) 获得Response Body以及Header的信息](#a-%E8%8E%B7%E5%BE%97response-body%E4%BB%A5%E5%8F%8Aheader%E7%9A%84%E4%BF%A1%E6%81%AF)
    - [8.1.2 PUT](#812-put)
      - [(1) 例子](#1-%E4%BE%8B%E5%AD%90)
    - [8.1.3 DELETE](#813-delete)
      - [(1) 例子](#1-%E4%BE%8B%E5%AD%90-1)
    - [8.1.4 POST](#814-post)
      - [(1) `postForObject(...)`](#1-postforobject)
      - [(2) `postForLocation(...)`](#2-postforlocation)
      - [(3) `postForEntity(...)`](#3-postforentity)
  - [8.2 使用Traverson来进行REST API导航](#82-%E4%BD%BF%E7%94%A8traverson%E6%9D%A5%E8%BF%9B%E8%A1%8Crest-api%E5%AF%BC%E8%88%AA)
    - [(1) 功能](#1-%E5%8A%9F%E8%83%BD)
    - [(2) 代码编写](#2-%E4%BB%A3%E7%A0%81%E7%BC%96%E5%86%99)
      - [(a) 创建Traverson Bean](#a-%E5%88%9B%E5%BB%BAtraverson-bean)
      - [(b) 跟随关系名"ingredients"导航](#b-%E8%B7%9F%E9%9A%8F%E5%85%B3%E7%B3%BB%E5%90%8Dingredients%E5%AF%BC%E8%88%AA)
      - [(c) 跟随跨两级的关系名“tacos”，"recents"来导航](#c-%E8%B7%9F%E9%9A%8F%E8%B7%A8%E4%B8%A4%E7%BA%A7%E7%9A%84%E5%85%B3%E7%B3%BB%E5%90%8Dtacosrecents%E6%9D%A5%E5%AF%BC%E8%88%AA)
      - [(d) 写入和删除资源](#d-%E5%86%99%E5%85%A5%E5%92%8C%E5%88%A0%E9%99%A4%E8%B5%84%E6%BA%90)
  - [8.3 小结](#83-%E5%B0%8F%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH08 REST Client

内容来源：https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-8/v-4/26 （限时预览、超过时限后要购买才能继续阅读）

Demo项目：[../ch08/](../ch08/)

> * [../ch08/tacocloud-api/](../ch08/tacocloud-api/)
> * [../ch08/tacocloud-data/](../ch08/tacocloud-data/)
> * [../ch08/tacocloud-domain/](../ch08/tacocloud-domain/)
> * [../ch08/tacocloud-restclient/](../ch08/tacocloud-restclient/)（本节代码）
> * [../ch08/tacocloud-security/](../ch08/tacocloud-security/)
> * [../ch08/tacocloud-ui/](../ch08/tacocloud-ui/)
> * [../ch08/tacocloud-web/](../ch08/tacocloud-web/)
> * [../ch08/tacos/](../ch08/tacos/)

## 8.1 使用RestTemplate来访问REST endpoint

### (1) Spring访问REST endpoint的三种方法

> * `RestTemplate`：Spring核心框架提供的REST客户端
> * `Traverson`：Spring HATEOAS提供的支持`相关API URL`列表的`同步REST客户端`
> * `WebClient`：`反应式、异步REST客户端`（将在第11章介绍）

### (2) RestTemplate的作用

用模板封装重复率高、单调乏味的代码，减少开发量

> 例如：创建client实例、构造请求对象 、执行请求、解析响应、将响应映射为Domain Object、处理异常等

### (3) RestTemplate的12个操作

> 下表是12个RestTempate操作、这12个操作的方法参数有多种重载方式，总共构成41个方法
>
> | Method               | Description                                                  |
> | -------------------- | ------------------------------------------------------------ |
> | `delete(…)`          | Performs an `HTTP DELETE` request on a resource at a specified URL |
> | `exchange(…)`        | Executes a `specified HTTP method` against a URL, returning a `ResponseEntity` containing an object mapped from the response body |
> | `execute(…)`         | Executes a `specified HTTP method` against a URL, returning an `object` mapped from the response body |
> | `getForEntity(…)`    | Sends an `HTTP GET` request, returning a `ResponseEntity` containing an object mapped from the response body |
> | `getForObject(…)`    | Sends an `HTTP GET` request, returning an `object` mapped from a response body |
> | `headForHeaders(…)`  | Sends an `HTTP HEAD` request, returning the `HTTP headers` for the specified resource URL |
> | `optionsForAllow(…)` | Sends an `HTTP OPTIONS` request, returning the `Allow header` for the specified URL |
> | `patchForObject(…)`  | Sends an `HTTP PATCH` request, returning the resulting `object` mapped from the response body |
> | `postForEntity(…)`   | `POSTs` data to a URL, returning a `ResponseEntity` containing an object mapped from the response body |
> | `postForLocation(…)` | `POSTs` data to a URL, returning the `URL` of the newly created resource |
> | `postForObject(…)`   | `POSTs` data to a URL, returning an `object` mapped from the response body |
> | `put(…)`             | `PUTs` resource data to the specified URL                    |
>
> 方法说明：
>
> * 上面的`Description`列 ：关注`HTTP Method`和`返回值类型`即可、其他内容基本一样
> * RestTemplate为每种标准HTTP方法（除了HTTP TRACE）都提供了至少一个专用方法
> * `execute()`和`exchange()`用来提供 low-level通用方法、以便支持任意HTTP操作
>
> 参数重载形式：大多数操作都以3种方式进行重载，3种参数形式分别是：
>
> * 参数1：`String`表示URL；参数2：`可变参数列表`表示URL参数
> * 参数1：`String`表示URL；参数2：`Map<String, String>`表示URL参数
> * 参数1：`java.net.URI`表示带有参数的URL

### (4) 创建RestTemplate

> 直接new一个RestTemplate
>
> ~~~java
> RestTemplate rest = new RestTemplate();
> ~~~
>
> 或者把RestTemplate定义成一个Bean（singleton scope）注入到需要使用的地方即可
>
> 以使用Java Config类创建Bean为例，代码如下
>
> [/tacocloud-restclient/src/main/java/tacos/restclient/RestExamples.java](../ch08/tacocloud-restclient/src/main/java/tacos/restclient/RestExamples.java)
>
> ~~~java
> @Bean
> public RestTemplate restTemplate() {
> 	return new RestTemplate();
> }
> ~~~
>
> 然后就可以使用上面的12种操作41种方法访问REST端点

### 8.1.1 GET

#### (1) `getForObject(...)`

##### (a) 获得Response Body

##### (b) 可变参数列表表示url参数

> ~~~java
> public Ingredient getIngredientById(String ingredientId) {
>   return rest.getForObject(
>       "http://localhost:8080/ingredients/{id}", // URL模板、{id}为占位符
>       Ingredient.class, // 返回的Object类型
>       ingredientId		// 占位符替换值
>   );
> }
> ~~~

##### (c) 用map封装url参数

> ~~~java 
> public Ingredient getIngredientById(String ingredientId) {
> 	Map<String, String> urlVariables = new HashMap<>();
> 	urlVariables.put("id", ingredientId);
> 	return rest.getForObject("http://localhost:8080/ingredients/{id}",
> 		Ingredient.class, urlVariables);
> }
> ~~~

##### (d) 用java.net.URI表示带有参数的URL

> ~~~java
> public Ingredient getIngredientById(String ingredientId) {
> 	Map<String, String> urlVariables = new HashMap<>();
> 	urlVariables.put("id", ingredientId);
> 	URI url = UriComponentsBuilder
> 		.fromHttpUrl("http://localhost:8080/ingredients/{id}")
> 		.build(urlVariables);
> 	return rest.getForObject(url, Ingredient.class);
> }
> ~~~

#### (2) `getForEntity(...)`

#####  (a) 获得Response Body以及Header的信息

> ~~~java
> public Ingredient getIngredientById(String ingredientId) {
> 	ResponseEntity<Ingredient> responseEntity =
> 		rest.getForEntity("http://localhost:8080/ingredients/{id}",
> 						  Ingredient.class, ingredientId);
>     // 在日志中输出来自response header的数据
> 	log.info("Fetched time: " + responseEntity.getHeaders().getDate());
>     // 返回来自response body的数据
> 	return responseEntity.getBody();
> }
> ~~~

### 8.1.2 PUT

#### (1) 例子

> ~~~java
> public void updateIngredient(Ingredient ingredient) {
> 	rest.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
> }
> ~~~
>
> 说明：
>
> * 3种参数形式与`8.1.1 GET`相同、这里只举一个列子
>
> * 第2个参数ingredient是Request Body的载荷，它将用这个对象`替换`后端id相同的数据（与POST不同、PUT在语义上更偏向于更新操作、类似Map的put方法）
>
> * 返回类型是void

### 8.1.3 DELETE

#### (1) 例子

> ~~~java
> public void deleteIngredient(Ingredient ingredient) {
> 	rest.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
> }
> ~~~

### 8.1.4 POST

#### (1) `postForObject(...)`

> ~~~java
> public Ingredient createIngredient(Ingredient ingredient) {
> 	return rest.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
> }
> ~~~
>
> 对象ingredient（通常代表新创建的资源）会POST给服务端，同时也会作为函数的返回值，供客户端使用

#### (2) `postForLocation(...)`

> ~~~java
> public java.net.URI createIngredient(Ingredient ingredient) {
> 	return rest.postForLocation("http://localhost:8080/ingredients", ingredient);
> }
> ~~~
>
> 会返回资源的地址（URI），这个值是从Response Header的Location字段中派生出来的

#### (3) `postForEntity(...)`

> ~~~java
> public Ingredient createIngredient(Ingredient ingredient) {
> 	ResponseEntity<Ingredient> responseEntity =
>          rest.postForEntity("http://localhost:8080/ingredients",
>                             ingredient,
>                             Ingredient.class);
>     // 在日志中打印Response Header中的数据
> 	log.info("New resource created at " + responseEntity.getHeaders().getLocation());
>     // 返回新创建的资源对象
> 	return responseEntity.getBody();
> }
> ~~~
>
> 同时需要Response Header（例如header.location中的Resource URI）和Response Body，可使用该方法

## 8.2 使用Traverson来进行REST API导航

### (1) 功能 

> Traverson来自于Spring Data  HATEOAS项目，取名灵感来自于[同名JavaScript库](https://github.com/traverson/traverson)（traverson: travers on)
>
> 可以通过遍历API关系名（API relation name）的方式来找到API URI，访问对应的REST端点

### (2) 代码编写

> 项目module：[../ch08/tacocloud-restclient/](../ch08/tacocloud-restclient/)

#### (a) 创建Traverson Bean

> [/tacocloud-restclient/src/main/java/tacos/restclient/RestExamples.java](../ch08/tacocloud-restclient/src/main/java/tacos/restclient/RestExamples.java)
>
> ~~~java
> @SpringBootConfiguration
> @ComponentScan
> @Slf4j
> public class RestExamples {
> 	@Bean
> 	public Traverson traverson() {
> 		Traverson traverson = new Traverson(
>             // 指向HATEOAS基础URL（服务器端实现见上一章）
> 			URI.create("http://localhost:8080/api"),
>             // 指定该API返回具有HAL风格超链接的JSON
>             MediaTypes.HAL_JSON);
> 		return traverson;
> 	}
>     ...
> }
> ~~~

#### (b) 跟随关系名"ingredients"导航

> API关系：其中关系名"ingredients"的REST API URL为`http://localhost:8080/api/ingredients`
>
> ~~~bash
> $ curl localhost:8080/api
> {
> 	"_links" : {
> 		"orders" : {
> 			"href" : "http://localhost:8080/api/orders"
> 		},
> 		"ingredients" : {
> 			"href" : "http://localhost:8080/api/ingredients"
> 		},
> 		"tacos" : {
> 			"href" : "http://localhost:8080/api/tacos{?page,size,sort}",
> 			"templated" : true
> 		},
> 		"users" : {
> 			"href" : "http://localhost:8080/api/users"
> 		},
> 		"profile" : {
> 			"href" : "http://localhost:8080/api/profile"
> 		}
> 	}
> }
> ~~~
>
> 代码：[/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java](../ch08/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java)
>
> ```java
> public Iterable<Ingredient> getAllIngredientsWithTraverson() {
>     // 服务器返回数据的类型
>     // * 类型其实是CollectionModel<Ingredient>（参考上一节的代码）
>     // * 然而为了克服泛型擦除的影响，需要用ParameterizedTypeReference<>来包裹
> 	ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
> 		new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};
> 	
>     // 获取服务器返回的数据
> 	CollectionModel<Ingredient> ingredientRes =
> 		traverson
> 			.follow("ingredients")		// 根据关系名"ingredients"来进行导航
> 			.toObject(ingredientType);	// 服务器返回的数据类型
> 
>     // 根据上一节的内容，
>     // * CollectionModel<Ingredient>即包含了数据
>     // * 也包含了相关Rest API的URL
>     // 通过调用ingredientRes.getContent()获取数据
> 	Collection<Ingredient> ingredients = ingredientRes.getContent();
> 	return ingredients;
> }
> ```

#### (c) 跟随跨两级的关系名“tacos”，"recents"来导航

> API关系：其中
>
> * 关系名"tecos"的REST API URL为`http://localhost:8080/api/tacos{?page,size,sort}`
> * "tecos"下一级关系名“recents"对应的API URL为`http://localhost:8080/api/tacos/recent`
>
> ~~~bash
> $ curl localhost:8080/api
> {
> 	"_links" : {
> 		...
> 		"ingredients" : {
> 			"href" : "http://localhost:8080/api/ingredients"
> 		},
> 		"tacos" : {
> 			"href" : "http://localhost:8080/api/tacos{?page,size,sort}",
> 			"templated" : true
> 		},
> 		...
> 	}
> }
> $ curl localhost:8080/tacos
> {
> 	...
> 	"_links" : {
> 		"first" : {
> 			"href" : "http://localhost:8080/api/tacos?page=0&size=5"
> 		},
> 		"self" : {
> 			"href" : "http://localhost:8080/api/tacos"
> 		},
> 		"next" : {
> 			"href" : "http://localhost:8080/api/tacos?page=1&size=5"
> 		},
> 		"last" : {
> 			"href" : "http://localhost:8080/api/tacos?page=2&size=5"
> 		},
> 		"profile" : {
> 			"href" : "http://localhost:8080/api/profile/tacos"
> 		},
> 		"recents" : {
> 			"href" : "http://localhost:8080/api/tacos/recent"
> 		}
> 	}
> }
> ~~~
>
> 跟随两层API关系进行导航 ，并访问REST端点
>
> 代码：[/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java](../ch08/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java)
>
> ~~~java
> public Iterable<Taco> getRecentTacosWithTraverson() {
> 	// 服务器返回数据的类型
> 	// * 类型其实是CollectionModel<Teco>（参考上一节的代码）
> 	// * 然而为了克服泛型擦除的影响，需要用ParameterizedTypeReference<>来包裹	
> 	ParameterizedTypeReference<CollectionModel<Taco>> tacoType =
> 		new ParameterizedTypeReference<CollectionModel<Taco>>() {};
> 
> 	// 跟随上面的两级关系名来导航
> 	CollectionModel<Taco> tacoRes =
> 		traverson
> 			.follow("tacos").follow("recents") // 等价于.follow("tacos", "recents")
> 			.toObject(tacoType);
> 
> 	// 根据上一节的内容，
> 	// * CollectionModel<Teco>既包含了数据
> 	// * 也包含了相关Rest API的URL
> 	// 通过调用ingredientRes.getContent()获取数据
> 	Collection<Taco> tacos = tacoRes.getContent();
> 	return tacos;
> }
> ~~~

#### (d) 写入和删除资源

> `Traverson`擅长导航HATEOAS的API并获取资源，但是它<b>没有写入和删除资源的API</b>
>
> 方法是使用`Traverson`导航得到资源的URL，然后再通过`RestTemplate`来写入或删除资源，例子如下
>
> 代码：[/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java](../ch08/tacocloud-restclient/src/main/java/tacos/restclient/TacoCloudClient.java)
>
> ~~~java
> @Service
> @Slf4j
> public class TacoCloudClient {
> 	private RestTemplate rest;
> 	private Traverson traverson;
> 
> 	public TacoCloudClient(RestTemplate rest, Traverson traverson) {
> 		this.rest = rest;
> 		this.traverson = traverson;
> 	}
> 
> 	... 
>       
> 	public Ingredient addIngredient(Ingredient ingredient) {
>         // 使用Traverson导航获得资源的URL
> 		String ingredientsUrl = traverson
> 			.follow("ingredients")
> 			.asLink()
> 			.getHref();
>         // 使用RestTemplate写入或删除资源
>         // 注意需要服务端对该资源HTTP Method实现比较规范和完整，
>         // 比如是HATEOAS自动生成的API而非类似tacos/recent这样的自定义API
> 		return rest.postForObject(ingredientsUrl, ingredient, Ingredient.class);
> 	}
>     
>     ...
> }
> ~~~

## 8.3 小结

> 略