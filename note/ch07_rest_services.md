[TOC]

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH07 REST Service

> Demo项目：[../ch07/](../ch07/) ，以下是这个项目的modules
>
> * [../ch07/tacocloud-api/](../ch07/tacocloud-api/)：REST API
> * [../ch07/tacocloud-domain/](../ch07/tacocloud-domain/)：Domain Objects
> * [../ch07/tacocloud-ui/](../ch07/tacocloud-ui/)：用Angular写的前端SPA（Single Page Application）页面
> * [../ch07/tacos/](../ch07/tacos/)
> * [../ch07/tacocloud-data/](../ch07/tacocloud-data/)：DAO层 
> * [../ch07/tacocloud-security/](../ch07/tacocloud-security/)：安全相关
> * [../ch07/tacocloud-web/](../ch07/tacocloud-web/)：Controller相关

# 7.1 RESTful Controller

### (1) Controller注解

> 用于Web Page请求的注解，仍然可以用于REST请求
>
> | Annotation        | HTTP method                                                  | Typical use [[a\]](https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-7/v-4/ftn.d5e4624) |
> | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
> | `@GetMapping`     | HTTP `GET` requests                                          | Reading resource data                                        |
> | `@PostMapping`    | HTTP `POST` requests                                         | Creating a resource                                          |
> | `@PutMapping`     | HTTP `PUT` requests                                          | Updating a resource                                          |
> | `@PatchMapping`   | HTTP `PATCH` requests                                        | Updating a resource                                          |
> | `@DeleteMapping`  | HTTP `DELETE` requests                                       | Deleting a resource                                          |
> | `@RequestMapping` | General purpose request handling; HTTP `method` specified in the method attribute |                                                              |

### 7.1.1 查询资源：@GetMapping

#### (1) 例子和说明 

例子功能：展现一批最新创建的Taco

前端页面：

> TS：[/tacocloud-ui/src/app/recents/recents.component.ts](../ch07/tacocloud-ui/src/app/recents/recents.component.ts)
>
> ```typescript
> import { Component, OnInit, Injectable } from '@angular/core';
> import { Http } from '@angular/http'; 
> import { HttpClient } from '@angular/common/http';
> 
> @Component({
> 	selector: 'recent-tacos',
> 	templateUrl: 'recents.component.html',
> 	styleUrls: ['./recents.component.css']
> })
> 
> @Injectable()
> export class RecentTacosComponent implements OnInit {
> 	// 从后端获取的数据，会用来渲染html页面
> 	recentTacos: any;
> 	constructor(private httpClient: HttpClient) { }
> 	ngOnInit() {
> 		// <1> 发送HTTP GET请求、返回结果存入recentTacos中 
> 		this.httpClient.get('http://localhost:8080/design/recent') 
>         	.subscribe(data => this.recentTacos = data);
> 	}
> }
> ```
>
> Html模板：[/tacocloud-ui/src/app/recents/recents.component.html](../ch07/tacocloud-ui/src/app/recents/recents.component.html)

REST  Controller：[/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java](ch07/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java)

> ~~~java
> // 注明这是一个Rest Controller，如果改成@Controller则必须为所有的方法都加上@ResponseBody注解
> @RestController 
> // 只处理HTTP Header的Accept字段为“application/json"的请求
> // 增加此限制，就可以在其他Controller上增加处理相同路径、但是返回内容不是json的方法（例如网页请求）
> // 还可让这个Controller接受多种Accept字段，例如
> // @RequestMapping(path="/design", produces={"application/json", "text/xml"})
> @RequestMapping(path="/design", produces="application/json")
> // Angular部分会运行在其他的host或端口上，增加跨域共享配置，容许来自任何domain的请求
> @CrossOrigin(origins="*")
> public class DesignTacoController {
> 	// 注入repository
>     private TacoRepository tacoRepo;	
>     public DesignTacoController(TacoRepository tacoRepo) {
> 		this.tacoRepo = tacoRepo;
> 	}
> 
>     // 处理发到/design/recent上的GET请求
> 	@GetMapping("/recent")
> 	public Iterable<Taco> recentTacos() {
> 		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
> 		return tacoRepo.findAll(page).getContent();
> 	}
> 
>     ...
> 
>     // 处理发到/design/${id}上的请求
> 	@GetMapping("/{id}")
> 	public Taco tacoById(@PathVariable("id") Long id) {
>     	Optional<Taco> optTaco = tacoRepo.findById(id);
>     	if (optTaco.isPresent()) {
> 			return optTaco.get();
>     	}
>         // 返回一个空的Response以及Status Code 200（OK）
> 		return null;
>         // 如果把Status Code改为404（Not Found）可以采用下面的方法
>         // return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
> 	}
> }
> ~~~

### 7.1.2 创建资源：@PostMapping

####  (1) 例子和说明

例子功能：新增一个Taco

前端页面：

> TypeScript：[/tacocloud-ui/src/app/design/design.component.ts](../ch07/tacocloud-ui/src/app/design/design.component.ts)
>
> ~~~typescript
> onSubmit() {
> 	// HTTP POST请求
> 	this.httpClient.post(
> 		// 将this.model作为BODY
> 		// 发送到http://localhost:8080/design
> 		// Headers内设置'Content-type':'application/json'
> 		'http://localhost:8080/design', this.model, {headers: new HttpHeaders().set('Content-type', 'application/json'),}
> 	).subscribe(
> 		// 回调函数、将返回结果增加到cart中
> 		taco => this.cart.addToCart(taco)
> 	);
> 	this.router.navigate(['/cart']);
> }
> ~~~

REST Controller：[/tacocloud-api/src/main/java/tacos/web/api/DesignTacoController.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/DesignTacoController.java)

> ```java
> @RestController 
> @RequestMapping(path="/design", produces="application/json")
> @CrossOrigin(origins="*")
> public class DesignTacoController {
>     ...
> 
>     // 只处理'Content-type'设置为'application/json'的请求
>     @PostMapping(consumes="application/json")
>     // 在没有抛出异常时的HTTP Status Code为201:CREATED，比200:OK描述性更强
>     @ResponseStatus(HttpStatus.CREATED)
>     public Taco postTaco(@RequestBody/*将Body转成Taco类型*/ Taco taco) {
>       return tacoRepo.save(taco);
>     }
> }
> ```

POST可以用来创建资源、也可以用来更新资源

尽管如此，更典型的用法是，POST用来创建资源，PUT和PATCH用来更新资源 

### 7.1.3 更新资源：@PutMapping、@PatchMapping

#### (1) PUT和PATCH的语义表达区别

> 从语义表达的角度讲
>
> * `PUT`：站在`GET`的对立面、更偏向于表示完整的数据替换（wholesale replacement  operation）
> * `PATCH`：则更偏向用来表示一个patching或者部分更新（partial update）
>
> 但是这些差别仅仅是从语义表达层面的代码规范和建议
>
> 从程序运行角度讲，使用@PutMapping还是@PatchMapping
>
> * 仅仅限制可以匹配HTTP  Header中怎样的Contente-type
> * 而Controller中的代码是完整更新还是部分更新，并不会有硬性限制

#### (2) PUT的例子

> [/tacocloud-web/src/main/java/tacos/web/OrderController.java](../ch07/tacocloud-web/src/main/java/tacos/web/OrderController.java)
>
> ~~~java
> @RestController
> @RequestMapping(path="/orders", produces="application/json")
> @CrossOrigin(origins="*")
> public class OrderApiController {
>     ...
>     @PutMapping(path="/{orderId}", consumes="application/json")
>     public Order putOrder(@PathVariable("orderId") Long orderId, @RequestBody Order order) {
>         order.setId(orderId);
>         return repo.save(order);
>     }
>     ...
> }
> ~~~
>
> 操作倾向于完整的Order

#### (3) PATCH的例子

> [/tacocloud-web/src/main/java/tacos/web/OrderController.java](../ch07/tacocloud-web/src/main/java/tacos/web/OrderController.java)
>
> ~~~java
> @RestController
> @RequestMapping(path="/orders", produces="application/json")
> @CrossOrigin(origins="*")
> public class OrderApiController {
>     ...
>     @PatchMapping(path="/{orderId}", consumes="application/json")
>     public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
>         Order order = repo.findById(orderId).get();
>         if (patch.getDeliveryName() != null) {
>             order.setDeliveryName(patch.getDeliveryName());
>         }
>         if (patch.getDeliveryStreet() != null) {
>             order.setDeliveryStreet(patch.getDeliveryStreet());
>         }
>         ...
>         if (patch.getCcCVV() != null) {
>             order.setCcCVV(patch.getCcCVV());
>         }
>         return repo.save(order);
>     }
>     ...
> }
> ~~~
>
> 使用Patch更加倾向于部分更新资源

### 7.1.4 删除数据：@DeleteMapping

> [/tacocloud-web/src/main/java/tacos/web/OrderController.java](../ch07/tacocloud-web/src/main/java/tacos/web/OrderController.java)
>
> ~~~java
> @RestController
> @RequestMapping(path="/orders", produces="application/json")
> @CrossOrigin(origins="*")
> public class OrderApiController {
>     ...
> 	@DeleteMapping("/{orderId}")
>     // 正常情况下返回HTTP Status Code 204: NO CONTENT
> 	@ResponseStatus(HttpStatus.NO_CONTENT)
> 	public void deleteOrder(@PathVariable("orderId") Long orderId) {
> 		try {
> 			repo.deleteById(orderId);
>     	} catch (EmptyResultDataAccessException e) {
>             // 不处理EmptyResultDataAccessException是希望表示在资源部存在时执行删除操作也视为成功
>             // 另一个选择是返回HTTP Status Code 404:NOT FOUND
>         }
>   	}
>     ...
> }
> ~~~

## 7.2 使用HATEOAS（超媒体应用状态引擎）

### (1) 超媒体作为应用状态引擎

Hypermedia as the Engine of Application State（简称HATEOAS）

> * 是一种创建自描述API的方式，API会返回包含相关资源的链接
> * 这样客户端只需知道少量与API相关的信息就可以进行调用，而不需要hard coding API的url

下面是两个例子，假设客户端需要在`最近创建的taco列表页面`中，增加对每个taco进行处理、以及对taco的具体原料进行操作，会需要生成相对应的REST API URL

#### 未开启HATEOAS

> 步骤1：访问后端，获取taco列表
>
> ~~~json
> [
> 	{
> 		"id": 4,
> 		"name": "Veg-Out",
> 		"createdAt": "2018-01-31T20:15:53.219+0000",
> 		"ingredients": [
> 			{"id": "FLTO", "name": "Flour Tortilla", "type": "WRAP"},
> 			{"id": "COTO", "name": "Corn Tortilla", "type": "WRAP"},
> 			{"id": "TMTO", "name": "Diced Tomatoes", "type": "VEGGIES"},
> 			{"id": "LETC", "name": "Lettuce", "type": "VEGGIES"},
> 			{"id": "SLSA", "name": "Salsa", "type": "SAUCE"}
> 		]
> 	},
> 	...
> ]
> ~~~
>
> 步骤二：根据得到id等、在客户端的代码中“hard code”拼接出所需要的REST API URL
>
> 问题：当客户端、URL数量增多时，维护会成为负担，URL的路径也无法修改

#### 开启HATEOAS后

> 客户端获取taco列表，而与单个taco以及原料相关的REST API URL，也有服务端生成。以后URL相关的修改时，只需要在服务端进行
>
> ~~~json
> {
> 	"_embedded": {
> 		"tacoResourceList": [
>             {
> 				"name": "Veg-Out",
> 				"createdAt": "2018-01-31T20:15:53.219+0000",
> 				"ingredients": [{
> 						"name": "Flour Tortilla", "type": "WRAP",
> 						"_links": {"self": { "href": "http://localhost:8080/ingredients/FLTO" }}
> 					},{
> 						"name": "Corn Tortilla", "type": "WRAP",
> 						"_links": {"self": { "href": "http://localhost:8080/ingredients/COTO" }}
> 					},
> 					...
> 				],
> 				"_links": {"self": { "href": "http://localhost:8080/design/4" }}
> 			},
> 			...
> 		]
> 	},
> 	"_links": {"recents": {"href": "http://localhost:8080/design/recent"}}
> }
> ~~~

### (2) HAL (Hypertext Application Language)

> HATEOAS选用了HAL（[http://stateless.co/hal_specification.html](http://stateless.co/hal_specification.html)）作为在JSON中嵌入超链接的语言表达方式。以上面的例子为例，它为
>
> * 每个taco，以及每个ingredient都加了一个`"_links":{"self":{...}}`属性
>
>     ~~~json
>     "_links": {"self": { "href": "http://localhost:8080/..." }}
>     ~~~
>
> * 为整个toco list，添加了 `"_links":{"recents":{...}}``属性
>
>     ~~~json
>     "_links": {"recents": {"href": "http://localhost:8080/design/recent"}}
>     ~~~

### (3) 添加依赖

> ~~~xml
> <dependency>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-hateoas</artifactId>
> </dependency>
> ~~~

### 7.2.1 添加REST API所对应的URL

>  即上面整个taco list的REST API URL

#### (1) 为Taco List生成对应的URL

以先前[RecentTacosController.java](/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java)中的recentTacos为例

> ~~~java
> @GetMapping("/recent")
> public Iterable<Taco> recentTacos() {
> 	PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
> 	return tacoRepo.findAll(page).getContent();
> }
> ~~~

方法

> 用`CollectionModel<EntityModel<Taco>>`来封装`Iterable<Taco>`
>
> ~~~java
> @GetMapping("/recent")
> public CollectionModel<EntityModel<Taco>> recentTacos() {
>     PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
>     List<Taco> tacos = tacoRepo.findAll(page).getContent();
>     // 添加整个taco list的URL
>     CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos); // 后续要改
>     recentResources.add(
>         // (1) 完全hard coding
>         // new Link("http://localhost:8080/design/recent", "recents") 
>         // (2) 部分hard coding
>         // WebMvcLinkBuilder
>         // 	.linkTo(DesignTacoController.class) // 基准url来源
>         // 	.slash("recent")					// 以基准为参照的相对路径，这里仍然有一点hardcoding
>         // 	.withRel("recents")					// 链接名称
>         // (3) 彻底消除hard coding
>         linkTo(
>             methodOn(DesignTacoController.class).recentTacos() // 生成url
>         ).withRel("recents"))								   // 链接名称
>     );
> 	return recentResources;
> }
> ~~~
>
> 使用`WebMvcLinkBuilder`，它可以在`DesignTacoController`的`base url`基础上 、添加一个`recent`来生成新的url
>
> ~~~json
> "_links": {"recents": {"href": "http://localhost:8080/design/recent"}}
> ~~~

### 7.2.2 创建资源装配器

#### (1) 为Taco对象生成URL

> ~~~java
> CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos); // 后续要改
> ~~~
>
> 上一小节的代码只能为taco list添加URL，无法为封装在内部的Taco和Ingredient添加URL，解决方法如下：

步骤1：编写`TacoEntityModel`以生成和封装`Taco`的URL

> 代码：[/tacocloud-api/src/main/java/tacos/web/api/TacoEntityModel.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/TacoEntityModel.java)
>
> ~~~java
> // @Relation(value="taco", collectionRelation="tacos") // 7.2.3小节启用并介绍
> public class TacoEntityModel extends EntityModel<TacoEntityModel> {
> 	private static final IngredientEntityModelAssembler
>             ingredientAssembler = new IngredientEntityModelAssembler();
> 	@Getter
> 	private final String name;
> 
> 	@Getter
> 	private final Date createdAt;
> 
> 	@Getter
> 	// private List<Ingredient> ingredients; 
>     private final CollectionModel<IngredientEntityModel> ingredients; //下一小节介绍
> 
> 	public TacoResource(Taco taco) {
> 		this.name = taco.getName();
> 		this.createdAt = taco.getCreatedAt();
> 		// this.ingredients = taco.getIngredients();
>         // 下一小节介绍
>         this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients()); 
> 	}
> }
> ~~~
>
> `TacoEntityModel`类用来代表Resource
>
> * 与Taco非常相似，有name、createdAt、ingredients属性
> * 会存储Taco对应的URL，属性和方法继承自`CollectionModelSupport`
> * 没有包含Taco的id属性，是因为id仅与data base相关不应当暴露给其他代码，而对于UI来说起到ID作用的应当是获取这个resource的url（ID仅用于拼接url）
>
> Resource和Domain Object是否分开
>
> * 这里的代码把Resource和Domain分成了两个类
> * 还有一种做法是让Domain Object继承CollectionModelSupport，
>     * 这样就可以把两个类合二为一
>     * 但也意味着Domain Object拥有了与它无关的URL属性，同时Resource也拥有了它不需要的database ID

步骤2：编写`TacoEntityModelAssembler`来辅助框架把list中的Taco转成`TacoEntityModel`

>  代码：[/tacocloud-api/src/main/java/tacos/web/api/TacoEntityModelAssembler.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/TacoEntityModelAssembler.java)
>
> ~~~java
> public class TacoEntityModelAssembler
>        extends RepresentationModelAssemblerSupport<Taco, TacoEntityModel> {
> 	public TacoEntityModelAssembler() {
>         // 当创建TacoEntityModel对象时会使用DesignTacoController来决定URL的base path
> 		super(DesignTacoController.class, TacoEntityModel.class);
> 	}
> 
>     // 仅被框架用来生成TacoEntityModel对象
> 	@Override
> 	protected TacoEntityModel instantiateModel(Taco taco) {
>         // 用Taco对象（Domain Object）创建TacoEntityModel对象（Resource）
>         // 如果TacoEntityModel有默认构造函数，则该方法只是可选的
>         // 否则则必须override这个方法
> 		return new TacoEntityModel(taco);
> 	}
> 
>     // 除了用来生成TacoEntityModel对象，还会用来生成URL
> 	@Override
> 	public TacoEntityModel toModel(Taco taco) {
>         // 该方法必须override，传入taco的id会被用来生成URL
> 		return createModelWithId(taco.getId(), taco);
> 	}
> }
> ~~~

步骤3：修改先前[RecentTacosController.java](/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java)中的recentTacos，让它能够为Taco也生成URL

> ~~~java
> @GetMapping("/recent")
> public Resources<TacoEntityModel> recentTacos() {
> 	// 从DAO层获得Taco对象（Domain Object）
>     PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
> 	List<Taco> tacos = tacoRepo.findAll(page).getContent();
>     // 将Taco对象（Domain Object）转换成能生成URL的TacoEntityModel对象（Resource）
> 	List<TacoEntityModel> tacoResources = new TacoEntityModelAssembler().toModels(tacos);
>     Resources<TacoEntityModel> recentResources = new Resources<TacoEntityModel>(tacoResources);
>     // 添加整个taco list的URL
> 	recentResources.add(
> 		linkTo(methodOn(DesignTacoController.class).recentTacos()
> 	).withRel("recents"));
>   return recentResources;
> }
> ~~~

此时返回的Json中同时拥有taco list和taco的url，但是还没有ingredient的URL

> ~~~json
> {
> 	"_embedded": {
> 		"tacoResourceList": [
>             {
> 				"name": "Veg-Out",
> 				"createdAt": "2018-01-31T20:15:53.219+0000",
> 				"ingredients": [{
> 						"name": "Flour Tortilla", "type": "WRAP",
> 					},{
> 						"name": "Corn Tortilla", "type": "WRAP",
> 					},
> 					...
> 				],
> 				"_links": {"self": { "href": "http://localhost:8080/design/4" }}
> 			},
> 			...
> 		]
> 	},
> 	"_links": {"recents": {"href": "http://localhost:8080/design/recent"}}
> }
> ~~~

#### (2) 为Ingredient对象生成URL

> 与为Taco生成URL步骤类似、具体如下

步骤1：编写`IngredientEntityModel`以封装`Ingredient`的URL

> 代码：[/tacocloud-api/src/main/java/tacos/web/api/IngredientEntityModel.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/IngredientEntityModel.java)
>
> ~~~java
> public class IngredientEntityModel extends EntityModel<IngredientEntityModel> {
>     // 与TocoEntityModel类似
>     // 包含Domain的其他属性，不包含id属性，但会从基类继承到URL
> 	@Getter
> 	private String name;
> 
> 	@Getter
> 	private Type type;
> 
> 	public IngredientEntityModel(Ingredient ingredient) {
> 		this.name = ingredient.getName();
> 		this.type = ingredient.getType();
> 	}
> }
> ~~~

步骤2：编写`TacoEntityModelAssembler`辅助框架把`Ingredient`转换成`IngredientEntityModel`

> ~~~java
> class IngredientEntityModelAssembler extends
>           RepresentationModelAssemblerSupport<Ingredient, IngredientEntityModel> {
> 	public IngredientEntityModelAssembler() {
>         // 当创建IngredientEntityModel对象时会使用IngredientController来决定URL的base path
> 		super(IngredientController.class, IngredientEntityModel.class);
> 	}
> 
>     // 仅被框架用来生成TacoEntityModel对象
> 	@Override
> 	protected IngredientEntityModel instantiateModel(Ingredient ingredient) {
>         // 用Ingredient对象（Domain Object）创建IngredientEntityModel对象（Resource）
>         // 如果IngredientEntityModel有默认构造函数，则该方法只是可选的
>         // 否则则必须override这个方法
> 		return new IngredientEntityModel(ingredient);
> 	}
> 
>     // 除了用来生成TacoEntityModel对象，还会用来生成URL
> 	@Override
> 	public IngredientEntityModel toModel(Ingredient ingredient) {
>         // 传入的id被用来生成URL
> 		return createModelWithId(ingredient.getId(), ingredient);
> 	}
> }
> ~~~

步骤3：先前的[TacoEntityModel.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/TacoEntityModel.java)不再封装Ingredient对象、改为封装[IngredientEntityModel.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/IngredientEntityModel.java)对象

> 代码见上一小节

此时的REST API的response中将包含taco list、taco、ingredient的URL

> ~~~json
> {
> 	"_embedded": {
> 		"tacoResourceList": [
>             {
> 				"name": "Veg-Out",
> 				"createdAt": "2018-01-31T20:15:53.219+0000",
> 				"ingredients": [{
> 						"name": "Flour Tortilla", "type": "WRAP",
> 						"_links": {"self": { "href": "http://localhost:8080/ingredients/FLTO" }}
> 					},{
> 						"name": "Corn Tortilla", "type": "WRAP",
> 						"_links": {"self": { "href": "http://localhost:8080/ingredients/COTO" }}
> 					},
> 					...
> 				],
> 				"_links": {"self": { "href": "http://localhost:8080/design/4" }}
> 			},
> 			...
> 		]
> 	},
> 	"_links": {"recents": {"href": "http://localhost:8080/design/recent"}}
> }
> ~~~

### 7.2.3 命名嵌套式的关联关系

#### (1) 嵌套关系默认命名（来自于数据类型）的问题

> 上面API Respons JSON中的`"_embedded: { "tacoResourceList": "`命名来自于创建它的数据类型`List<TacoEntityMode>`，如果以后数据类型发生了改变，那么该名称也会发生改变，影响客户端解析返回值
>
> ```java
> @GetMapping("/recent")
> public Resources<TacoEntityModel> recentTacos() {
> 	// 从DAO层获得Taco对象（Domain Object）
>     PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
> 	List<Taco> tacos = tacoRepo.findAll(page).getContent();
>     // 将Taco对象（Domain Object）转换成能生成URL的TacoEntityModel对象（Resource）
> 	List<TacoEntityModel> tacoResources = new TacoEntityModelAssembler().toModels(tacos);
>     Resources<TacoEntityModel> recentResources = new Resources<TacoEntityModel>(tacoResources);
>     // 添加整个taco list的URL
> 	recentResources.add(
> 		linkTo(methodOn(DesignTacoController.class).recentTacos()
> 	).withRel("recents"));
>   return recentResources;
> }
> ```

#### (2) 设置嵌套关系命名

> 解决方法是，为TacoEntityMode增加@Relation注解以命名这种嵌套关系
>
> ~~~java
> // @Relation：嵌套关系命名 
> // 当TacoEntityModel被嵌套在CollectionModel<TacoEntityModel>或者Resources<TacoEntityModel>中时
> // TacoEntityModel被命名为taco，外层的结构被命名为tacos
> @Relation(value="taco", collectionRelation="tacos") 
> public class TacoEntityModel extends EntityModel<TacoEntityModel> {
> 	private static final IngredientEntityModelAssembler
>             ingredientAssembler = new IngredientEntityModelAssembler();
> 	@Getter
> 	private final String name;
> 
> 	@Getter
> 	private final Date createdAt;
> 
> 	@Getter
> 	// private List<Ingredient> ingredients; 
>     private final CollectionModel<IngredientEntityModel> ingredients; //下一小节介绍
> 
> 	public TacoResource(Taco taco) {
> 		this.name = taco.getName();
> 		this.createdAt = taco.getCreatedAt();
> 		// this.ingredients = taco.getIngredients();
>         // 下一小节介绍
>         this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients()); 
> 	}
> }
> ~~~
>
> 命名之后，之前的Response
>
> ~~~json
> {
> 	"_embedded": {
> 		"tacoResourceList": [
> ~~~
>
> 就改成了
>
> ~~~json
> {
> 	"_embedded": {
> 		"tacos": [
> ~~~
>
> 它始终与注解绑定，不依赖于数据类型

## 7.3 使用`Spring Data REST`为repository生成API

### (1) 自动生成API

> 添加依赖
>
> ~~~xml
> <dependency>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-data-rest</artifactId>
> </dependency>
> ~~~
>
> 无需编写代码，框架会为使用Spring Data创建的repository（包括Spring Data JPA，Spring Data Mongo等）自动生成REST API（并且还包含访问嵌套资源的URL），例如：
>
> ~~~bash
> $ curl localhost:8080/ingredients
> {
>   "_embedded" : {
>     "ingredients" : [ {
>       "name" : "Flour Tortilla",
>       "type" : "WRAP",
>       "_links" : {
>         "self" : {
>           "href" : "http://localhost:8080/ingredients/FLTO"
>         },
>         "ingredient" : {
>           "href" : "http://localhost:8080/ingredients/FLTO"
>         }
>       }
>     },
>     ...
>     ]
>   },
>   "_links" : {
>     "self" : {
>       "href" : "http://localhost:8080/ingredients"
>     },
>     "profile" : {
>       "href" : "http://localhost:8080/profile/ingredients"
>     }
>   }
> }
> $ curl http://localhost:8080/ingredients/FLTO
> {
>   "name" : "Flour Tortilla",
>   "type" : "WRAP",
>   "_links" : {
>     "self" : {
>       "href" : "http://localhost:8080/ingredients/FLTO"
>     },
>     "ingredient" : {
>       "href" : "http://localhost:8080/ingredients/FLTO"
>     }
>   }
> }
> ~~~

### (2) 设置API的Base Path

> 通过`spring.data.rest.base-path`配置项来设置，例如
>
> ~~~yml
> spring:
>   data:
>     rest:
>       base-path: /api
> ~~~
>
> 这样所有使用Spring Data REST生成的API，都统一放在了`/api`路径之下，例如
>
> ~~~bash
> $ curl http://localhost:8080/api/ingredients/FLTO
> {
>   "name" : "Flour Tortilla",
>   "type" : "WRAP",
>   "_links" : {
>     "self" : {
>       "href" : "http://localhost:8080/api/ingredients/FLTO"
>     },
>     "ingredient" : {
>       "href" : "http://localhost:8080/api/ingredients/FLTO"
>     }
>   }
> }
> ~~~

### 7.3.1 调整资源路径和嵌套关系名称

#### (1) 获取Spring Data REST生成的API地址

> 向基础路径发送GET请求即可，例如上面把`spring.data.rest.base-path`设为`/api`以后
>
> ~~~bash
> $ curl localhost:8080/api
> {
>   "_links" : {
>     "orders" : {
>       "href" : "http://localhost:8080/api/orders"
>     },
>     "ingredients" : {
>       "href" : "http://localhost:8080/api/ingredients"
>     },
>     "tacoes" : {
>       "href" : "http://localhost:8080/api/tacoes{?page,size,sort}",
>       "templated" : true
>     },
>     "users" : {
>       "href" : "http://localhost:8080/api/users"
>     },
>     "profile" : {
>       "href" : "http://localhost:8080/api/profile"
>     }
>   }
> }
> ~~~

#### (2) 修改资源路径和资源关系名称

> 上面的API地址，是根据实体类类型名称的复数来生成的，其中的"tacoes"拼写错误，正确的拼写应当是“taco”，如何纠正这种错误？或者自定义API的URL呢？
>
> ~~~java
> @Data
> @Entity
> @RestResource(rel="tacos", path="tacos")
> public class Taco {
> 	...
> }
> ~~~
>
> 在实体类上使用@RestResource注解即可，修改后API地址也变为
>
> ~~~json
> "tacos" : {
>   "href" : "http://localhost:8080/api/tacos{?page,size,sort}",
>   "templated" : true
> },
> ~~~

### 7.3.2 分页和排序

#### (1) 分页

> 对于`home resource`，Spring Data REST会为自动生成的API加上page和size参数，使用方法如下
>
> ~~~bash
> $ curl "localhost:8080/api/tacos?size=5"
> ~~~
>
> ~~~bash
> $ curl "localhost:8080/api/tacos?size=5&page=1" # page从0开始
> ~~~
>
> 为了减少客户端手动拼接url的情况，HATEOAS会在Response的“_links"字段中携带相关的url以用于翻页等操作
>
> ~~~json
> "_links" : {
>   "first" : {
>     "href" : "http://localhost:8080/api/tacos?page=0&size=5"
>   },
>   "self" : {
>     "href" : "http://localhost:8080/api/tacos"
>   },
>   "next" : {
>     "href" : "http://localhost:8080/api/tacos?page=1&size=5"
>   },
>   "last" : {
>     "href" : "http://localhost:8080/api/tacos?page=2&size=5"
>   },
>   "profile" : {
>     "href" : "http://localhost:8080/api/profile/tacos"
>   },
>   "recents" : {
>     "href" : "http://localhost:8080/api/tacos/recent"
>   }
> }
> ~~~

#### (2) 排序

> 使用url参数`sort`
>
> 例如下面的API URL返回了”最近新建的taco“
>
> ~~~bash
> $ curl "localhost:8080/api/tacos?sort=createdAt,desc&page=0&size=12"
> ~~~
>
> 但这样的URL暴露了太多内部信息给Client，增加了系统间的耦合和脆弱性，同时也不如`/design/recent`这样的路径更加简洁

### 7.3.3 添加自定义的REST API端点

> Spring Data REST提供的端点都是以执行CRUD操作为主，需要在@RestController中编写自定义API端点

#### (1) 问题

> * @RestController注解bean提的API Base Path，与Spring Data REST的API Base Path是分开配置的，不便于统一更改
> * 自定义Controller端点的Response不会自动包含超链接，客户端无法通过资源关系发现自定义端点

#### (2) 解决Base Path配置不统一的问题

> 使用@RepositoryRestController来替换@RestController，这样Controller的Base Path就会与Spring Data REST保持一致，都以`spring.data.rest.base-path`为准。例子如下：
>
> 代码：[/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java](../ch07/tacocloud-api/src/main/java/tacos/web/api/RecentTacosController.java)
>
> ~~~java
> @RepositoryRestController // 使用Spring Data REST的base path
> public class RecentTacosController {
> 	// 注入repository
> 	private TacoRepository tacoRepo;
> 	public RecentTacosController(TacoRepository tacoRepo) {
> 		this.tacoRepo = tacoRepo;
> 	}
> 
> 	@GetMapping(path="/tacos/recent", produces="application/hal+json")
> 	public ResponseEntity<CollectionModel<TacoEntityModel>> recentTacos() {
> 		// 从DAO获取tacos，同时也演示了Spring Data JPA分页器的使用
>         PageRequest page = PageRequest.of(
>                           0, 12, Sort.by("createdAt").descending());
> 		List<Taco> tacos = tacoRepo.findAll(page).getContent();
> 		// 为返回的数据添加URL连接 
> 		CollectionModel<TacoEntityModel> tacoResources =
> 				new TacoEntityModelAssembler().toCollectionModel(tacos);
> 		CollectionModel<TacoEntityModel> recentResources =
> 				new CollectionModel<TacoEntityModel>(tacoResources);
> 		recentResources.add(
> 			linkTo(methodOn(RecentTacosController.class).recentTacos())
>             .withRel("recents"));
>         // 使用@RepositoryRestController注解时，不会默认返回Json，需要
>         // 方法1：用@ResponseBody注解该方法
>         // 方法2：用ResponseEntity包裹返回结果
> 		return new ResponseEntity<>(recentResources, HttpStatus.OK);
> 	}
> }
> ~~~
>
> 虽然`/{spring.data.rest.base-path}/tacos/recent`的返回结果中，包含了其他相关API的url，但是这个url不会包含在其他的API中，这个问题在下一小节解决

### 7.3.4 为Spring Data端点添加自定义的超链接

> 声明`ResourceProcessor Bean`，可以向Spring Data REST自动生成的URL列表中添加新的URL
>
> 例如：
>
> ~~~java
> // Spring HATEOAS会发现这类RepresentationModelProcessor bean
> @Bean
> public RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>tacoProcessor(EntityLinks links) {
> 	return new RepresentationModelProcessor<PagedModel<EntityModel<Taco>>>() {
>         // PagedModel<EntityModel<Taco>>
>         // * 是Spring Data REST生成的controller返回的数据类型
>         // * 对应的路径是`/{spring.data.rest.base-path}/tacos`
> 		@Override
> 		public PagedModel<EntityModel<Taco>> process(PagedModel<EntityModel<Taco>> resource) {
>             // 调用`/{spring.data.rest.base-path}/tacos/`时
>             // 返回结果的"_link"字段中会增加一个连接`/{spring.data.rest.base-path}/tacos/recent`，对应的关系名为"recents"
>             resource.add(
> 				links.linkFor(Taco.class).slash("recent")
>                .withRel("recents"));
> 			return resource;
> 		}
> 	};
> }
> ~~~

## 7.4 小结

> 略 