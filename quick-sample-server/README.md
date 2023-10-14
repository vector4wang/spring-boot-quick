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

2.1、 修改文件名称，支持动态参数
跟POM增加动态module声明
```xml
    <modules>
        <module>${rootArtifactId}-api</module>
        <module>${rootArtifactId}-server</module>
    </modules>
```
2.2、修改module文件名
```
__rootArtifactId__-api
```

2.3、修改启动文件名和类名
```
__appNameCap__Application.java

public class ${appNameCap}Application {
    public static void main(String[] args) {
        SpringApplication.run(${appNameCap}Application.class);
    }
}
```
2.4、archetype-metadata.xml 修改
```
    <module id="${rootArtifactId}-api" dir="__rootArtifactId__-api" name="${rootArtifactId}-api">

```

2.5、文件中的package需要替换
```
${packageInPathFormat}	 ---> ${package}
```
| Variable               | Meaning                                                                                                                             |   |   |   |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------|---|---|---|
| _rootArtifactId_       | 做文件夹名替换用，例如_rootArtifactId_-dao, 占位符来动态获取父工程的ArtifactId                                                      |   |   |   |
| ${rootArtifactId}      | 它保存用户输入的值作为项目名（maven在用户运行原型时在提示符中询问为artifactid:的值）                                                |   |   |   |
| ${artifactId}          | 如果您的项目由一个模块组成，则此变量的值将与${rootArtifactId}相同，但如果项目包含多个模块，则此变量将由每个模块文件夹中的模块名替换 |   |   |   |
| `${package}            | 用户为项目提供的包，也在用户运行原型时由maven提示                                                                                   |   |   |   |
| ${packageInPathFormat} | 与${package}变量的值相同，但将“.”替换为字符“/”，例如：，对于包com.foo.bar，此变量为com/foo/bar                                      |   |   |   |
| ${groupId}             | 用户为项目提供的groupid，在用户运行原型时由maven提示                                                                                |   |   |   |
| ${version}             | 版本号                                                                                                                              |   |   |   |

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

