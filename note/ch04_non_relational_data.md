<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH04 访问NoSQL数据库](#ch04-%E8%AE%BF%E9%97%AEnosql%E6%95%B0%E6%8D%AE%E5%BA%93)
  - [4.1 使用Cassandra Repositories](#41-%E4%BD%BF%E7%94%A8cassandra-repositories)
      - [(1) Cassandra](#1-cassandra)
      - [(2) Demo项目位置](#2-demo%E9%A1%B9%E7%9B%AE%E4%BD%8D%E7%BD%AE)
    - [4.1.1 引入Spring Data Cassandra](#411-%E5%BC%95%E5%85%A5spring-data-cassandra)
      - [(1) Spring Boot Starter](#1-spring-boot-starter)
      - [(2) 开发环境搭建](#2-%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA)
    - [4.1.2 理解Cansandra数据模型](#412-%E7%90%86%E8%A7%A3cansandra%E6%95%B0%E6%8D%AE%E6%A8%A1%E5%9E%8B)
    - [4.1.3 将Domain Objects映射到Cansandra存储结构](#413-%E5%B0%86domain-objects%E6%98%A0%E5%B0%84%E5%88%B0cansandra%E5%AD%98%E5%82%A8%E7%BB%93%E6%9E%84)
    - [4.1.4 编写Canssandra Repositories](#414-%E7%BC%96%E5%86%99canssandra-repositories)
  - [4.2 编写MongoDB Repositories](#42-%E7%BC%96%E5%86%99mongodb-repositories)
    - [4.2.1 引入Spring Data MongoDB](#421-%E5%BC%95%E5%85%A5spring-data-mongodb)
      - [(1) Spring Data Starter](#1-spring-data-starter)
      - [(2) 搭建开发环境](#2-%E6%90%AD%E5%BB%BA%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83)
      - [(3) 生产环境配置](#3-%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE)
    - [4.2.2 映射Domain Objects到Mondgo DB Documents](#422-%E6%98%A0%E5%B0%84domain-objects%E5%88%B0mondgo-db-documents)
      - [(1) 四个最常用的Spring Data MongoDB注解](#1-%E5%9B%9B%E4%B8%AA%E6%9C%80%E5%B8%B8%E7%94%A8%E7%9A%84spring-data-mongodb%E6%B3%A8%E8%A7%A3)
      - [(2) 例子](#2-%E4%BE%8B%E5%AD%90)
    - [4.2.3 编写MongoDB Repository](#423-%E7%BC%96%E5%86%99mongodb-repository)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

> * 格式形如`1.2.1`的章节序号为原书的章节序号
> * 格式形如`(1)/(2)/(3)`的章节序号为在笔记中展开的内容

# CH04 访问NoSQL数据库

> Spring Data 覆盖多种NoSQL数据库、包括MongoDB, Cassandra, Couchbase, Neo4j, Redis, ……，并且编程模式相近

## 4.1 使用Cassandra Repositories

#### (1) Cassandra

> Cassandra是分布式、高性能、高可用、满足最终一致性、分区列式存储的NoSQL数据库。每一行数据都分散存储在1到多个节点上，同时采用多节点副本避免单点失败。文档地址为[(http://cassandra.apache.org/doc/latest/](http://cassandra.apache.org/doc/latest/)

#### (2) Demo项目位置

> [../ch04/tacos-sd-cassandra/](../ch04/tacos-sd-cassandra/)

### 4.1.1 引入Spring Data Cassandra

#### (1) Spring Boot Starter

> 有两个不同的Spring Boot Starter：一个用于reactive数据持久化（将在后续关于reactive的章节中介绍）；下面是用于non-reactive数据持久化的starter
>
> ~~~xml
> <!-- 用于non-reactive数据持久化的cassandra的starter -->
> <dependency>
>   <groupId>org.springframework.boot</groupId>
>   <artifactId>spring-boot-starter-data-cassandra</artifactId>
> </dependency>
> ~~~

#### (2) 开发环境搭建

> 略

### 4.1.2 理解Cansandra数据模型

> 略

###  4.1.3 将Domain Objects映射到Cansandra存储结构

> 略

### 4.1.4 编写Canssandra Repositories

> 略

## 4.2 编写MongoDB Repositories

> MongoDB是文档数据库（而非关系型数据库），将数据以BSON（Binary Json）的格式存储
>
> Demo项目地址：[../ch04/tacos-sd-mongodb/](../ch04/tacos-sd-mongodb/)

### 4.2.1 引入Spring Data MongoDB

> [/pom.xml](../ch04/tacos-sd-mongodb/pom.xml)

#### (1) Spring Data Starter

> 同样有两个starter：用于reactive的starter在第15章介绍 ；本章使用的是用于non-reactive的starter
>
> ~~~xml
> <dependency>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-data-mongodb</artifactId>
> </dependency>
> ~~~

#### (2) 搭建开发环境

> 方法1：获取 mogodb的docker镜像并在本地启动
>
> ~~~
> $ docker run -p27017:27017 -d mongo:latest
> ~~~
>
> 方法2：使用in-merory embedded Mongo DB （程序重启后数据会丢失）
>
> ~~~xml
> <dependency>
> 	<groupId>de.flapdoodle.embed</groupId>
> 	<artifactId>de.flapdoodle.embed.mongo</artifactId>
> 	<!-- <scope>test</scope> -->
> </dependency>
> ~~~

#### (3) 生产环境配置

> 部署到生产环境，需连接外部的Mongo DB，下面是一个配置例子
>
> ~~~yml
> spring:
>   data:
>     mongodb:
>       host: mongodb.tacocloud.com
>       port: 27018
>       username: tacocloud
>       password: s3cr3tp455w0rd
>       database: tacoclouddb
> ~~~

### 4.2.2 映射Domain Objects到Mondgo DB Documents

#### (1) 四个最常用的Spring Data MongoDB注解

> * @Id：Document ID，可以注解在任何类型的成员变量上，但是如果这个变量的类型是String，就可以使用MongoDB自动生成ID的特性
> * @Document：声明一个Domain Object为Mongo DB Document
> * @Field：指定field name（可选指定order）用于持久化Domain Object的一个属性
> * @Transient：指明一个property不需要持久化
>
> 对于不指定@Field和@Transient的属性，默认它们在Document中的field name与属性名称相同

#### (2) 例子

> [/src/main/java/.../Ingredient.java](../ch04/tacos-sd-mongodb/src/main/java/tacos/Ingredient.java)
>
> ~~~java
> @Data //lambok注解
> @Document //这个Domain Object与一个Mongo DB Document相对应
> @AllArgsConstructor //lambok注解
> @NoArgsConstructor(access=AccessLevel.PRIVATE, force=true) //lambok注解
> public class Ingredient {
>       @Id //文档ID
>       private String id;
>       private String name;
>       private Type type;
>       public static enum Type {
>    		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
>       }
> }
> ~~~
>
> [/src/main/java/.../Taco.java](../ch04/tacos-sd-mongodb/src/main/java/tacos/Taco.java)：Taco只在作为TocoOrder的一个属性时被持久化，因此不需要使用@Document来注解，也不需要一个用@Id注解的成员变量
>
> [/src/main/java/.../TacoOrder.java](../ch04/tacos-sd-mongodb/src/main/java/tacos/TacoOrder.java)：使用@Document注解，同时提供一个用@Id注解的成员变量

### 4.2.3 编写MongoDB Repository

> [/src/main/java/.../data/IngredientRepository.java](../ch04/tacos-sd-mongodb/src/main/java/tacos/data/IngredientRepository.java)
>
> ~~~java
> public interface IngredientRepository
>          extends CrudRepository<Ingredient, String> {
> }
> ~~~
>
> [/src/main/java/.../data/OrderRepository.java](../ch04/tacos-sd-mongodb/src/main/java/tacos/data/OrderRepository.java)
>
> ~~~java
> public interface OrderRepository
>          extends CrudRepository<TacoOrder, String> {
> }
> ~~~
>
> 与之前几种DB所使用的CrudRepository相同 ，除了Domain Object的@Id成员变量的类型不同