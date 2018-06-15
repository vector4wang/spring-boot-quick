### Swagger介绍
　　每每get新的技能想分享的时候，按照套路来讲，需要有一个版块将该技能的“前世今生”介绍个遍，但就我接触到完成配置不超过半小时，我觉得让我完完整整的介绍有点太虚了，所以，最好的介绍就是下面的官网
http://swagger.io/

http://swagger.io/irc/ 这个是实时聊天室，刚刚和老外沟通了一番“how are you？fine thk you.and you?”

https://github.com/swagger-api/swagger-core/wiki/Annotations#apimodel 这个是一些注解的api

Swagger有三个模块

- Swagger Editor
- Swagger UI
- Swagger Codegen

　　我使用的是Swagger UI，我个人的理解就是“使用Swagger相关的注解并启动服务后，就可以在对应的页面查看API并**测试**”，先看一下最终的界面
　　
![api.png](http://upload-images.jianshu.io/upload_images/3167229-ec0d13bf3b0c9da5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
接口描述、参数类型、返回示例在线调试都给你搞定了。你还在犹豫什么，赶快checkou代码，试一试吧

### 整合
　　我接着之前的代码的写(可以在我的[GitHub](https://github.com/vector4wang/springbootquick)上浏览，或者直接clone到本地再切换到**api-norms**分支)，这里要说一下，使用Springboot整合Swagger贼JB简单，相比较而言，SpringMVC就比较复杂了，这里暂且不谈（以后可能也不会谈了，自从我使用了Springboot之后，就已经开始抛弃SpringMVC了）

#### maven依赖
老规矩上配置
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>${swagger.version}</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>${swagger.version}</version>
</dependency>
```

#### 添加`Swagger`注解
　　在Application上直接添加`@EnableSwagger2`，注意版本，官网上的版本还没有更新到最新的，最新的在[Github](https://github.com/swagger-api/swagger-ui)上看,配置后的代码
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by wangxc on 2017/3/9.
 */
@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```
可以了，接下来就是描述接口的注解了！在Controller层，做如下配置
```java
package com.quick.api;


import com.quick.po.Address;
import com.quick.utils.BaseResp;
import com.quick.utils.ResultStatus;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/4/12
 * Time: 8:41
 * Description:
 */
@RestController
@RequestMapping("/api")
@Api("springboot整合swagger2") // Swagger UI 对应api的标题描述
public class WebController {

    @ApiOperation("获取地址信息") // 单个接口的描述
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="province",dataType="String",required=true,value="省",defaultValue="广东省"),// 每个参数的类型，名称，数据类型，是否校验，描述，默认值(这些在界面上有展示)
            @ApiImplicitParam(paramType="query",name="area",dataType="String",required=true,value="地区",defaultValue="南山区"),
            @ApiImplicitParam(paramType="query",name="street",dataType="String",required=true,value="街道",defaultValue="桃园路"),
            @ApiImplicitParam(paramType="query",name="num",dataType="String",required=true,value="门牌号",defaultValue="666")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"), // 响应对应编码的描述
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address",method = RequestMethod.POST)
    public BaseResp<Address> getAddressInfo(@RequestParam(value = "province")String province,
                                   @RequestParam(value = "area")String area,
                                   @RequestParam(value = "street")String street,
                                   @RequestParam(value = "num")String num){

        if(StringUtils.isEmpty(province)||StringUtils.isEmpty(area)||StringUtils.isEmpty(street)||StringUtils.isEmpty(num)){
            return new BaseResp(ResultStatus.error_invalid_argument);
        }
        Address address = new Address();
        address.setProvince(province);
        address.setArea(area);
        address.setStreet(street);
        address.setNum(num);
        return new BaseResp(ResultStatus.SUCCESS,address);

    }

    @ApiOperation("获取地址信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="province",dataType="String",required=true,value="省",defaultValue="广东省"),
            @ApiImplicitParam(paramType="query",name="area",dataType="String",required=true,value="地区",defaultValue="南山区"),
            @ApiImplicitParam(paramType="query",name="street",dataType="String",required=true,value="街道",defaultValue="桃园路"),
            @ApiImplicitParam(paramType="query",name="num",dataType="String",required=true,value="门牌号",defaultValue="666")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address/list",method = RequestMethod.POST)
    public BaseResp<List<Address>> getAddressList(@RequestParam(value = "province")String province,
                                             @RequestParam(value = "area")String area,
                                             @RequestParam(value = "street")String street,
                                             @RequestParam(value = "num")String num){

        if(StringUtils.isEmpty(province)||StringUtils.isEmpty(area)||StringUtils.isEmpty(street)||StringUtils.isEmpty(num)){
            return new BaseResp(ResultStatus.error_invalid_argument);
        }
        Address address = new Address();
        address.setProvince(province);
        address.setArea(area);
        address.setStreet(street);
        address.setNum(num);
        List<Address> lists = new ArrayList<>();
        lists.add(address);
        lists.add(address);
        lists.add(address);
        return new BaseResp(ResultStatus.SUCCESS,lists);

    }
}
```

我只是在原来的基础上添加了下面注解

名称|解释
:---|---
@Api()|将类标记为一种Swagger资源。
@ApiOperation()|描述针对特定路径的操作或通常是 http 方法。
@ApiImplicitParams|允许多个 ApiImplicitParam 对象列表的包装。
@ApiImplicitParam|表示 api 操作中的单个参数。
@ApiResponses|允许多个 ApiResponse 对象列表的包装。
@ApiResponse|描述操作的可能响应。

更多的[看这里](https://github.com/swagger-api/swagger-core/wiki/Annotations#apimodel)

就这么简单，一个基本而又强大的API文档就整理好了！

### 启动

正常的启动SpringBoot,你会发现控制台输出了这些内容
```console
2017-05-03 21:42:52,975  INFO ClassOrApiAnnotationResourceGrouping:100 - Group for method getAddressList was springboot整合swagger2
2017-05-03 21:42:52,986  INFO ClassOrApiAnnotationResourceGrouping:100 - Group for method getAddressList was springboot整合swagger2
```
说明Swagger已经成功跑起来了，接下来打开浏览器，输入你链接
**yourdomain/swagger-ui.html**
我的是**http://localhost:8080/swagger-ui.html**

相信你看了界面并四处点点之后，就会对上面注解的含义有了更进一步的了解~

### 后记
　　这里展示的只是Swagger最基本的功能，更多强大的功能如果后面有运用，我会持续更新的。
　　目前我在看api寻找SwaggerUI输入文件的测试，因为我有个接口需要上传文件，等我搞定，再来分享吧！！！
　　

欢迎进入我的[个人博客](https://www.jianshu.com/p/e326914418eb)浏览
