
- 第一个挑战是如何收集大量的数据；
- 第二个挑战是分析收集的数据；


最近在搭建一个简易的基于Flink的实时推荐系统中用到了kafka，就快速的整合了下并记录在此

### 简介

#### 什么是Kafka
Apache Kafka是一个分布式发布 - 订阅消息系统和一个强大的队列，可以处理大量的数据，并使您能够将消息从一个端点传递到另一个端点。 Kafka适合离线和在线消息消费。 Kafka消息保留在磁盘上，并在群集内复制以防止数据丢失。 Kafka构建在ZooKeeper同步服务之上。 它与Apache Storm和Spark非常好地集成，用于实时流式数据分析。

#### 术语

- Broker：Kafka 集群包含一个或多个服务器，这种服务器被称为 broker。

- Topic：每条发布到 Kafka 集群的消息都有一个类别，这个类别被称为 Topic。（物理上不同 Topic 的消息分开存储，逻辑上一个 Topic 的消息虽然保存于一个或多个 broker 上，但用户只需指定消息的 Topic 即可生产或消费数据而不必关心数据存于何处）。

- Partition：Partition 是物理上的概念，每个 Topic 包含一个或多个 Partition。

- Producer：负责发布消息到 Kafka broker。

- Consumer：消息消费者，向 Kafka broker 读取消息的客户端。

- Consumer Group：每个 Consumer 属于一个特定的 Consumer Group（可为每个 Consumer 指定 group name，若不指定 group name 则属于默认的 group）。

- Offset： 消息在partition中的偏移量。每一条消息在partition都有唯一的偏移量，消息者可以指定偏移量来指定要消费的消息。

#### 特性

可扩展性、数据分区、低延迟、处理大量不同消费者

#### 容错
kafka集群，每一个服务器为broker，每一个broker会有多个partition(创建topic后)，假如有三个broker，然后一个topic设置了三个分区，那么每一个broker都会拥有一个分区，然后分区中会产生一个leader，在使用
过程中有消息进来(producer发送数据)，leader则会拿到数据，然后会分发(复制)给其他两个随从，最终会保持每一个分区都会存在该消息(类似ES的集群，也许大部分的集群都是这样设计的) 

#### 消息偏移量

> The Kafka cluster durably persists all published records—whether or not they have been consumed—using a configurable retention period. For example, if the retention policy is set to two days, then for the two days after a record is published, it is available for consumption, after which it will be discarded to free up space. Kafka's performance is effectively constant with respect to data size so storing data for a long time is not a problem.

> In fact, the only metadata retained on a per-consumer basis is the offset or position of that consumer in the log. This offset is controlled by the consumer: normally a consumer will advance its offset linearly as it reads records, but, in fact, since the position is controlled by the consumer it can consume records in any order it likes. For example a consumer can reset to an older offset to reprocess data from the past or skip ahead to the most recent record and start consuming from "now".

https://kafka.apache.org/intro

Kafka集群持久地保留所有已发布的记录 - 无论它们是否已被消耗 - 使用可配置的保留期。例如，如果保留策略设置为两天，则在发布记录后的两天内，它可供使用，之后将被丢弃以释放空间。Kafka的性能在数据大小方面实际上是恒定的，因此长时间存储数据不是问题。

基于每个消费者保留的唯一元数据是该消费者在日志中的偏移或位置。这种偏移由消费者控制：通常消费者在读取记录时会线性地提高其偏移量，但事实上，由于消费者控制位置，它可以按照自己喜欢的任何顺序消费记录。例如，消费者可以重置为较旧的偏移量以重新处理过去的数据，或者跳到最近的记录并从“现在”开始消费。

可查看此[博文](https://zhuanlan.zhihu.com/p/31731892)


### 使用Docker搭建Kafka开发环境


#### download images
```bash
docker pull wurstmeister/zookeeper
docker pull wurstmeister/kafka
docker pull sheepkiller/kafka-manager
```



#### run zookepper
```bash
docker run -d --name zookeeper --publish 2181:2181 \
  --volume /etc/localtime:/etc/localtime \
  --restart=always \
  wurstmeister/zookeeper
```


#### run kafka

```bash
docker run --name kafka \
  -p 9092:9092 \
  --link zookeeper:zookeeper \
  -e KAFKA_ADVERTISED_HOST_NAME=192.168.1.8 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -d  wurstmeister/kafka  
```

#### run kafka manager
```bash
docker run -it --rm -d \
  --link zookeeper:zookeeper \
  -p 9000:9000 \
  -e ZK_HOSTS="zookeeper:2181" \
  -e APPLICATION_SECRET=letmein sheepkiller/kafka-manager
```

web ui in `localhost:9000`

可以再[Gist](https://gist.github.com/vector4wang/5dff5b6e5abcc41f8955d566dedf74e7 )上查看


### SpringBoot整合Kafka
直接看项目
```bash
.
|-- README.md
|-- pom.xml
|-- quick-kafka.iml
`-- src
    |-- main
    |   |-- java
    |   |   `-- com
    |   |       `-- quick
    |   |           `-- kafka
    |   |               |-- KafkaApplication.java
    |   |               |-- config
    |   |               |   |-- KafkaTopicConfiguration.java
    |   |               |   `-- KafkaTopicProperties.java
    |   |               |-- controller
    |   |               |   `-- ApiController.java
    |   |               `-- service
    |   |                   |-- ConsumerService.java
    |   |                   `-- ProducerService.java
    |   `-- resources
    |       |-- application.yml
    |       `-- logback.xml
    `-- test
        `-- java

```

用法很简单，主要是后面参数的配置（具体看个人应用场景了）

#### 项目启动

项目启动后，kafka自动创建了三个topic

![mark](http://cdn.wangxc.club/image/20190718/Js3bAzjpqd5Q.png)

在postman中发送一个请求
![mark](http://cdn.wangxc.club/image/20190718/th9rISBAT1CK.png)
控制台日志显示发送和接受一个消息


### 不错的博文

https://zhuanlan.zhihu.com/p/31731892


