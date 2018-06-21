# quick-swagger


 - web接口的规范化示例
 - 添加了当传递参数为对象时，如何使用swagger注解
 
 注意:*如果接受参数是已@RequestBody方式并且参数中包含日期类型的参数，可在对应模型的日期属性上面添加`@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")`注解*
 如
```java
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8") // 注意时区的设置
    private Date birthday; // 出生日期
```

注意`BaseResp`里 T 的get|set方法 
[![微信截图_20180621115113.png](https://i.loli.net/2018/06/21/5b2b20cb86eeb.png)](https://i.loli.net/2018/06/21/5b2b20cb86eeb.png)


