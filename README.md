<div align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.3.4-brightgreen?style=flat-square&logo=spring" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Java-8+-red?style=flat-square&logo=java" alt="Java"/>
  <img src="https://img.shields.io/badge/License-Anti%20996-blue?style=flat-square" alt="License"/>
  <img src="https://img.shields.io/github/stars/vector4wang/spring-boot-quick?style=flat-square&label=Stars" alt="Stars"/>
  <img src="https://img.shields.io/github/forks/vector4wang/spring-boot-quick?style=flat-square&label=Forks" alt="Forks"/>
  
  <h1>🚀 Spring Boot Quick Start</h1>
  <p><strong>Enterprise-Grade Spring Boot Learning Hub</strong></p>
  <p>80+ Production-Ready Modules | Real-World Examples | Cloud-Native Architecture</p>
  
  [中文](#-chinese) • [English](#-english) • [Features](#-features) • [Quick Start](#-quick-start) • [Modules](#-modules) • [Contributing](#-contributing)
</div>

---

## 📖 English

### Introduction

**Spring Boot Quick** is a comprehensive, enterprise-grade learning platform featuring 80+ production-ready modules demonstrating real-world microservice architectures and distributed system patterns using Spring Boot, Dubbo, and Spring Cloud.

Perfect for:
- 🎓 **Students & Junior Developers** - Learn Spring Boot from fundamentals to advanced patterns
- 👨‍💼 **Mid-level Engineers** - Explore microservice architecture and distributed solutions  
- 🏢 **Architects & Tech Leads** - Reference implementation for system design decisions
- 🔍 **Code Reviewers** - Best practices and design patterns showcase

### ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🎯 **80+ Production Modules** | Battle-tested examples covering real-world scenarios |
| 🏗️ **Microservice Architecture** | Dubbo, Spring Cloud, and cloud-native patterns |
| 🗄️ **Multi-Database Support** | MyBatis, JPA, MongoDB, HBase integration |
| 📨 **Message Queue Integration** | RabbitMQ, Kafka, RocketMQ, ActiveMQ examples |
| 🔐 **Enterprise Security** | OAuth2, JWT, Shiro, CAS authentication flows |
| ☁️ **Cloud-Native Ready** | Docker, Kubernetes (K8s/K3s/K3d), container orchestration |
| ⚡ **Performance Optimization** | Caching, async processing, connection pooling (Druid) |
| 🔍 **Monitoring & Observability** | Actuator, health checks, metrics, thread monitoring |
| 🛠️ **Developer Tools** | Swagger, Lombok, Maven Plugins, code generation |
| 📦 **Multi-Environment Deploy** | Profiles, assembly packing, multi-env configuration |

### 🚀 Quick Start

#### Prerequisites
- Java 8+
- Maven 3.6+
- Git

#### Clone & Build
```bash
# Clone repository
git clone https://github.com/vector4wang/spring-boot-quick.git
cd spring-boot-quick

# Build entire project
mvn clean install -U

# Build specific module
cd quick-swagger
mvn clean install

# Run example
mvn spring-boot:run
```

#### Try It Out
```bash
# Run Swagger API documentation server
cd quick-swagger
mvn spring-boot:run
# Visit: http://localhost:8080/swagger-ui.html

# Run MyBatis + Druid demo
cd quick-mybatis-druid
mvn spring-boot:run
# Health check: http://localhost:8080/health

# Run JWT authentication example
cd quick-jwt
mvn spring-boot:run
```

### 🏛️ Project Architecture

```
spring-boot-quick/
├── quick-platform/                    # Foundation & Dependency Management
├── 
├── 📦 Database Layer
│   ├── quick-mybatis-druid/          # MyBatis + Druid Connection Pool
│   ├── quick-jpa/                    # Spring Data JPA
│   ├── quick-multi-data/             # Multi-datasource Configuration
│   ├── quick-mongodb/                # MongoDB Integration
│   └── quick-hbase/                  # HBase Big Data
│
├── 📨 Message Queue & Event Streaming
│   ├── quick-rabbitmq/               # RabbitMQ with Delayed Queues
│   ├── quick-kafka/                  # Apache Kafka
│   ├── quick-activemq/               # Apache ActiveMQ
│   ├── quick-rocketmq/               # Alibaba RocketMQ
│   └── quick-sse/                    # Server-Sent Events (Real-time Push)
│
├── 🔐 Security & Authentication
│   ├── quick-oauth2/                 # OAuth2 Authorization Server
│   ├── quick-jwt/                    # JWT Token Authentication
│   ├── quick-shiro/                  # Apache Shiro Framework
│   └── quick-shiro-cas/              # Shiro + CAS Single Sign-On
│
├── 🌐 Web & API Gateway
│   ├── quick-swagger/                # OpenAPI/Swagger Documentation
│   ├── quick-rest-template/          # HTTP Client Abstraction
│   ├── quick-feign/                  # Declarative HTTP Client
│   └── quick-graphql/                # GraphQL Query Language
│
├── 💾 Caching & Session
│   ├── quick-redis/                  # Redis Distributed Cache
│   └── quick-cache/                  # Spring Cache Abstraction
│
├── 🏗️ Microservice & Distributed
│   ├── quick-dubbo/                  # Dubbo RPC Framework
│   ├── quick-dubbo-nacos/            # Dubbo + Nacos Service Registry
│   ├── quick-zookeeper/              # Zookeeper Coordination
│   └── quick-saturn/                 # Distributed Task Scheduling
│
├── 🛠️ Infrastructure & DevOps
│   ├── quick-container/              # Docker Containerization
│   ├── quick-package-assembly/       # Maven Assembly Packaging
│   ├── quick-config-encrypt/         # Configuration Encryption
│   └── quick-starter/                # Custom Spring Boot Starter
│
└── 📚 Utilities & Tools
    ├── quick-exception/              # Global Exception Handler
    ├── quick-log/                    # Log4j Configuration
    ├── quick-async/                  # Async Processing & Thread Pool
    ├── quick-batch/                  # Spring Batch Processing
    └── quick-vw-crawler/             # Web Crawler Framework
```

### 📦 Core Technology Stack

| Layer | Technologies | Versions |
|-------|---|---|
| **Framework** | Spring Boot | 2.3.4.RELEASE |
| **Cloud** | Spring Cloud Alibaba | 2.2.5.RELEASE |
| **RPC** | Apache Dubbo | 2.7.11 |
| **ORM** | MyBatis, JPA | 3.5.6, latest |
| **Message Queue** | RabbitMQ, Kafka, RocketMQ | - |
| **Cache** | Redis | - |
| **Security** | Shiro, OAuth2, JWT | 1.9.1 |
| **Database** | Druid, MySQL, MongoDB, HBase | - |
| **Container** | Docker, Kubernetes | latest |
| **Tools** | Swagger/OpenAPI, Lombok, Maven | 2.7+ |

### 📚 Documentation

#### Getting Started Guides
- [MyBatis + Druid Setup](./quick-mybatis-druid/README.md)
- [RabbitMQ Configuration](./quick-rabbitmq/README.md)
- [JWT Authentication](./quick-jwt/README.md)
- [Dubbo Microservices](./quick-dubbo/README.md)

#### Advanced Topics
- [Multi-Datasource Architecture](./quick-multi-data/README.md)
- [OAuth2 Authorization Flow](./quick-oauth2/README.md)
- [Kafka Event Streaming](./quick-kafka/README.md)
- [Docker Deployment](./quick-container/README.md)

#### Module Directory
- 🗄️ **Database**: MyBatis, JPA, JDBC, MongoDB, HBase
- 📨 **Message Queue**: RabbitMQ, Kafka, RocketMQ, ActiveMQ
- 🔐 **Security**: OAuth2, JWT, Shiro, CAS
- 🌐 **API**: REST, GraphQL, OpenAPI/Swagger
- 💾 **Cache**: Redis, Spring Cache
- 🏗️ **Microservices**: Dubbo, Spring Cloud, Nacos
- ☁️ **Cloud**: Docker, Kubernetes, K3s
- 🛠️ **DevOps**: CI/CD, Config Management, Monitoring

### 💡 Best Practices

**Code Quality**
- ✅ Clean Architecture patterns
- ✅ SOLID principles demonstration
- ✅ Exception handling best practices
- ✅ Logging standards

**Performance**
- ✅ Connection pooling (Druid)
- ✅ Caching strategies
- ✅ Async processing patterns
- ✅ Database query optimization

**Security**
- ✅ Authentication & Authorization flows
- ✅ Data encryption (MyBatis Crypt Plugin)
- ✅ Configuration encryption
- ✅ Secure API design

**Scalability**
- ✅ Multi-datasource architecture
- ✅ Distributed caching
- ✅ Message queue patterns
- ✅ Microservice decomposition

### ❓ FAQ

**Q: Is this project maintained?**  
A: Yes! While the author primarily uses Go for new projects, this learning repository is actively maintained as a comprehensive Spring Boot reference.

**Q: Can I use this in production?**  
A: These are learning examples. For production, apply security hardening, performance tuning, and requirements-specific modifications.

**Q: Which versions should I use?**  
A: Recommend using `(latest - 1 or 2)` versions for stability. Latest versions may have undiscovered issues.

**Q: How to contribute?**  
A: Fork → Create Feature Branch → Commit → Push → Submit PR. Ensure code compiles and runs correctly.

**Q: Is there Chinese documentation?**  
A: See [中文部分](#-chinese) below.

### 🤝 Contributing

We welcome contributions! Please:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** changes with clear messages (`git commit -m 'Add amazing feature'`)
4. **Push** to branch (`git push origin feature/amazing-feature`)
5. **Submit** a Pull Request

**Contribution Guidelines:**
- Ensure code compiles: `mvn clean install`
- Follow existing code style
- Add documentation for new modules
- Update README if adding significant features
- Test your changes thoroughly

### 📊 Statistics

- ⭐ **2700+ Stars** on GitHub
- 🍴 **927 Forks** 
- 📦 **80+ Modules** covering all enterprise patterns
- 🎓 **9+ Years** of continuous development and improvement

### 📞 Support

- 📖 [Official Blog](http://blog.wangxc.club)
- 🐛 [Issue Tracker](https://github.com/vector4wang/spring-boot-quick/issues)
- 💬 [Discussions](https://github.com/vector4wang/spring-boot-quick/discussions)
- 🔗 [Related Projects](#related-projects)

### 🔗 Related Projects

- [Crawlers Framework](https://github.com/vector4wang/Crawlers) - Web scraping toolkit
- [Spark Processing](https://github.com/vector4wang/quick-spark-process) - Big data examples
- [Elasticsearch Guide](https://github.com/vector4wang/quick-elasticsearch) - Search & analytics

### 📄 License

<div align="center">
  
[![Anti 996](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)
[![Creative Commons](https://img.shields.io/badge/License-Creative%20Commons-DC3D24.svg)](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.en)

</div>

---

## 🇨🇳 Chinese

### 项目介绍

**Spring Boot Quick** 是一个企业级 Spring Boot 学习平台，包含 80+ 个生产级别模块，展示使用 Spring Boot、Dubbo 和 Spring Cloud 构建的真实微服务架构和分布式系统设计模式。

适用人群：
- 🎓 **学生和初级开发者** - 从基础到高阶全面学习 Spring Boot
- 👨‍💼 **中级工程师** - 深入探索微服务架构和分布式解决方案
- 🏢 **架构师和技术负责人** - 系统设计决策的参考实现
- 🔍 **代码审查者** - 最佳实践和设计模式展示

### ✨ 核心特性

| 特性 | 说明 |
|------|------|
| 🎯 **80+ 生产级模块** | 经过验证的真实场景示例 |
| 🏗️ **微服务架构** | Dubbo、Spring Cloud 和云原生模式 |
| 🗄️ **多数据库支持** | MyBatis、JPA、MongoDB、HBase 集成 |
| 📨 **消息队列集成** | RabbitMQ、Kafka、RocketMQ、ActiveMQ 示例 |
| 🔐 **企业级安全** | OAuth2、JWT、Shiro、CAS 认证流程 |
| ☁️ **云原生就绪** | Docker、Kubernetes(K8s/K3s/K3d) 容器编排 |
| ⚡ **性能优化** | 缓存、异步处理、连接池(Druid) |
| 🔍 **监控可观测性** | Actuator、健康检查、指标、线程监控 |
| 🛠️ **开发工具** | Swagger、Lombok、Maven 插件、代码生成 |
| 📦 **多环境部署** | Profiles、assembly 打包、多环境配置 |

### 🚀 快速开始

#### 环境要求
- Java 8+
- Maven 3.6+
- Git

#### 克隆和构建
```bash
# 克隆仓库
git clone https://github.com/vector4wang/spring-boot-quick.git
cd spring-boot-quick

# 构建整个项目
mvn clean install -U

# 构建特定模块
cd quick-swagger
mvn clean install

# 运行示例
mvn spring-boot:run
```

#### 尝试示例
```bash
# 运行 Swagger API 文档服务
cd quick-swagger
mvn spring-boot:run
# 访问: http://localhost:8080/swagger-ui.html

# 运行 MyBatis + Druid 演示
cd quick-mybatis-druid
mvn spring-boot:run
# 健康检查: http://localhost:8080/health

# 运行 JWT 认证示例
cd quick-jwt
mvn spring-boot:run
```

### 🏛️ 项目结构

```
spring-boot-quick/
├── quick-platform/                    # 基础平台和依赖管理
├── 
├── 📦 数据库层
│   ├── quick-mybatis-druid/          # MyBatis + Druid 连接池
│   ├── quick-jpa/                    # Spring Data JPA
│   ├── quick-multi-data/             # 多数据源配置
│   ├── quick-mongodb/                # MongoDB 集成
│   └── quick-hbase/                  # HBase 大数据
│
├── 📨 消息队列与事件流
│   ├── quick-rabbitmq/               # RabbitMQ 延迟队列
│   ├── quick-kafka/                  # Apache Kafka
│   ├── quick-activemq/               # Apache ActiveMQ
│   ├── quick-rocketmq/               # 阿里 RocketMQ
│   └── quick-sse/                    # 服务器推送事件
│
├── 🔐 安全认证
│   ├── quick-oauth2/                 # OAuth2 授权服务器
│   ├── quick-jwt/                    # JWT 令牌认证
│   ├── quick-shiro/                  # Apache Shiro 框架
│   └── quick-shiro-cas/              # Shiro + CAS 单点登录
│
├── 🌐 Web 和 API 网关
│   ├── quick-swagger/                # OpenAPI/Swagger 文档
│   ├── quick-rest-template/          # HTTP 客户端抽象
│   ├── quick-feign/                  # 声明式 HTTP 客户端
│   └── quick-graphql/                # GraphQL 查询语言
│
├── 💾 缓存与会话
│   ├── quick-redis/                  # Redis 分布式缓存
│   └── quick-cache/                  # Spring Cache 抽象
│
├── 🏗️ 微服务与分布式
│   ├── quick-dubbo/                  # Dubbo RPC 框架
│   ├── quick-dubbo-nacos/            # Dubbo + Nacos 服务注册
│   ├── quick-zookeeper/              # Zookeeper 协调服务
│   └── quick-saturn/                 # 分布式任务调度
│
├── 🛠️ 基础设施与运维
│   ├── quick-container/              # Docker 容器化
│   ├── quick-package-assembly/       # Maven Assembly 打包
│   ├── quick-config-encrypt/         # 配置加密
│   └── quick-starter/                # 自定义 Spring Boot Starter
│
└── 📚 工具库
    ├── quick-exception/              # 全局异常处理
    ├── quick-log/                    # Log4j 配置
    ├── quick-async/                  # 异步处理和线程池
    ├── quick-batch/                  # Spring Batch 批处理
    └── quick-vw-crawler/             # 网页爬虫框架
```

### 📦 核心技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| **框架** | Spring Boot | 2.3.4.RELEASE |
| **云平台** | Spring Cloud Alibaba | 2.2.5.RELEASE |
| **RPC** | Apache Dubbo | 2.7.11 |
| **ORM** | MyBatis、JPA | 3.5.6、latest |
| **消息队列** | RabbitMQ、Kafka、RocketMQ | - |
| **缓存** | Redis | - |
| **安全** | Shiro、OAuth2、JWT | 1.9.1 |
| **数据库** | Druid、MySQL、MongoDB、HBase | - |
| **容器** | Docker、Kubernetes | latest |
| **工具** | Swagger/OpenAPI、Lombok、Maven | 2.7+ |

### 📚 文档

#### 入门指南
- [MyBatis + Druid 配置](./quick-mybatis-druid/README.md)
- [RabbitMQ 配置](./quick-rabbitmq/README.md)
- [JWT 认证](./quick-jwt/README.md)
- [Dubbo 微服务](./quick-dubbo/README.md)

#### 高级主题
- [多数据源架构](./quick-multi-data/README.md)
- [OAuth2 授权流程](./quick-oauth2/README.md)
- [Kafka 事件流](./quick-kafka/README.md)
- [Docker 部署](./quick-container/README.md)

#### 模块目录
- 🗄️ **数据库**: MyBatis、JPA、JDBC、MongoDB、HBase
- 📨 **消息队列**: RabbitMQ、Kafka、RocketMQ、ActiveMQ
- 🔐 **安全**: OAuth2、JWT、Shiro、CAS
- 🌐 **API**: REST、GraphQL、OpenAPI/Swagger
- 💾 **缓存**: Redis、Spring Cache
- 🏗️ **微服务**: Dubbo、Spring Cloud、Nacos
- ☁️ **云计算**: Docker、Kubernetes、K3s
- 🛠️ **运维**: CI/CD、配置管理、监控

### 💡 最佳实践

**代码质量**
- ✅ 清晰架构模式
- ✅ SOLID 原则演示
- ✅ 异常处理最佳实践
- ✅ 日志标准规范

**性能优化**
- ✅ 连接池管理 (Druid)
- ✅ 缓存策略
- ✅ 异步处理模式
- ✅ 数据库查询优化

**安全防护**
- ✅ 认证和授权流程
- ✅ 数据加密 (MyBatis 加密插件)
- ✅ 配置加密
- ✅ 安全 API 设计

**可扩展性**
- ✅ 多数据源架构
- ✅ 分布式缓存
- ✅ 消息队列模式
- ✅ 微服务拆分

### ❓ 常见问题

**Q: 这个项目还在维护吗？**  
A: 是的！虽然作者目前主要使用 Go，但本学习库作为 Spring Boot 综合参考资源持续维护。

**Q: 可以用于生产环境吗？**  
A: 这些是学习示例。生产环境需要根据需求进行安全加固、性能调优和功能扩展。

**Q: 应该使用哪个版本？**  
A: 推荐使用 `(最新-1 或 -2)` 版本以获得稳定性。最新版本可能存在未知问题。

**Q: 如何贡献代码？**  
A: Fork → 创建分支 → 提交 → Push → 提交 PR。确保代码能正常编译运行。

**Q: 有中文文档吗？**  
A: 有的，本 README 包含完整中文内容。

### 🤝 贡献指南

欢迎贡献！请按以下步骤操作：

1. **Fork** 本仓库
2. **创建** 特性分支 (`git checkout -b feature/amazing-feature`)
3. **提交** 更改 (`git commit -m 'Add amazing feature'`)
4. **推送** 到分支 (`git push origin feature/amazing-feature`)
5. **提交** Pull Request

**贡献指南：**
- 确保代码能编译: `mvn clean install`
- 遵循现有代码风格
- 为新模块添加文档
- 如添加重要功能请更新 README
- 充分测试你的改动

### 📊 项目统计

- ⭐ **GitHub 2700+ Stars**
- 🍴 **927 个 Forks**
- 📦 **80+ 模块** 覆盖所有企业级模式
- 🎓 **9+ 年** 持续开发和改进

### 📞 获取帮助

- 📖 [官方博客](http://blog.wangxc.club)
- 🐛 [问题反馈](https://github.com/vector4wang/spring-boot-quick/issues)
- 💬 [讨论区](https://github.com/vector4wang/spring-boot-quick/discussions)
- 🔗 [相关项目](#相关项目)

### 🔗 相关项目

- [爬虫框架](https://github.com/vector4wang/Crawlers) - 网页爬虫工具包
- [Spark 处理](https://github.com/vector4wang/quick-spark-process) - 大数据示例
- [Elasticsearch 指南](https://github.com/vector4wang/quick-elasticsearch) - 搜索和分析

### 📄 许可证

<div align="center">
  
[![Anti 996](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)
[![Creative Commons](https://img.shields.io/badge/License-Creative%20Commons-DC3D24.svg)](https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh)

拒绝 996 | Made with ❤️ by the Community

</div>

---

<div align="center">
  
### Star 历史

[![Stargazers over time](https://starchart.cc/vector4wang/spring-boot-quick.svg)](https://starchart.cc/vector4wang/spring-boot-quick)

### 赞助商

<p>如果此项目对您有帮助，请考虑赞助以支持持续开发 ☕</p>

</div>
