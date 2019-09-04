## Hbase简介

HBase是Hadoop的生态系统，是建立在Hadoop文件系统（HDFS）之上的分布式、面向列的数据库，通过利用Hadoop的文件系统提供容错能力。如果你需要进行实时读写或者随机访问大规模的数据集的时候，请考虑使用HBase！

HBase作为Google Bigtable的开源实现，Google Bigtable利用GFS作为其文件存储系统类似，则HBase利用Hadoop HDFS作为其文件存储系统；Google通过运行MapReduce来处理Bigtable中的海量数据，同样，HBase利用Hadoop MapReduce来处理HBase中的海量数据；Google Bigtable利用Chubby作为协同服务，HBase利用Zookeeper作为对应。

### HDFS与HBase比较

|HDFS|HBase|
|---|---|
|HDFS适于存储大容量文件的分布式文件系统。|HBase是建立在HDFS之上的数据库。|
|HDFS不支持快速单独记录查找。|HBase提供在较大的表快速查找|
|HDFS提供了高延迟批量处理;没有批处理概念。|HBase提供了数十亿条记录低延迟访问单个行记录（随机存取）。|
|HDFS提供的数据只能顺序访问。|HBase内部使用哈希表和提供随机接入，并且其存储索引，可将在HDFS文件中的数据进行快速查找。|

### 特征
- 大：一个表可以有数十亿行，上百万列；
- 无模式：每行都有一个可排序的主键和任意多的列，列可以根据需要动态的增加，同一张表中不同的行可以有截然不同的列；
- 面向列：面向列（族）的存储和权限控制，列（族）独立检索；
- 稀疏：空（null）列并不占用存储空间，表可以设计的非常稀疏；
- 数据多版本：每个单元中的数据可以有多个版本，默认情况下版本号自动分配，是单元格插入时的时间戳；
- 数据类型单一：Hbase中的数据都是字符串，没有类型。

### 一些概念

 - RowKey：是Byte array，是表中每条记录的“主键”，方便快速查找，Rowkey的设计非常重要。
 - Column Family：列族，拥有一个名称(string)，包含一个或者多个相关列
 - Column：属于某一个columnfamily，familyName:columnName，每条记录可动态添加
 - Version Number：类型为Long，默认值是系统时间戳，可由用户自定义
 - Value(Cell)：Byte array

## Docker安装步骤

### 拉取镜像

Dockerhub上有很多镜像，找了一个不错的https://hub.docker.com/r/harisekhon/hbase

一般能用到的镜像，这哥们都给整理了https://github.com/HariSekhon/Dockerfiles

`docker pull harisekhon/hbase`

### 启动脚本

```bash
docker run -d -h docker-hbase \
        -p 2181:2181 \
        -p 8080:8080 \
        -p 8085:8085 \
        -p 9090:9090 \
        -p 9000:9000 \
        -p 9095:9095 \
        -p 16000:16000 \
        -p 16010:16010 \
        -p 16201:16201 \
        -p 16301:16301 \
        -p 16020:16020\
        --name hbase \
        harisekhon/hbase

```
简单说一下
```text
-d 表示后台运行
-h 该容器的host为docker-hbase
-p 宿主机端口:容器端口
--name 该容器的名字
```
可以看下[简易教程](https://blog.csdn.net/qqHJQS/column/info/33078)


|节点|端口号|协议|使用|说明|
|:---:|---|---|---|---|
|zookeeper|2181||zkCli.sh-serverzookeeper1:2181|客户端接入|
|zookeeper|2888,3888|||集群内部通讯|
|HDFSNamenode|9000|HDFS|hdfsdfs-lshdfs://namenode1:9000/|客户端接入
|HDFSNamenode|50070|HTTP|http://namenode1:50070/|集群监控|
|HDFSSecondaryNamenode|50090|HTTP|http://namenode1:50090/|secondary监控|
|HDFSDatanode|50010|||客户端接入/其他节点接入|
|HDFSDatanode|50020||||
|HDFSDatanode|50075|HTTP|http://datanode1:50075/|节点监控|
|HBaseMaster|16000||hbase-client-1.x.x.jar|RegionServer接入|
|HBaseMaster|16010|HTTP|http://namenode1:16010/|集群监控|
|HBaseRegionServer|16020|||客户端接入|
|HBaseRegionServer|16030|HTTP|http://datanode1:16030/|节点监控|

hbase对应的端口(harisekhon/hbase 修改了默认端口：)
```text
# Stargate 8080 / 8085
# Thrift 9090 / 9095
# HMaster 16000 / 16010
# RS 16201 / 16301
EXPOSE 2181 8080 8085 9090 9095 16000 16010 16201 16301

```

### 设置host
在服务的host文件中设置如下映射
```text
127.0.0.1 docker-hbase
```

### 界面
http://localhost:16010/master-status


![图示](http://cdn.wangxc.club/20190716203049.png)

可在(Gist)[https://gist.github.com/7163422771a9f1ba64991fc7ac5f7087#file-note-md]上查看

## 常用命令
容器起来后，我们进入容器内部，执行`docker exec -ti e60a300f7749 /bin/bash`
然后再执行`hbase shell`得到如下
```bash
bash-4.4# hbase shell
2019-07-20 08:47:19,876 WARN  [main] util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
HBase Shell
Use "help" to get list of supported commands.
Use "exit" to quit this interactive shell.
For Reference, please visit: http://hbase.apache.org/2.0/book.html#shell
Version 2.1.1, rb60a92d6864ef27295027f5961cb46f9162d7637, Fri Oct 26 19:27:03 PDT 2018
Took 0.0222 seconds
hbase(main):001:0>
```

我们可以使用`help 'create'`的方法查看某命令的使用帮助

- 查看集群状态`status`
- 查看hbase版本`version`
- 查看所有表`list`
- 创建表 `create 't1','[Column Family 1]','[Column Family 2]'`

例如创建一个菜谱表，调味料为一个列族(类)，食材则为另一个列族(类)，脚本为`create 'cookbook','seasoning','ngredients'`

- 查看表的结构`describe 't1'`
- 删除表 
`disable 't1'` 之后 `drop 't1'`
- 检查表是否存在`exists 't1'`
- 获取表中所有数据`scan 't1'`
-获取表中前10行数据`scan 't1',{LIMIT=>10}`
- 获取指定column(ad:pv)的前10行数据`scan 't1',{COLUMNS=>['ad:pv'],LIMIT=>10}`
- 加Filter：如过滤rowkey以“2015”开头的前10行数据 `scan 't1',{FILTER=>"PrefixFilter('2015')",LIMIT=>10}`
- 查询rowkey=001下所有的数据`get 't1','001'`
- 查询rowkey=001下family=ad,column=pv`get 't1','001','ad:pv'` 或者 `get 't1','001',{COLUMN=>'ad:pv'}`
- 添加数据`put <table>,<rowkey>,<family:column>,<value>,<timestamp>`

例如：向’t1’表中添加rowkey=002,family=ad,column=pv,value=1000,时间戳默认
`put 't1','002','ad:pv','1000'`
- 删除rowkey=002的所有数据`deleteall 't1','002'`
- 删除rowkey=002中ad:pv数据`delete 't1','002','ad:pv'`
- 清空表`truncate 't1'`
- 统计行数`count 't1'`
- 查询表t1中的行数，每100条显示一次`count 't1',{INTERVAL=>100}`

## springboot整合hbase 
这里使用的是hbase-client
```bash
.
├── README.md
├── pom.xml
├── quick-hbase.iml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── quick
    │   │           └── hbase
    │   │               ├── HbaseApplication.java
    │   │               └── config
    │   │                   ├── HBaseClient.java
    │   │                   ├── HbaseConfig.java
    │   │                   └── HbaseProperties.java
    │   └── resources
    │       ├── application.yml
    │       └── log4j.properties
    └── test
        └── java
            └── com
                └── quick
                    └── hbase
                        └── config
                            └── HBaseClientTest.java

```

## 相关参考
[w3cSchool](https://www.w3cschool.cn/hbase_doc/hbase_doc-vxnl2k1n.html)

[Hbase原理、基本概念、基本架构](https://cloud.tencent.com/developer/article/1018571)
