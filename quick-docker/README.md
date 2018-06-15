![DOCKER.png](http://upload-images.jianshu.io/upload_images/3167229-9a4b70bb7a4dd06b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


> 准备工作

- 会一点springboot
不会没关系，花十几分钟补一下[Quick-SpringBoot](https://github.com/vector4wang/spring-boot-quick)
- 会一点Maven
不会没关系，花几分钟补一下[Maven的快速应用](http://blog.csdn.net/column/details/16112.html)
- 会一点Linux命令
不会没关系，花十几分再补一下[Linux菜鸟教程](http://www.runoob.com/linux/linux-tutorial.html)
- 一台联网的Centos机器

> 安装Docker

进入Centos终端，执行命令
```bash
yum install docker
```
静待一会，等待安装成功，之后再执行
```bash
systemctl start docker
```
然后查看安装的docker版本
```bash
[root@iZ2zejaebtdc3kosrltpqaZ ~]# docker version
Client:
 Version:         1.12.6
 API version:     1.24
 Package version: docker-1.12.6-68.gitec8512b.el7.centos.x86_64
 Go version:      go1.8.3
 Git commit:      ec8512b/1.12.6
 Built:           Mon Dec 11 16:08:42 2017
 OS/Arch:         linux/amd64

Server:
 Version:         1.12.6
 API version:     1.24
 Package version: docker-1.12.6-68.gitec8512b.el7.centos.x86_64
 Go version:      go1.8.3
 Git commit:      ec8512b/1.12.6
 Built:           Mon Dec 11 16:08:42 2017
 OS/Arch:         linux/amd64
```
> 准备Springboot项目

简单的创建一个工程，实现一个接口即可，然后在pom中添加docker插件，相关代码如下
```java
@SpringBootApplication
@RestController
public class DockerApplication {
    @RequestMapping("/hello")
    public String hello() {
        return "<h1>Hello Spring-Boot Maven Docker</h1>";
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class);
    }
}
```
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>docker-maven-plugin</artifactId>
            <version>0.4.3</version>
            <configuration>
                <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
                <dockerDirectory>src/main/docker</dockerDirectory>
                <resources>
                    <resource>
                        <targetPath>/</targetPath>
                        <directory>${project.build.directory}</directory>
                        <include>${project.build.finalName}.jar</include>
                    </resource>
                </resources>
            </configuration>
        </plugin>
    </plugins>
</build>
```
再创建一个Dockerfile，**注意里面的`ADD xxx.jar`是你使用maven打包之后的jar包的名称，其他的不变**
```bash
FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD quick-docker-1.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
最终的目录结构如下
```bash
.
├── pom.xml
└── src
    └── main
        ├── docker
        │   └── Dockerfile
        ├── java
        │   └── com
        │       └── docker
        │           └── DockerApplication.java
        └── resources
            └── application.properties
```
你也可以直接下载源码https://github.com/vector4wang/spring-boot-quick/tree/master/quick-docker
运行的效果如下
[![1.png](http://upload-images.jianshu.io/upload_images/3167229-61d8c78770673536.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60a90700a46.png)

> 使用Docker部署服务

将src文件和pom放在任意文件夹下，执行命令
```bash
mvn package docker:build
```
在打包的过程中比以往多了以下几步
```log
[INFO] Building image vector4wang/quick-docker
Step 1 : FROM frolvlad/alpine-oraclejdk8:slim
Trying to pull repository docker.io/frolvlad/alpine-oraclejdk8 ... 
Pulling from docker.io/frolvlad/alpine-oraclejdk8
ff3a5c916c92: Pull complete 
51d82ddde372: Pull complete 
31b0b3ea6b23: Pull complete 
Digest: sha256:52864f95ade9d007fb439f9d396742a104dc1067d32b6837caf6df1e0f1a5dc1
 ---> 7d711dabe19e
Step 2 : VOLUME /tmp
 ---> Running in 04ce9f4dce7d
 ---> f46d6639a6f3
Removing intermediate container 04ce9f4dce7d
Step 3 : ADD quick-docker-1.0-SNAPSHOT.jar app.jar
 ---> 1540566b402c
Removing intermediate container e8139d4f64e5
Step 4 : RUN sh -c 'touch /app.jar'
 ---> Running in ae7be1b36370
 ---> 49a6d6c256ac
Removing intermediate container ae7be1b36370
Step 5 : ENV JAVA_OPTS ""
 ---> Running in ea06fa1e72ff
 ---> af945e1fd8b3
Removing intermediate container ea06fa1e72ff
Step 6 : ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
 ---> Running in a1b9bed3a100
 ---> d1dcc0ee4b44
Removing intermediate container a1b9bed3a100
Successfully built d1dcc0ee4b44
[INFO] Built vector4wang/quick-docker
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 36.315 s
[INFO] Finished at: 2018-01-18T21:58:53+08:00
[INFO] Final Memory: 32M/77M
[INFO] ------------------------------------------------------------------------
[root@iZ2zejaebtdc3kosrltpqaZ code]# 
```
> Docker启动服务

先来看下服务部署完之后，docker里多了些什么吧,执行`docker images`
[![2.png](http://upload-images.jianshu.io/upload_images/3167229-10f9e4d48f65b86d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60ab26e3b5b.png)

简单的看到了docker中存在的镜像、标签、镜像ID、已经创建的时间和大小，看下`vector4wang/quick-docker` 这个是在pom中`<imageName>${docker.image.prefix}/${project.artifactId}</imageName>`配置的，比较重要，因为它和接下来要讲的将镜像提交到DockerHub有着密切的联系。
执行`docker run -d -p 8080:8080 vector4wang/quick-docker` 来启动服务。
简单解释下这个命令

- -d 代表后台运行
- -p 标识宿主机与docker服务的端口映射，注意谁前谁后：【**宿主端口：docker内服务端口**】
- vector4wang/quick-docker 就是启动镜像的名称，当然了使用IMAGE ID 也是可以的

好了，看下docker是否将服务启动成功，执行`docker ps`
[![3.png](http://upload-images.jianshu.io/upload_images/3167229-dbce8f9acf01a030.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60ac0d680ea.png)
很顺利，运行成功了

- `CONTAINER ID` 容器ID
- `PORTS`宿主与docker内部的服务映射 
- `NAMES` 容器名称，跟容器ID对应，如果你不指定名称的话，docker会随机给你分配一个name， 名字还是很有意思的
接下来，就是验证的时候了，在浏览器输入对应URL
[![4.png](http://upload-images.jianshu.io/upload_images/3167229-49d4ab8badd306bc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60ad778b7d1.png)
到此，docker部署springboot服务的过程就结束了，接下来就是将自己开发的镜像推送到DockerHub

> DockerHub

推送镜像通俗的说就是类似把代码推送到github一样，这个是把一个完整的应用程序推送到[DockerHub](https://hub.docker.com)，供其他Docker pull与使用。
首先你需要注册一个账号，很简单不再赘述
创建仓库的时候，回头看看pom里的配置，两者有着密切的联系
[![5.png](http://upload-images.jianshu.io/upload_images/3167229-9239c12859480cc2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60afadb4232.png)
这个你可以理解成maven里面的依赖jar包的“坐标”
回到终端，执行`docker login` 按照提示输入用户名和密码
```bash
[root@iZ2zejaebtdc3kosrltpqaZ code]# docker login
Login with your Docker ID to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com to create one.
Username (vector4wang): vector4wang
Password: 
Login Succeeded
[root@iZ2zejaebtdc3kosrltpqaZ code]# 
```
接下来就是推送的了，执行命令` docker push vector4wang/quick-docker:latest`
latest是tag，相当于版本号
包比较大网络传输的比较慢，等了几分钟过程如下(如果失败就多重试几次)
[![6.png](http://upload-images.jianshu.io/upload_images/3167229-62a41613540b250d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60b1310858e.png)
再来看看DockerHub页面
[![7.png](http://upload-images.jianshu.io/upload_images/3167229-ff3a4d6a00f494b1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)](https://i.loli.net/2018/01/18/5a60b1e3e5e61.png)
注意右边红框的代码，意思就是说，在任何docker执行
```bash
docker pull vector4wang/quick-docker
```
就能快速部署并启动一个应用，网络快的话，能达到秒级别的

> 快速部署

现在来感受下docker的强大之处
我把docker所有容器和镜像清空，快速的去部署quick-docker服务
[![演示.gif](http://upload-images.jianshu.io/upload_images/3167229-9a41b47f302cbde7.gif?imageMogr2/auto-orient/strip)](https://i.loli.net/2018/01/18/5a60b4f5eb2e1.gif)
注意看右下角时间，刚刚好一分钟~~~~

> 最后

Docker的强大之处你也见识了，真的是再一次解放了程序员，让程序员不再关心如何去配置项目需要的环境。接下来我会持续记录学习和使用Docker的过程，并从以下几个问题着手：

- 什么是Docker
- Docker常用命令
- Image和Continer两者的关系
- Dockerfile的应用
- 容器的生命周期
- Docker内部服务的监控与性能调优
- 。。。




CSDN：http://blog.csdn.net/qqhjqs?viewmode=list

博客：http://blog.wangxc.club

简书：https://www.jianshu.com/u/223a1314e818

Github:https://github.com/vector4wang

Gitee:https://gitee.com/backwxc

如果感觉有帮助的话，点个赞哦~
