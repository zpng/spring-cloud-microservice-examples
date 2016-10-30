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

