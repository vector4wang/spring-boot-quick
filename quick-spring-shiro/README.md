### 参考链接
- https://zhuanlan.zhihu.com/p/140454269


1、身份认证和鉴权；

认证成功后的信息保存

2、访问之前的拦截；

3、认证之后的拦截；

4、支持 oauth2、session、jwt

5、session 存在
  
### 问题
1、 提示如下
```text
Parameter 0 of method authorizationAttributeSourceAdvisor in org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration required a bean named 'authenticator' that could not be found.
Action:
Consider defining a bean named 'authenticator' in your configuration.
```

排查`SecurityManager`实例话的类型是否为`SessionsSecurityManager`

2、Request method 'GET' not supported

shiroFilterFactoryBean实例化是，设置的setLogin method为post，其他无权限访问的get接口会重定向到setlogin
就会出现此错误；
