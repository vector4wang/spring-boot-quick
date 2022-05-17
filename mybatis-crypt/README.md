### 本地docker启mysql
```bash
docker run --name mysql-server  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
```