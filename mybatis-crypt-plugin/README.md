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


### 接入
#### 1、直接引用
在需要使用加解密的服务里，使用@EnableEncrypt，这里支持自定义密码，如@EnableEncrypt(value = "123")，但是不建议使用

然后在需要加解密的实体或者mapper方法返回对象中增加注解，如下
```java
@Table(name = "user")
@Data
@CryptEntity
public class User {
	。。。
    @Column(name = "to_phone")
    @CryptField
    private String phone;

	。。。
}
```


#### 2、自定义数据源
对于显示自定义SqlSessionFactory的应用来说，需要手动将拦截器增加进去，如
        
```java
@Bean("sqlSessionFactory")
public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapper));
    sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    PageHelper ph = new PageHelper();
    Properties defaults = new Properties();
    defaults.setProperty("returnPageInfo","check");
    ph.setProperties(defaults);
    sqlSessionFactoryBean.getObject().getConfiguration().addInterceptor(ph);

    /**
     * mybatis加解密增加拦截
     */
    Encrypt encrypt = new AesDesDefaultEncrypt();
    sqlSessionFactoryBean.getObject().getConfiguration().addInterceptor(new CryptResultInterceptor(encrypt));
    sqlSessionFactoryBean.getObject().getConfiguration().addInterceptor(new CryptParamInterceptor(encrypt));

    return sqlSessionFactoryBean.getObject();
}
```

原因：在源码的tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration#sqlSessionFactory中，会自动的将Spring容器中所有的mybatis的Interceptor拦截器加入到factory中，所以，如果我们自定义sqlSessionFactory时，就要把插件引入的逻辑重写
```java
@Bean
@ConditionalOnMissingBean
public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(this.properties.getConfigLocation())) {
    factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
    }
    applyConfiguration(factory);
    if (this.properties.getConfigurationProperties() != null) {
    factory.setConfigurationProperties(this.properties.getConfigurationProperties());
    }
    // 自动将spring容器中的插件设置到了factory中
    if (!ObjectUtils.isEmpty(this.interceptors)) {
    factory.setPlugins(this.interceptors);
    }
    if (this.databaseIdProvider != null) {
    factory.setDatabaseIdProvider(this.databaseIdProvider);
    }
    if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
    factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
    }
    if (this.properties.getTypeAliasesSuperType() != null) {
    factory.setTypeAliasesSuperType(this.properties.getTypeAliasesSuperType());
    }
    if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
    factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
    factory.setMapperLocations(this.properties.resolveMapperLocations());
    }
    
    return factory.getObject();
}
```



#### 3、springmvc 配置引入
对于springmvc项目可以这样引入

<plugins>

	<plugin interceptor="com.quick.db.crypt.intercept.CryptResultInterceptor"/>
	<plugin interceptor="com.quick.db.crypt.intercept.CryptParamInterceptor"/>
</plugins>
