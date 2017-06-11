
### 前言
　　自己很早就想搞一个总的仓库就是将自己平时遇到的和学习到的东西整合在一起，方便后面用的时候快速的查找与使用，之前是通过branch
的方式去整理，感觉不直观，一咬牙就花了点时间将所有的分支整合在一起（之前使用branch方式的兄dei，这里对不住了，你们可以将之前的干掉，重新克隆总的），方便自己也方便大家参考，以下是我的相关博客，有兴趣的
可以去浏览浏览，觉得对自己有点启发或者解决了一些问题，可以点个赞~要是能给个电费，那就再好不过了。

- CSDN http://blog.csdn.net/qqhjqs?viewmode=contents
- 简书 http://www.jianshu.com/u/223a1314e818
- BLOG http://vector4wang.tk

### spring-boot-quick
基于springboot的快速学习示例


### 所有分支介绍

#### quick-crawler 平时的爬虫

- 领英爬虫
- 百度搜索领英种子url爬虫和"随机应变"的处理方式

#### quick-swagger 
web接口的规范化示例


#### quick-thread 
java线程池Executor的示例


#### quick-modules  
springboot 模块化开发示例


#### quick-mybatis-druid
springboot整合mybatis和阿里云的druid监控功能


#### quick-spark 
springboot整合spark示例


#### quick-package-assembly 
maven打包示例包含jar的启动与停止脚本(简单粗暴，慎重考虑再使用)


#### quick-tika 
apache的文本抽取开源框架，整合到springboot中


#### quick-ElasticSearch 
关于es搜索的相关内容
     现在在学习Elasticsearch+Logstash+Kibana 后续会有相关博文、和代码示例~
 
 
#### quick-img2txt 图片与文字转换的程序示例
- 文字转换成图片
- 图片转换为txt，可作为一些程序启动的注释，如佛祖注释，spring启动注释，等等，可以根据你输入的图片制作(背景一定要为白色)，如

前方预警！！！！

![转换前](https://ooo.0o0.ooo/2017/06/11/593c2c1d64882.jpg)
![转换后](https://ooo.0o0.ooo/2017/06/11/593c2a4b4980f.jpg)
-  **验证码识别** 使用easyocr(项目地址)提供的api接口，可以识别验证码，这简直是小虫子的福利，就问你怕不怕!




---

(后面会持续更新)



### 温馨提示
　　如果您自己想在本地跑一跑，可以将其checkout到本地，直接`mvn clean install -U` 就ok了，如果您只想运行某个模块，直接复制配置文件和代码就ok了，如果有对爬虫有相关业务上的需求或其他疑问请发邮件到**vector4wang@qq.com**
