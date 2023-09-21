初步完成支持登陆认证和header中的key认证(JWT)
- 实现一个filter、realm和token  HeaderFilter、HeaderRealm和HeaderToken
- 通过对请求进行判断，如果header头包含key，则进行key的校验，不包含则进行登陆认证




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


参考内容：
https://www.w3cschool.cn/shiro/co4m1if2.html
https://juejin.cn/post/6992391181330186270
https://blog.csdn.net/a23452/article/details/125967279

支持多种登录：https://blog.csdn.net/zhourenfei17/article/details/88826911