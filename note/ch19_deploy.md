<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
<!--**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*-->

- [CH09 部署](#ch09-%E9%83%A8%E7%BD%B2)
  - [19.1 构建和运行方式](#191-%E6%9E%84%E5%BB%BA%E5%92%8C%E8%BF%90%E8%A1%8C%E6%96%B9%E5%BC%8F)
  - [19.2 构建成可执行Jar文件](#192-%E6%9E%84%E5%BB%BA%E6%88%90%E5%8F%AF%E6%89%A7%E8%A1%8Cjar%E6%96%87%E4%BB%B6)
  - [19.3 构建成WAR包](#193-%E6%9E%84%E5%BB%BA%E6%88%90war%E5%8C%85)
  - [19.4 构建容器镜像](#194-%E6%9E%84%E5%BB%BA%E5%AE%B9%E5%99%A8%E9%95%9C%E5%83%8F)
    - [(1) 构建镜像](#1-%E6%9E%84%E5%BB%BA%E9%95%9C%E5%83%8F)
    - [(2) 创建容器并启动](#2-%E5%88%9B%E5%BB%BA%E5%AE%B9%E5%99%A8%E5%B9%B6%E5%90%AF%E5%8A%A8)
    - [19.4.1 部署到Kubernetes](#1941-%E9%83%A8%E7%BD%B2%E5%88%B0kubernetes)
    - [19.4.2 在Kubernetes集群中实现服务器优雅关闭](#1942-%E5%9C%A8kubernetes%E9%9B%86%E7%BE%A4%E4%B8%AD%E5%AE%9E%E7%8E%B0%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%BC%98%E9%9B%85%E5%85%B3%E9%97%AD)
    - [19.4.3 确保应用程序在Kubernetes集群中的`liveness`以及`rediness`](#1943-%E7%A1%AE%E4%BF%9D%E5%BA%94%E7%94%A8%E7%A8%8B%E5%BA%8F%E5%9C%A8kubernetes%E9%9B%86%E7%BE%A4%E4%B8%AD%E7%9A%84liveness%E4%BB%A5%E5%8F%8Arediness)
  - [19.5 / 6 展望和总结](#195--6-%E5%B1%95%E6%9C%9B%E5%92%8C%E6%80%BB%E7%BB%93)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# CH09 部署

内容来源：[https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-19/v-6](https://livebook.manning.com/book/spring-in-action-sixth-edition/chapter-19/v-6)（限时预览、超过时限后要购买才能继续阅读）

Demo项目：作者尚未上传

## 19.1 构建和运行方式

可以使用多种方式构建和运行Spring Boot应用程序，包括

> - IDE 中直接运行应用程序
> - 使用 Maven`spring-boot:run`或 Gradle`bootRun`从命令行运行
> - 使用 Maven 或 Gradle 生成可执行的 JAR 文件
> - 使用 Maven 或 Gradle 生成可以部署到传统服务器的 WAR 文件
> - 使用 Maven 或 Gradle 生成容器镜像来运行

方法选择：

> * 部署到PaaS云平台（例如Cloud Foundry）：构建成可执行Jar
> * 部署到传统Java服务器（例如Tomcat、WebSphere、WebLogic）：别无选择只能构建成War
> * 部署到Kubernetes：构建成容器镜像

## 19.2 构建成可执行Jar文件

生成Jar文件

> 使用Maven：Jar文件将生成在`target`目录中，命名格式为`<artifactId>-<version>.jar`，取值来自于pom.xml
>
> ~~~bash
> $ mvnw package
> ~~~
>
> 使用Gradle：Jar文件生成在`build/libs`目录中，文件命名取决于settings.gradle文件的`rootProject.name`以及build.gradle文件的`version`
>
> ~~~bash
> $ gradlew build
> ~~~

在本地运行Jar包

> ~~~bash
> $ java -jar tacocloud-0.0.19-SNAPSHOT.jar
> ~~~

将Jar包推送到Cloud Foundry，例如VMWare的Tanzu Application Service上运行

> ~~~bash
> # 具体说明参考原书
> $ cf push tacocloud -p target/tacocloud-0.0.19-SNAPSHOT.jar
> ~~~

将Jar包打包到Docker镜像中，在Docker容器中运行

> Dockerfile
>
> ~~~dockerfile
> FROM adoptopenjdk/openjdk11
> ARG JAR_FILE=target/*.jar
> COPY ${JAR_FILE} app.jar
> ENTRYPOINT ["java","-jar","/tacocloud-0.0.19-SNAPSHOT.jar"]
> ~~~
>
> 创建Docker镜像
>
> ~~~bash
> $ docker build -t tacocloud/tacocloud:0.0.19-SNAPSHOT
> ~~~
>
> 说明：后面的小节会介绍更简单的方法，使用Spring Boot构建插件来创建容器镜像

## 19.3 构建成WAR包

> 略

## 19.4 构建容器镜像

> Spring Boot 2.3.0开始，不用添加任何额外的依赖或配置文件、也不用修改项目，就可以将项目部署成Docker镜像。只需要使用Spring Boot Build Plugins即可。

### (1) 构建镜像

使用Maven

> ~~~bash
> $ mvnw spring-boot:build-image
> ~~~
>
> 镜像名取决于pom.xml中的`<artifactId>`和`<version>`，命名类似"library/tacocloud:0.0.19-SNAPSHOT"

使用Gradle

> ~~~bash
> $ gradlew bootBuildImage
> ~~~
>
> 镜像名称取决于settings.gradle文件的`rootProject.name`以及build.gradle文件的`version`

说名：目前在Apple M1芯片的Mac上构建镜像会有问题，底层Docker实现还存在问题

### (2) 创建容器并启动

使用Docker命令

> ~~~bash
> $ docker run -p8080:8080 library/tacocloud:0.0.19-SNAPSHOT
> ~~~

注意library只是本地的存储库名称，如果推送到Dockerhub等可能会需要更换库名

如果想要在构建镜像时，就让库名与Dockerhub上的库名一致，有两种方法

方法1： 在构建命令中指定，例如：

> 使用Maven时
>
> ~~~bash
> $ mvnw spring-boot:build-image \
>     -Dspring-boot.build-image.imageName=tacocloud/tacocloud:0.0.19-SNAPSHOT
> ~~~
>
> 使用Gradle时
>
> ~~~bash
> $ gradlew bootBuildImage --imageName=tacocloud/tacocloud:0.0.19-SNAPSHOT
> ~~~

方法2：在pom.xml或build.gradle中指定

> 使用Maven时
>
> ~~~xml
> <plugin>
>  <groupId>org.springframework.boot</groupId>
>  <artifactId>spring-boot-maven-plugin</artifactId>
>  <configuration>
>    <image>
>      <name>tacocloud/${project.artifactId}:${project.version}</name>
>    </image>
>  </configuration>
> </plugin>
> ~~~
>
> 使用Gradle时
>
> ~~~javascript
> bootBuildImage {
>   imageName = "habuma/${rootProject.name}:${version}"
> }
> ~~~

这样就可以将其推送到Docker Image Registry了，例如Docker Hub

> ~~~bash
> $ docker push habuma/tacocloud:0.0.19-SNAPSHOT
> ~~~

### 19.4.1 部署到Kubernetes

> TODO

### 19.4.2 在Kubernetes集群中实现服务器优雅关闭

> TODO

### 19.4.3 确保应用程序在Kubernetes集群中的`liveness`以及`rediness`

> TODO

## 19.5 / 6 展望和总结

> 略

