<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!-- **Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH03 使用数据](#ch03-%E4%BD%BF%E7%94%A8%E6%95%B0%E6%8D%AE)
  - [3.1 使用`JDBC Template`](#31-%E4%BD%BF%E7%94%A8jdbc-template)
    - [3.1.1 调整Domain Object以适应持久化](#311-%E8%B0%83%E6%95%B4domain-object%E4%BB%A5%E9%80%82%E5%BA%94%E6%8C%81%E4%B9%85%E5%8C%96)
    - [3.1.2 使用JDBC Template](#312-%E4%BD%BF%E7%94%A8jdbc-template)
      - [(1) 依赖](#1-%E4%BE%9D%E8%B5%96)
      - [(2) 定义JDBC Repository](#2-%E5%AE%9A%E4%B9%89jdbc-repository)
      - [(3) 将Repository注入到Controller中并使用](#3-%E5%B0%86repository%E6%B3%A8%E5%85%A5%E5%88%B0controller%E4%B8%AD%E5%B9%B6%E4%BD%BF%E7%94%A8)
    - [3.1.3 数据库Schema及预加载数据](#313-%E6%95%B0%E6%8D%AE%E5%BA%93schema%E5%8F%8A%E9%A2%84%E5%8A%A0%E8%BD%BD%E6%95%B0%E6%8D%AE)
      - [(1) 项目的数据库Schema](#1-%E9%A1%B9%E7%9B%AE%E7%9A%84%E6%95%B0%E6%8D%AE%E5%BA%93schema)
      - [(2) 自动向H2数据库建表并加载测试数据](#2-%E8%87%AA%E5%8A%A8%E5%90%91h2%E6%95%B0%E6%8D%AE%E5%BA%93%E5%BB%BA%E8%A1%A8%E5%B9%B6%E5%8A%A0%E8%BD%BD%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE)
    - [3.1.4 插入数据](#314-%E6%8F%92%E5%85%A5%E6%95%B0%E6%8D%AE)
      - [(1) 嵌套Domain  Object问题](#1-%E5%B5%8C%E5%A5%97domain--object%E9%97%AE%E9%A2%98)
      - [(2) 解决插入嵌套Domain Object时代码冗余的问题](#2-%E8%A7%A3%E5%86%B3%E6%8F%92%E5%85%A5%E5%B5%8C%E5%A5%97domain-object%E6%97%B6%E4%BB%A3%E7%A0%81%E5%86%97%E4%BD%99%E7%9A%84%E9%97%AE%E9%A2%98)
      - [(3) `@SessionAttributes("order")`和`@ModelAttribute`](#3-sessionattributesorder%E5%92%8Cmodelattribute)
  - [3.2 使用`Spring Data JDBC`](#32-%E4%BD%BF%E7%94%A8spring-data-jdbc)
    - [3.2.1 添加Spring  Data  JDBC到项目中](#321-%E6%B7%BB%E5%8A%A0spring--data--jdbc%E5%88%B0%E9%A1%B9%E7%9B%AE%E4%B8%AD)
      - [(1) pom.xml](#1-pomxml)
    - [3.2.2 定义repository interfaces](#322-%E5%AE%9A%E4%B9%89repository-interfaces)
      - [(1) `CrudRepository<T, ID>`](#1-crudrepositoryt-id)
    - [3.2.3 添加注解用于Domain Object持久化](#323-%E6%B7%BB%E5%8A%A0%E6%B3%A8%E8%A7%A3%E7%94%A8%E4%BA%8Edomain-object%E6%8C%81%E4%B9%85%E5%8C%96)
      - [(1) 注解及例子](#1-%E6%B3%A8%E8%A7%A3%E5%8F%8A%E4%BE%8B%E5%AD%90)
      - [(2) 使用Repository](#2-%E4%BD%BF%E7%94%A8repository)
  - [3.3 使用`Spring Data JPA`](#33-%E4%BD%BF%E7%94%A8spring-data-jpa)
    - [3.3.1 添加`Spring Data JPA`到项目中](#331-%E6%B7%BB%E5%8A%A0spring-data-jpa%E5%88%B0%E9%A1%B9%E7%9B%AE%E4%B8%AD)
      - [(1) 添加依赖及选择不同的JPA实现](#1-%E6%B7%BB%E5%8A%A0%E4%BE%9D%E8%B5%96%E5%8F%8A%E9%80%89%E6%8B%A9%E4%B8%8D%E5%90%8C%E7%9A%84jpa%E5%AE%9E%E7%8E%B0)
    - [3.3.2 将Domain Object标注为实体](#332-%E5%B0%86domain-object%E6%A0%87%E6%B3%A8%E4%B8%BA%E5%AE%9E%E4%BD%93)
      - [(1) 注解和例子](#1-%E6%B3%A8%E8%A7%A3%E5%92%8C%E4%BE%8B%E5%AD%90)
    - [3.3.3 声明JPA repository](#333-%E5%A3%B0%E6%98%8Ejpa-repository)
    - [3.3.4 自定义JPA repository](#334-%E8%87%AA%E5%AE%9A%E4%B9%89jpa-repository)
      - [(1) 方法签名推断](#1-%E6%96%B9%E6%B3%95%E7%AD%BE%E5%90%8D%E6%8E%A8%E6%96%AD)
      - [(2) 用`@Query`注解指定查询内容](#2-%E7%94%A8query%E6%B3%A8%E8%A7%A3%E6%8C%87%E5%AE%9A%E6%9F%A5%E8%AF%A2%E5%86%85%E5%AE%B9)
      - [(3) `Spring Data JPA`对比`Spring Data JDBC`](#3-spring-data-jpa%E5%AF%B9%E6%AF%94spring-data-jdbc)
  - [3.4 小结](#34-%E5%B0%8F%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH03 使用数据

## 3.1 使用`JDBC Template`

项目代码：[../ch03/tacos-jdbctemplate/](../ch03/tacos-jdbctemplate/)

解决的问题

> 用原生的JDBC接口 （例如[RawJdbcIngredientRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/RawJdbcIngredientRepository.java)）需要要执行与Connection、ResultSet、Statement相关的各种操作并处理异常，代码繁琐。Spring通过JdbcTemmplate来简化这类代码的编写（例如 （[IngredientRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/IngredientRepository.java)）

###  3.1.1 调整Domain Object以适应持久化 

> 通常最好有一个字段作为`对象的唯一标识`，另外再添加一个`创建时间`字段 
>
> [/src/main/java/.../Taco.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/Taco.java)
>
> [/src/main/java/.../TacoOrder.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/TacoOrder.java)

### 3.1.2 使用JDBC Template

#### (1) 依赖

> ~~~xml
> <!-- jdbc starter -->
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-jdbc</artifactId>
> </dependency>
> <!-- database connector -->
> <!-- 为了方便演示，使用了H2嵌入式数据库 -->
> <dependency>
>   <groupId>com.h2database</groupId>
>   <artifactId>h2</artifactId>
>   <scope>runtime</scope>
> </dependency>
> ~~~

#### (2) 定义JDBC Repository

> 编写接口：[/src/main/java/.../data/IngredientRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/IngredientRepository.java)
>
> ~~~java
> public interface IngredientRepository {
>       Iterable<Ingredient> findAll();
>       Optional<Ingredient> findById(String id);
>       Ingredient save(Ingredient ingredient);
> }
> ~~~
>
> 编写实现类：[/src/main/java/.../data/JdbcIngredientRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/JdbcIngredientRepository.java)
>
> ```java
> @Repository // 可以被Spring当做DAO来加载到Spring上下文中
> public class JdbcIngredientRepository implements IngredientRepository { 
>        private JdbcTemplate jdbcTemplate;
>     
>        @Autowired //注入JdbcTemplate
>        public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
>           this.jdbcTemplate = jdbcTemplate;
>        }
>    
>        @Override
>        public Optional<Ingredient> findById(String id) {
>           List<Ingredient> results = jdbcTemplate.query(
>               "select id, name, type from Ingredient where id=?",
>               this::mapRowToIngredient, /*用lambda表达式实现函数式接口RowMapper<Ingredient>*/ 
>               id /*与SQL中的?相对应*/);
>           return results.size() == 0 ?  Optional.empty() :  Optional.of(results.get(0));
>        }
> 
>        private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException{
>           return new Ingredient(
>               row.getString("id"),  row.getString("name"),
>               Ingredient.Type.valueOf(row.getString("type")));
>        }
>     
>        @Override
>        public Ingredient save(Ingredient ingredient) {
>           // insert数据的方法: (1)直接用update()方法；(2)使用SimpleJdbcInsert包装器类
>           jdbcTemplate.update(
>              "insert into Ingredient (id, name, type) values (?, ?, ?)",
>              ingredient.getId(),
>              ingredient.getName(),
>              ingredient.getType().toString());
>           return ingredient;
>        }
> }
> ```

####  (3) 将Repository注入到Controller中并使用

> [/src/main/java/.../web/DesignTacoController.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/web/DesignTacoController.java)
>
> ```java
> @Controller
> @RequestMapping("/design")
> @SessionAttributes("tacoOrder")
> public class DesignTacoController {
>         private final IngredientRepository ingredientRepo;
> 
>         @Autowired
>         public DesignTacoController(
>                 IngredientRepository ingredientRepo) {
>             this.ingredientRepo = ingredientRepo;
>         }
>     
>         ...
> }
> ```

###  3.1.3 数据库Schema及预加载数据 

#### (1) 项目的数据库Schema

> ![](https://raw.githubusercontent.com/kenfang119/pics/main/002_spring_boot/spring_in_action_6_ch3_db_schema.jpg)

####  (2) 自动向H2数据库建表并加载测试数据

> [/src/main/resources/schema.sql](../ch03/tacos-jdbctemplate/src/main/resources/schema.sql)
>
> [/src/main/resources/data.sql](../ch03/tacos-jdbctemplate/src/main/resources/data.sql)
>
> 根据命名约定，Spring Boot会在启动时执行resources根目录下的这两个文件，建表并预加载数据库

### 3.1.4 插入数据

> [/src/main/java/.../web/OrderController.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/web/OrderController.java)
>
> [/src/main/java/.../data/OrderRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/OrderRepository.java)
>
> [/src/main/java/.../data/JdbcOrderRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/JdbcOrderRepository.java)

####  (1) 嵌套Domain  Object问题

> 插入数据困难的地方在于，如果插入嵌套Domain Objects，数据会插入到多张表中，并且这些表之间存在 外键依赖，过程会非常复杂，代码维护负担也比较大 。这个问题在小节`3.2. Spring Data JDBC`中解决 
>
> 例如：[/src/main/java/.../data/JdbcOrderRepository.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/data/JdbcOrderRepository.java) 中的`save(TacoOrder)`方法
>
> ```java
> @Override
> @Transactional  // 在一个事务中运行 
> public TacoOrder save(TacoOrder order) {
>     	// Prepared Statement Factory
>     	PreparedStatementCreatorFactory pscf =
>             new PreparedStatementCreatorFactory(
>                     "insert into Taco_Order "
>                             + "(delivery_name, delivery_street, delivery_city, "
>                             + "delivery_state, delivery_zip, cc_number, "
>                             + "cc_expiration, cc_cvv, placed_at) "
>                             + "values (?,?,?,?,?,?,?,?,?)",
>                     Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
>                     Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
>                     Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
>             );
>     pscf.setReturnGeneratedKeys(true); // 返回执行sql后得到的key
> 
>     // Prepared Statement
>     order.setPlacedAt(new Date());
>     PreparedStatementCreator psc =
>             pscf.newPreparedStatementCreator(
>                     Arrays.asList(
>                             order.getDeliveryName(),
>                             order.getDeliveryStreet(),
>                             order.getDeliveryCity(),
>                             order.getDeliveryState(),
>                             order.getDeliveryZip(),
>                             order.getCcNumber(),
>                             order.getCcExpiration(),
>                             order.getCcCVV(),
>                             order.getPlacedAt()));
> 
>     // Key Holder：用来记录insert order时产生的key
>     GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
>     
>     // 执行update操作
>     jdbcOperations.update(psc, keyHolder);
>     
>     // 获取并保存order id
>     long orderId = keyHolder.getKey().longValue();
> 	order.setId(orderId);
> 
>     // 用order id向taco表存入该订单的所有卷饼
>     List<Taco> tacos = order.getTacos();
>     int i = 0;
>     for (Taco taco : tacos) {
>         // 执行另外一个类似的复杂方法（Taco表外联了IngredientRef表）
>         saveTaco(orderId, i++, taco);
>     }
>     return order;
> }
> ```

#### (2) 解决插入嵌套Domain Object时代码冗余的问题

> 使用`SimpleJdbcInsert`（`JdbcTemplate`的封装类）
>
> * 具体参考《Spring In Action 第5版》（即上一版）第3.1小节后半段的介绍
> * 代码参考：[https://github.com/fangkun119/spring-in-action-5-samples/blob/master/ch03/tacos-jdbc/src/main/java/tacos/data/JdbcOrderRepository.java](https://github.com/fangkun119/spring-in-action-5-samples/blob/master/ch03/tacos-jdbc/src/main/java/tacos/data/JdbcOrderRepository.java)

#### (3) `@SessionAttributes("order")`和`@ModelAttribute`

> 代码：[/src/main/java/.../web/DesignTacoController.java](../ch03/tacos-jdbctemplate/src/main/java/tacos/web/DesignTacoController.java)
>
> ~~~java
> @Controller
> @RequestMapping("/design")
> //@SessionAttributes("tacoOrder")让名为”tacoOrder“的model对象保存在session中
> @SessionAttributes("tacoOrder") 
> public class DesignTacoController {
>         // 通过@Autoware注入DAO
>         private final IngredientRepository ingredientRepo;
>         @Autowired
>         public DesignTacoController(IngredientRepository ingredientRepo) {
>             this.ingredientRepo = ingredientRepo;
>         }
> 
>         // @ModelAttribute确保在model中会创建名为tacoOrder的TacoOrder对象
>         @ModelAttribute(name = "tacoOrder")
>         public TacoOrder order() {
>             return new TacoOrder();
>         }
>     
>         @PostMapping
>         public String processTaco(
>                 @Valid Taco taco, Errors errors,
>                 // @ModelAttribute表示该对象来自于model，不需要绑定其他对象
>                 @ModelAttribute TacoOrder tacoOrder, 
>                Model model) {
>             if (errors.hasErrors()) {
>                 return "design";
>             }
>             tacoOrder.addTaco(taco);
>             return "redirect:/orders/current";
>         }
>     
>         ...
> }
> ~~~

## 3.2 使用`Spring Data JDBC`

> `Spring Data JDBC`是`Spring Data`的一个子项目 ，其他的子项目还包括`Spring Data JPA`、`Spring Data MongoDB`、`Spring Data Neo4j`、`Spring Data Redis`、`Spring Data Cassandra`……。这些框架可以自动创建repositories并提供操作接口。

项目代码：[../ch03/tacos-sd-jdbc/](../ch03/tacos-sd-jdbc/)

### 3.2.1 添加Spring  Data  JDBC到项目中

#### (1) [pom.xml](../ch03/tacos-sd-jdbc/pom.xml)

> 添加依赖`spring-boot-starter-data-jdbc`并删除`spring-boot-starter-jdbc`依赖（已不再需要）
>
> ~~~xml
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-data-jdbc</artifactId>
> </dependency>
> ~~~
>
> 同时不要忘记添加jdbc connector的编译依赖，例子中沿用了H2嵌入式数据库的connector

### 3.2.2 定义repository interfaces

> * [/src/main/java/.../data/IngredientRepository.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/data/IngredientRepository.java)
>
> * [/src/main/java/.../data/OrderRepository.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/data/OrderRepository.java)

#### (1) `CrudRepository<T, ID>`

> 只需要声明Repository接口并让它继承CrudRepository即可 ，框架会自动生成Repository的实现，因此不再需要为其编写实现类
>
> ```java
> // 第二个泛型参数对应于TacoOrder的id字段的类型（使用@Id注解）
> public interface OrderRepository extends CrudRepository<TacoOrder, Long> {}
> ```
> 
>CrudRepository继承自Repository接口，为其添加了如下方法
> 
>```java
> @NoRepositoryBean
> public interface CrudRepository<T, ID> extends Repository<T, ID> {
>      <S extends T> S save(S var1);
>         <S extends T> Iterable<S> saveAll(Iterable<S> var1);
>         Optional<T> findById(ID var1);
>         boolean existsById(ID var1);
>         Iterable<T> findAll();
>         Iterable<T> findAllById(Iterable<ID> var1);
>         long count();
>         void deleteById(ID var1);
>         void delete(T var1);
>         void deleteAll(Iterable<? extends T> var1);
>         void deleteAll();
>    }
> ```

### 3.2.3 添加注解用于Domain Object持久化

> 除了声明Repository接口，还需要为Domain添加注解，以便让Spring Data JDBC知道如何持久化他们
>
> [/src/main/java/.../Taco.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/Taco.java)
>
> [/src/main/java/.../TacoOrder.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/TacoOrder.java)
>
> [/src/main/java/.../Ingredient.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/Ingredient.java)
>
> [/src/main/java/.../Ingredient.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/Ingredient.java)

####  (1) 注解及例子

> ```java
> // @EqualsAndHashCode(exclude = "createdAt")  
> //    Exclude createdAt from equals() method so that tests won't fail trying to
> //    compare java.util.D ate with java.sql.Timestamp (even though they're essentially
> //    equal). Need to figure out a better way than this, but excluding this property
> //    for now.
> 
> @Data  // lombok的注解，用来生成setter/getter/constructor等方法
> @EqualsAndHashCode(exclude = "createdAt") // lambok注解，见顶部的说明
> @Table // 可选注解，用来指定DB表名：默认与类名相同，也可指定其他表例如@Table("Taco_Cloud_Taco")
> public class Taco {
>         // @Id表名该列是CrudRepository<T, ID>中泛型参数ID所对应的property
>         @Id  
>         private Long id;
> 
>         // 所有属性都默认映射到数据库中名称相对应的列（驼峰 -> 小写用下划线分隔单词）
>         // 也可以使用注解指定列名，例如@Column("create_at")
>         private Date createdAt = new Date();
> 
>         // 还可以添加java validation的注解，用于数据校验
>         @NotNull
>         @Size(min = 5, message = "Name must be at least 5 characters long")
>         private String name;
> 
>         @Size(min = 1, message = "You must choose at least 1 ingredient")
>         private List<IngredientRef> ingredients = new ArrayList<>();
> 
>         public void addIngredient(Ingredient taco) {
>             this.ingredients.add(new IngredientRef(taco.getId()));
>         }
> }
> ```
>
> ```java
> // 对应的表名是ingredient_ref，并且该表没有ID列，因此也可以不加注解
> @Data
> public class IngredientRef {
>       private final String ingredient;
> }
> ```

#### (2) 使用Repository

> [/src/main/java/.../web/DesignTacoController.java](../ch03/tacos-sd-jdbc/src/main/java/tacos/web/DesignTacoController.java)
>
> ```java
> @Controller
> @RequestMapping("/design")
> @SessionAttributes("tacoOrder")
> public class DesignTacoController {
>     // 使用@Autowired注入
>        private final IngredientRepository ingredientRepo;
>        @Autowired
>        public DesignTacoController(IngredientRepository ingredientRepo) {
>             this.ingredientRepo = ingredientRepo;
>        }
>        ...
> }
> ```

## 3.3 使用`Spring Data JPA`

> 项目代码：[../ch03/tacos-sd-jpa/](../ch03/tacos-sd-jpa/)

### 3.3.1 添加`Spring Data JPA`到项目中

#### (1) 添加依赖及选择不同的JPA实现

> * 在[pom.xml](../ch03/tacos-sd-jpa/pom.xml)中添加`spring-boot-starter-data-jpa`即可，该starter会使用Hibernate作为实现
>
> * 如果想更换为其他实现，需要将`hibernate-entitymanager`添加到`exclusions`中，并引入其他实现的依赖，例如`eclipselink`
>
> ~~~xml
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-data-jpa</artifactId>
>   <exclusions>
>     <exclusion>
>         <groupId>org.hibernate</groupId>
>         <artifactId>hibernate-core</artifactId>
>     </exclusion>
>   </exclusions>
> </dependency>
> <dependency>
>   <groupId>org.eclipse.persistence</groupId>
>   <artifactId>org.eclipse.persistence.jpa</artifactId>
>   <version>2.7.6</version>
> </dependency>
> ~~~

### 3.3.2 将Domain Object标注为实体

#### (1) 注解和例子

> [/src/main/java/.../Ingredient.java](../ch03/tacos-sd-jpa/src/main/java/tacos/Ingredient.java)
>
> ~~~java
> @Data    // lombok注解，生成get/set/constructor/equal/hashCode等方法
> @Entity  // JPA注解，表名这是一个JPA Entity
> // lombok注解，生成使用所有字段的构造函数
> @AllArgsConstructor 
> // lombok注解，提供JPA需要的无参数构造函数
> // 但为了不被误用，将其设为private
> // 并强制（force）所有字段被初始化为0/null/false
> @NoArgsConstructor(access=AccessLevel.PRIVATE, force=true) 
> public class Ingredient {
>       @Id //数据库table中表示id的列
>       private String id;
>   
>       private String name;
>       private Type type;
>       public static enum Type {
>    		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
>       }
> }
> ~~~
>
> [/src/main/java/.../Taco.java](../ch03/tacos-sd-jpa/src/main/java/tacos/Taco.java)
>
> ~~~java
> @Data
> @Entity
> public class Taco {
>       // 使用Database的ID自增所产生的值
>       @Id
>       @GeneratedValue(strategy = GenerationType.AUTO) 
>       private Long id;
> 
>       ...
> 
>       // 声明表映射关系为@ManyToMany
>       @Size(min=1, message="You must choose at least 1 ingredient")
>       @ManyToMany()
>       private List<Ingredient> ingredients = new ArrayList<>();
> 
>       ...
> }
> ~~~
>
> [/src/main/java/.../TacoOrder.java](../ch03/tacos-sd-jpa/src/main/java/tacos/TacoOrder.java)
>
> ~~~java
> @Data
> @Entity
> public class TacoOrder implements Serializable {
>       ...
>       // 声明表映射关系为@OneToMany
>       @OneToMany(cascade = CascadeType.ALL)
>       private List<Taco> tacos = new ArrayList<>();
> }
> ~~~

###  3.3.3 声明JPA repository

> [/src/main/java/.../data/IngredientRepository.java](../ch03/tacos-sd-jpa/src/main/java/tacos/data/IngredientRepository.java)
> [/src/main/java/.../data/OrderRepository.java](../ch03/tacos-sd-jpa/src/main/java/tacos/data/OrderRepository.java)
>
> ```java
> public interface OrderRepository extends CrudRepository<TacoOrder, Long> {}
> ```

###  3.3.4 自定义JPA repository

>  添加`CrudRepository<T, ID>`接口以外的自定义方法

#### (1) 方法签名推断 

示例

> 按照Spring Data DSL（Domain-Specific Language）的约定来命名方法名称 ，并将其添加到Repository接口中，Spring Data JPA可以根据方法名称推测出该方法要执行的操作，并实现该方法。例如：
>
> ~~~java
> List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);
> List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(
>     					String deliveryZip, Date startDate, Date endDate);
> ~~~

方法签名结构:

> ![](https://raw.githubusercontent.com/kenfang119/pics/main/002_spring_boot/spring_in_action_6_ch3_jpa_dsl_structure.jpg)
>
> * 上图中的"Orders"（订单）是主语，在DSL中是可选的，多数情况下会被忽略，因为返回值`List<TacoOrder>`已经说明了主语的内容
> * 注意3个参数与`By`，`Between`的关系

除了`Equals`和`Between`，对应的位置还可以使用如下关键字来进行条件限定

> - `IsAfter`, `After`, `IsGreaterThan`, `GreaterThan`
> - `IsGreaterThanEqual`, `GreaterThanEqual`
> - `IsBefore`, `Before`, `IsLessThan`, `LessThan`
> - `IsLessThanEqual`, `LessThanEqual`
> - `IsBetween`, `Between`
> - `IsNull`, `Null`
> - `IsNotNull`, `NotNull`
> - `IsIn`, `In`
> - `IsNotIn`, `NotIn`
> - `IsStartingWith`, `StartingWith`, `StartsWith`
> - `IsEndingWith`, `EndingWith`, `EndsWith`
> - `IsContaining`, `Containing`, `Contains`
> - `IsLike`, `Like`
> - `IsNotLike`, `NotLike`
> - `IsTrue`, `True`
> - `IsFalse`, `False`
> - `Is`, `Equals`
> - `IsNot`, `Not`
> - `IgnoringCase`, `IgnoresCase`

如果方法名中`IgnoringCase`和`IgnoresCase`作用在所有字段上 ，可以用`AllIgnoringCase` or `AllIgnoresCase`来简化

> ~~~java
> List<TacoOrder> findByDeliveryToAndDeliveryCityAllIgnoresCase(
>         String deliveryTo, String deliveryCity);
> ~~~

使用`OrderBy`可以排序

> ~~~java
> List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);
> ~~~

#### (2) 用`@Query`注解指定查询内容

> `@Query`注解用于Spring Data JPA
>
> ~~~java
> // 传入的是JPA Query
> @Query("Order o where o.deliveryCity='Seattle'")
> List<TacoOrder> readOrdersDeliveredInSeattle();
> ~~~
>
> 也可以传入Native SQL Query
>
> ```java
> public interface UserRepository extends JpaRepository<User, Long> {
> 	@Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
> 	User findByEmailAddress(String emailAddress);
> }
> ```
>
> 更多内容参考：
>
> [https://github.com/fangkun119/java_proj_ref/blob/master/002_spring_boot/springboot_note/01_spring_boot_demos.md](https://github.com/fangkun119/java_proj_ref/blob/master/002_spring_boot/springboot_note/01_spring_boot_demos.md) （第3小节和第5.2小节）

#### (3) `Spring Data JPA`对比`Spring Data JDBC`

> 有两点差别：
>
> 1. Spring Data JDBC不支持根据方法名推断来添加自定义方法
> 2. Spring Data JDBC只能通过@Query注解来添加自定义方法，但是向@Query注解中传入的不是JPA Query，而是SQL

## 3.4 小结

>  略