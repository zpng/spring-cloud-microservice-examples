# spring-cloud-microservice-examples
spring-cloud-microservice-examples

## 说明
  目前该项目实现了 zuul(路由模块), config-server(配置管理), eureka server（服务注册和发现）, zipkin（服务调用追踪）, simple-service,simple-serviceB两个待发现的服务
  simple-ui (一个用angular写的前端页面)
  
  路由功能实现在 cloud-api-gateway 模块，注册到eureka server上面，所有的请求访问 http://localhost:5555, 然后根据路由规则
  ```
zuul.routes.api-a.path: /cloud-simple-service/**
zuul.routes.api-a.serviceId: cloud-simple-service

zuul.routes.api-b.path: /cloud-simple-serviceB/**
zuul.routes.api-b.serviceId: cloud-simple-serviceB
 ```
 分别请求到  注册到eureka server的cloud-simple-service 和 cloud-simple-serviceB服务。
 服务的架构图:
 ![流程图](https://docs.google.com/drawings/d/1kb_2cLW-KcwhWfmu-iburNTCKKuH7HGUEdQCKCZMgZE/pub?w=960&h=720)

---
## 使用指南
  * 先决条件
  本机安装rabbitmq,并启动
  ```
  rabbitmq-server
  ```
  本机安装mysql,并启动且创建dev和test数据库,并分别创建表
  ```
  mysql.server start
  mysql -uroot
    CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
   dev数据库的user表中插入数据
   INSERT INTO `user` VALUES (1,'dev1'),(2,'dev2'),(3,'dev3');
   test数据库的user表中插入数据
   INSERT INTO `user` VALUES (1,'test1'),(2,'test2'),(3,'test3');
  ```

 * 运行各模块
  ```
  cd cloud-api-gateway
  mvn spring-boot:run
  cd cloud-config-server
  mvn spring-boot:run
  cd cloud-eureka-server
  mvn spring-boot:run
  cd cloud-simple-service
  mvn spring-boot:run
  cd cloud-simple-service
  mvn spring-boot:run --server.port=8082  # cloud-simple-service 以8082端口再次启动服务
  cd cloud-simple-ui
  mvn spring-boot:run
  cd cloud-zipkin
  mvn spring-boot:run
  ```
 * 打开浏览器输入网址并浏览效果
 ```
  查看Eureka Server
  http://localhost:8761 #查看eureka
 ```
  ![Eureka Server](https://drive.google.com/uc?id=0BxyRSlBgU-ShX1dEdG5YSi10OEE)

  ---
  ```
  请求simple service, simple service2, simple serviceB
  http://localhost:8081/user  #simple service
  结果:
  [
    {
        id: 1,
        username: "test1"
    },
    {
        id: 2,
        username: "test2"
    },
    {
        id: 3,
        username: "test3"
    }
  ]
  http://localhost:8082/user  #simple service2
  结果:
  [
    {
        id: 1,
        username: "test1"
    },
    {
        id: 2,
        username: "test2"
    },
    {
        id: 3,
        username: "test3"
    }
  ]
  http://localhost:8091/user  #simple serviceB
  结果:
  Result from simpleserviceB
  ```
---
 本项目实现了通过spring-cloud-bus, 传播config-server中config的变化.下面动手验证之.
    1. 下载配置git repository
    ```
    git clone git@github.com:zpng/spring-cloud-config-demo.git
    ```
    根目录下有个cloud-config-repo目录,该目录下有两个文件:
    cloud-simple-service-dev.properties
    cloud-simple-service-test.properties
    分别是cloud-simple-service在 dev和test环境下的配置信息
    cloud-simple-service-dev.properties内容:
    ```
    mysqldb.datasource.url=jdbc\:mysql\://localhost\:3306/test?useUnicode\=true&characterEncoding\=utf-8

    mysqldb.datasource.username=root

    mysqldb.datasource.password=

    logging.level.org.springframework.web:DEBUG

    ```
    cloud-simple-service-test.properties内容:
    ```
    mysqldb.datasource.url=jdbc\:mysql\://localhost\:3306/test?useUnicode\=true&characterEncoding\=utf-8

    mysqldb.datasource.username=root

    mysqldb.datasource.password=

    logging.level.org.springframework.web:DEBUG
    ```

  
