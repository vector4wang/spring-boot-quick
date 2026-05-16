## 2026年03月09日更新
已经进入‌Vibe Coding，大家要拥抱AI哈~

---

## 项目状态
此项目作为 Spring Boot 学习示例库仍在持续维护。虽然作者目前主要使用 Go，但本项目提供的学习资料和示例代码依然有参考价值。欢迎大家留言和PR！

> **提示**: 技术更新换代太快，本仓库仅做参考，自己的项目具体使用哪个版本还需谨慎思考。不推荐使用最新的版本，推荐使用(最新-1|2)的版本，会比较稳定。

---

# spring-boot-quick

[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)
[![知识共享协议（CC协议）](https://img.shields.io/badge/License-Creative%20Commons-DC3D24.svg)](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh)
[![Build Status](https://travis-ci.org/vector4wang/spring-boot-quick.svg?branch=master)](https://travis-ci.org/vector4wang/spring-boot-quick)
[![GitHub stars](https://img.shields.io/github/stars/vector4wang/spring-boot-quick.svg?style=flat&label=Star)](https://github.com/vector4wang/spring-boot-quick/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/vector4wang/spring-boot-quick.svg?style=flat&label=Fork)](https://github.com/vector4wang/spring-boot-quick/fork)

## 前言

基于 Spring Boot 框架的快速学习示例库。本项目整合了常见的开源框架和技术栈，提供了实用的代码示例，便于快速查找与使用。

**作者博客**: [![](https://img.shields.io/badge/BLOG-@%E5%86%AC%E4%B8%8E%E6%99%A8-lightgrey.svg)](http://blog.wangxc.club)

---

## 快速开始

### 编译整个项目
```bash
mvn clean install -U
```

### 编译特定模块
```bash
cd quick-mybatis-druid
mvn clean install
```

### 运行特定示例
```bash
cd quick-swagger
mvn spring-boot:run
```

---

## 项目结构

本项目采用多模块 Maven 结构，包含以下几大类模块：

### 📦 基础平台 (Foundation)
- **quick-platform**: 基础依赖管理和版本控制
- **quick-framework**: 框架基础包
- **quick-platform-common**: 平台公共模块
- **quick-platform-component**: 平台组件库

### 🗄️ 数据库相关 (Database)
- **quick-mybatis-druid**: MyBatis + Druid 数据源监控
- **quick-mybatis-spring-boot**: MyBatis 集成示例
- **quick-jpa**: Spring Data JPA 示例
- **quick-jdbc**: 原生 JDBC 示例
- **quick-multi-data**: 多数据源配置示例
- **quick-mongodb**: MongoDB 集成示例
- **quick-hbase**: HBase 集成示例
- **mybatis-crypt-plugin**: MyBatis 字段加解密插件

### 📨 消息队列 (Message Queue)
- **quick-rabbitmq**: RabbitMQ 集成示例
- **quick-multi-rabbitmq**: 多个 RabbitMQ 实例配置
- **quick-kafka**: Kafka 集成示例
- **quick-activemq**: ActiveMQ 集成示例
- **quick-rocketmq**: 阿里云 RocketMQ 示例
- **quick-sse**: 服务端推送事件 (Server-Sent Events)

### 🔐 安全认证 (Security)
- **quick-oauth2**: OAuth2 集成示例
- **quick-jwt**: JWT 令牌认证示例
- **quick-shiro**: Apache Shiro 权限框架
- **quick-shiro-cas**: Shiro + CAS 单点登录
- **quick-spring-shiro**: Spring + Shiro 集成

### 🌐 Web & API (Web Framework)
- **quick-swagger**: Swagger/OpenAPI 文档示例
- **quick-rest-template**: RestTemplate HTTP 请求示例
- **quick-feign**: Feign 声明式 HTTP 客户端
- **quick-okhttp**: OkHttp 网络请求库
- **quick-graphql**: GraphQL API 示例

### 💾 缓存 (Caching)
- **quick-redies**: Redis 缓存集成
- **quick-cache**: Spring Cache 抽象示例

### 🔧 工具库 (Utilities)
- **quick-exception**: 统一异常处理
- **quick-log**: Log4j 日志配置
- **quick-logback**: Logback 日志配置
- **quick-async**: 异步处理和线程池
- **quick-batch**: Spring Batch 批处理
- **quick-lombok**: Lombok 代码生成示例
- **quick-monitor-thread**: 线程监控

### 🏗️ 微服务 (Microservices)
- **quick-dubbo**: Dubbo RPC 框架
- **quick-dubbo-nacos**: Dubbo + Nacos 服务治理
- **quick-config-server**: Spring Cloud Config 中心
- **quick-config-encrypt**: 配置加密

### 🛠️ 开发工具 (Developer Tools)
- **quick-swagger**: Web 接口规范化文档
- **quick-img2txt**: 图片与文字转换
- **quick-vw-crawler**: 网页爬虫示例
- **quick-maven-plugin**: Maven 插件开发示例
- **quick-starter**: Spring Boot Starter 开发示例

### 🐳 部署 (Deployment)
- **quick-container**: Docker 容器化部署
- **quick-package-assembly**: Maven Assembly 打包
- **quick-package-assembly-multi-env**: 多环境分离打包
- **quick-undertow**: Undertow 服务器集成

### 📱 其他示例 (Others)
- **quick-jsx**: JSP 模板示例
- **quick-vue**: Vue.js 前端集成
- **quick-wx-public**: 微信公众号接入示例
- **quick-method-evaluate**: 方法性能评估
- **quick-dynamic-bean**: 动态 Bean 创建
- **quick-sharding-jdbc**: 数据库分片 (ShardingJDBC)
- **quick-flowable**: Flowable 流程引擎
- **quick-camunda**: Camunda 流程引擎
- **quick-saturn**: saturn 分布式任务调度
- **quick-zookeeper**: Zookeeper 分布式协调
- **quick-i18n**: 国际化示例
- **quick-mail**: 邮件发送示例
- **quick-app**: 应用启动器示例
- **quick-liteflow**: LiteFlow 轻量级流程框架

---

## 主要技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.3.4.RELEASE | Web 框架 |
| Spring Cloud Alibaba | 2.2.5.RELEASE | 微服务方案 |
| MyBatis | 3.5.6 | ORM 框架 |
| Dubbo | 2.7.11 | RPC 框架 |
| Redis | - | 缓存存储 |
| RabbitMQ | - | 消息队列 |
| Kafka | - | 消息队列 |
| Druid | 1.1.10 | 数据库连接池 |
| Shiro | 1.9.1 | 权限框架 |
| JWT | - | 令牌认证 |
| GraphQL | - | 查询语言 |

---

## 核心模块详解

### quick-platform (基础平台)
所有模块的父 POM，统一管理依赖版本。包含：
- 统一的 Spring Boot 和相关框架版本
- 常用工具库版本控制
- Maven 插件配置

### quick-mybatis-druid (MyBatis + Druid)
展示如何在 Spring Boot 中集成 MyBatis 和 Druid 数据源监控：
- Druid 连接池配置
- SQL 性能监控
- Spring Boot Actuator 健康检查端点

健康检查端点:
| HTTP方法 | 路径 | 描述 |
|---------|------|------|
| GET | /health | 应用健康状态 |
| GET | /beans | 所有Spring Beans列表 |
| GET | /env | 环境变量 |
| GET | /metrics | 应用指标 |
| POST | /shutdown | 优雅关闭(需启用) |

### quick-swagger (API 文档)
RESTful 接口规范化示例，集成 Swagger 2.7 进行 API 文档生成。

### quick-rabbit (RabbitMQ)
RabbitMQ 集成示例，包含：
- 消息生产者和消费者
- 延迟队列实现
- 死信队列处理

### quick-exception (异常处理)
Spring Boot 统一异常处理方案，统一返回 JSON 格式错误响应。

### quick-jwt (JWT 认证)
基于 JWT 的无状态身份验证实现。

### quick-dubbo (Dubbo RPC)
Dubbo 分布式服务框架集成。

### quick-cache (缓存)
Spring Cache 抽象层集成，支持多种缓存实现。

---

## 常见问题

### Q: 项目还在维护吗?
A: 是的，本项目作为学习示例库持续维护。虽然作者目前主要使用 Go，但这里的学习资料仍有参考价值。

### Q: 可以直接用于生产环境吗?
A: 本项目主要用于学习和参考。生产环境需要根据实际需求进行相应的性能优化、安全加固和功能扩展。

### Q: 哪个版本最稳定?
A: 建议使用 `最新版本 -1 或 -2` 的依赖版本，会比较稳定。最新版本可能存在未知问题。

### Q: 如何贡献代码?
A: 欢迎提交 PR 和 Issue。请确保代码能正常编译和运行。

---

## 相关资源

- 📖 [官方博客](http://blog.wangxc.club)
- 🐛 [问题反馈](https://github.com/vector4wang/spring-boot-quick/issues)
- 🔗 [相关爬虫项目](https://github.com/vector4wang/Crawlers)
- 🔗 [Spark 处理示例](https://github.com/vector4wang/quick-spark-process)
- 🔗 [Elasticsearch 示例](https://github.com/vector4wang/quick-elasticsearch)

---

## 许可证

- [![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE) 拒绝 996
- [![知识共享协议](https://img.shields.io/badge/License-Creative%20Commons-DC3D24.svg)](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh) 创意共享

欢迎 Star、Fork 和 PR 👍
