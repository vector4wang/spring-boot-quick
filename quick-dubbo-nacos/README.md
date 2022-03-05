# springboot dubbo nacos整合记录

[nacos与dubbo是否重叠](https://www.zhihu.com/question/451932038)

### 部署nacos服务
[使用docker快速启动nacos服务](https://hub.docker.com/r/nacos/nacos-server)
```
docker pull nacos/nacos-server
```

```docker
# 参照官方文档，这里使用的 Standalone Mysql
docker-compose -f example/standalone-mysql-5.7.yaml up
```
启动成功访问http://localhost:8848/nacos/ 

用户名和密码都是nacos