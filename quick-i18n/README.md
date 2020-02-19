# 国际化资源在springboot中的使用

### 目录结构
```bash
.
├── README.md
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── quick
    │   │           └── source
    │   │               ├── Application.java
    │   │               ├── config
    │   │               │   └── ResourceBundleConfig.java
    │   │               └── controller
    │   │                   └── ApiController.java
    │   └── resources
    │       ├── application.properties
    │       └── i18n
    │           ├── messages.properties
    │           ├── messages_en.properties
    │           └── messages_zh.properties
    └── test
        └── java

```
留意下资源文件的路径，实在i18n下

### 请求示例

- `localhost:8080/hello?lang=zh_CN`

返回
```bash
操作成功
```

- `localhost:8080/hello?lang=en_US`

返回
```bash
action success
```

### 配置
- 两种配置方式，直接在properties中配置
```text
spring.messages.basename=i18n/messages
spring.messages.encoding=UTF-8
```
- 或者自己创建Bean，在Bean中直接指定
```java
@Bean
public ResourceBundleMessageSource messageSource() {
    Locale.setDefault(Locale.CHINESE);
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasenames("i18n/messages");// name of the resource bundle
    source.setUseCodeAsDefaultMessage(true);
    source.setDefaultEncoding("UTF-8");
    return source;
}
```

### springboot 切换语言可能会报错，记得加上下面Bean的声明

```java
@Bean
public LocaleResolver localeResolver(){
    final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    //final CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("zh", "CN"));
    return localeResolver;
}
```
一定要注意这个bean的名字，是`localeResolver`，也可以这样
`@Bean(name="localeResolver")`