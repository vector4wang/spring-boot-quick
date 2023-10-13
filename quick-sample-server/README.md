快速生成服务骨架

https://blog.csdn.net/u011731544/article/details/132322425

#### 创建maven骨架步骤
1、在目录下执行命令
```bash
spring-boot-quick\quick-archetype\mvn archetype:create-from-project
```

2、进入target中的archetype文件执行install
```bash
spring-boot-quick\quick-archetype\target\generated-sources\archetype> mvn install
```

3、执行archetype:crawl命令，用于构建骨架，在本地仓库的根目录生成archetype-catalog.xml骨架配置文件

4、为确认，可查看本仓库`archetype-catalog.xml`中内容是否有当前archetype声明节点，如下：
```xml
<archetype>
  <groupId>com.quick</groupId>
  <artifactId>quick-sample-server-archetype</artifactId>
  <version>1.0-SNAPSHOT</version>
  <description>quick-sample-server</description>
</archetype>
```

