#### 集成 CAS

docker安装cas-server

参考: https://apereo.github.io/cas/development/installation/Docker-Installation.html
```docker
docker pull apereo/cas:v5.3.10

docker run  --name cas -p 8443:8443 -p 8442:8080 -d  apereo/cas:v5.3.10 /bin/sh /cas-overlay/bin/run-cas.sh

## 生成ssl证书  其中密钥库命令输入的是changeit
keytool -genkey -alias tomcat -keypass changeit -keyalg RSA -keystore server.keystore
## 复制
docker cp server.keystore cas:/etc/cas/thekeystore

```

访问：https://localhost:8443/cas/login

用户名：casuser
密码：Mellon

## 参照以下网址搭建cas服务端用于验证
https://github.com/willwu1984/springboot-cas-shiro


后续了解流程即可