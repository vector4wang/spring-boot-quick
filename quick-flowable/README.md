# Floable 快速入门


### 基础准备
使用docker部署flowable [all-in-one](https://hub.docker.com/r/flowable/all-in-one)

```shell
#user: admin
#password: test
docker run -p 8080:8080 -d flowable_aio flowable/all-in-one
```
- Flowable IDM (http://localhost:8080/flowable-idm)
- Flowable Modeler (http://localhost:8080/flowable-modeler)
- Flowable Task (http://localhost:8080/flowable-task)
- Flowable Admin (http://localhost:8080/flowable-admin)

### 测试用例
#### 使用maven引入依赖
```xml
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter</artifactId>
    <version>${flowable.version}</version>
</dependency>
```

#### 初始化表和引擎实例
```java
ProcessEngineConfiguration cfg=new StandaloneProcessEngineConfiguration().setJdbcUrl(
		"jdbc:mysql://127.0.0.1:3306/flowable-sample?characterEncoding=UTF-8").setJdbcUsername("root")
		.setJdbcPassword("123456").setJdbcDriver("com.mysql.cj.jdbc.Driver")
		.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
processEngine=cfg.buildProcessEngine();

```

### 重点
- 每个节点可以动态设置处理人`flowable:assignee="${manager}"`,后续可通过任务查询，查出某些人是否有任务在处理 **待确认**
- 谁可以处理任务，需由业务系统对接自行控制 **待确认**