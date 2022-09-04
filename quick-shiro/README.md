#### 集成 CAS

docker安装cas-server

参考: https://apereo.github.io/cas/development/installation/Docker-Installation.html
```docker
docker pull apereo/cas

docker run  --name cas -p 8443:8443 -p 8442:8080  apereo/cas /bin/sh /cas-overlay/bin/run-cas.sh

## 生成ssl证书  其中密钥库命令输入的是changeit
keytool -genkey -alias tomcat -keypass changeit -keyalg RSA -keystore server.keystore
## 复制
docker cp server.keystore cas:/etc/cas/thekeystore

```

访问：https://localhost:8443/cas/login

用户名：casuser
密码：Mellon

TODO 
待处理：

[x] https://github.com/willwu1984/springboot-cas-shiro

https://www.cnblogs.com/hellxz/p/15768700.html
https://shiro.apache.org/cas.html
https://blog.csdn.net/yixuan_love/article/details/107080671


---
11•授权需要维承 AuthorizingRealm 类，并实现其 doGetAuthorizationInfo方法
22. AuthorizingRea lm 类继承自 AuthenticatingRealm， 但没有实现 AuthenticatingRea lm 中的
    3docetAuthenticationInfo， 所以认证和授权只需要继承 AuthorizingRea lm 就可以了．同时实现他的两个抽象;
    4
51. 为什么使用 MD5 盐值加密：
    62.如何做到：
71) ．在docetAuthenticationInfo 方法返回值创建 Simp leAuthenticationInf• 对象的时候，需要使用
    8 SimpleAuthenticationInfo (principal, credentials, credentialssalt, realmvame)构造司
    9.2)。使用Bytesource .util.bytes(）来计算盐值，
103) . 盐值需要唯一：
     一般使用随机字符串或 user id
114) •使用new SimpleHash (hashAlgorithnName credentials,
     salt, hashIterations)：来计1
     12
     13 1.如何把一个字符串加密为 MD5
     142.替换当前 Rea lm 的credentialsMatche王 屬性， 直接使用 Hashedcredent ial sMatcher 对象，并设置;
     15
     16密码的比对：
     17通过 Authent icatingRealm 的credentialsMatcher 属性来进行的密码的比对！
     18
     191.获取当前的 Subject．调用 Securityutils.getsubject()
     202．测试当前的用户是否己经被认证。即是否己经登录．调用 suboject 的 isAuthenticated ()
213.
若没有被认证，则把用户名和密码封装为 Use rname Passwo rdroken对象
221) ．创建一个表单页面
     232）．把请求提交到 SpringMVC 的 Handler
     243)．获取用户名和密码。
     25 A
     执行沯录
     调田 cihiemtl 的lanin (znthentilrztinnnakenll
     方汁