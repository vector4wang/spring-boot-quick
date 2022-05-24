### 本地docker启mysql
```bash
docker run --name mysql-server  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
```


### 参考
- https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers
- https://www.cnblogs.com/felordcn/p/13473844.html

### 注意

mybatis 3.5.6
```
org.apache.ibatis.executor.statement.StatementHandler 无 Statement prepare(Connection connection, Integer transactionTimeout) 方法
```
