# Zookeeper

> https://www.w3cschool.cn/zookeeper/zookeeper_overview.html

### zk集群
使用docker快速构建一个三个节点的zk集群
https://hub.docker.com/_/zookeeper
```yml
version: '3.1'

services:
  zoo1:
    image: zookeeper
    restart: always
    hostname: zoo1
    ports:
      - 2181:2181
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=0.0.0.0:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo2:
    image: zookeeper
    restart: always
    hostname: zoo2
    ports:
      - 2182:2181
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=0.0.0.0:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo3:
    image: zookeeper
    restart: always
    hostname: zoo3
    ports:
      - 2183:2181
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=0.0.0.0:2888:3888;2181

```

`docker-compose -f stack.yml up`

### zkUI
https://github.com/XiaoMi/shepher
使用小米的开源代码作为zk的ui界面

注意：我只使用了web镜像，zk和mysql我都是自己部署的

或者用这个 https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip
推荐

### 其他
https://www.cnblogs.com/zhaoletian/p/13292250.html