# Kafka Streams Joins Explored: 'Stream-Stream Inner-Join', 'Stream-Stream Left-Join', 'Stream-Stream Outer-Join', 'Stream-Table Inner-Join', 'Stream-Table Left-Join'

The corresponding blog post for this repository can be found here: https://mydeveloperplanet.com/2019/10/30/kafka-streams-joins-explored


---


### Step 1

```shell
> sudo su -
> cat /opt/kafka/config/server.properties | grep log.dirs
```
Expected output:
```shell
log.dirs=/var/lib/kafka/data
```
```shell
> cd /var/lib/kafka/data
```
Delete **data** which saved the wrong cluster.id of last/failed session

### Step 2

In a separate terminal, Start ZooKeeper

```shell
> sudo su -
> /opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties
```
Expected output:
```text
[2022-01-15 06:37:01,006] INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)
```

### Step 3

In a separate terminal, Start Kafka broker

```shell
> sudo su -
> /opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties
```
Expected output:
```text
[2022-01-15 06:38:04,234] INFO Connecting to zookeeper on localhost:2181 (kafka.server.KafkaServer)
[2022-01-15 06:38:04,245] INFO [ZooKeeperClient Kafka server] Initializing a new session to localhost:2181. (kafka.zookeeper.ZooKeeperClient)
...
[2022-01-15 07:54:33,508] INFO [BrokerToControllerChannelManager broker=0 name=forwarding]: Recorded new controller, from now on will use broker dell-5500:9092 (id: 0 rack: null) (kafka.server.BrokerToControllerRequestThread)
```

### The left topic:

```shell
> /opt/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic my-kafka-left-stream-topic
```

### The right topic:

```shell
> /opt/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic my-kafka-right-stream-topic
```

```shell
> /opt/kafka/bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic my-kafka-stream-stream-inner-join-out
> /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic my-kafka-stream-stream-inner-join-out --property print.key=true --property print.timestamp=true
```

1. to start (Kafka-Producer Application) `MyKafkaProducerPlanetApplication`
2. to start (Kafka-Processing Application) `MyKafkaProcessingPlanetApplication`
3. to start:
   - select `Stream-Stream Inner-Join` [Kafka-Processing Controller](http://localhost:8082/startStreamStreamInnerJoin)
   - do `Send Messages` [Kafka-Producer Controller](http://localhost:8081/sendMessages)
   - and looking response from `MyKafkaProcessingPlanetApplication` to console
4. to start:
   - select `Stream-Table Inner-Join` [Kafka-Processing Controller](http://localhost:8082/startStreamTableInnerJoin)
   - do `Send Messages` [Kafka-Producer Controller](http://localhost:8081/sendMessages)
   - and looking response from `MyKafkaProcessingPlanetApplication` to console

---

?????????????? ?????????????????? ???? `KafkaProducerController`
???? ?????????? ?????????????????? ?????????? ????????????, ???????????? URL-?????????? `http://localhost:8081/sendMessages`

???? ?????????????? ?????? HashMaps:
- ???????? ?????? LEFT-????????????
- ?? ???????? ?????? RIGHT-????????????,
?????????????????????? ???????????? ?? ???????????????????????? ???????????????? ??????????????.

?????????? ?????????? ???? ?????????????? ????????-for ?? ?????????????? ???? ???????????????????? ?????????????????? ?? ???????? ???? ?????? ???????????? 10 ????????????.
???????????????????????? HashMaps, ?????????? ????????????????????, ???????????? ???? ???????????? ?????????????????????????? ?? ????????.
??????????-???????? KStream: `my-kafka-left-stream-topic` ?? ????????????-???????? KStream `my-kafka-right-stream-topic`.

### KStream-KStream Inner Join

???? ?????????????????? ??????????-KStream ?? ????????????-KStream ???? ?????????????????????????????? ??????
???????????????? ???????????????????? ???????????????????? ?? ???????????? ???????????????? ?? ???????????????????? ???? ?? ???????? `my-kafka-stream-stream-inner-join-out`

**Inner Join** ??? ???????????????? ?????? ???????????? ???????????? ?? ?????????? ?????????????? ?????????????? ???????????? ???? ?????????? ???????????????????????? ???????????????? (???????????????????????????????? ??????????) ?? ???????????? ?????????????? ???????????? ???????????????????????? ????????


### KStream-KStream Left Join

???? ?????????????????? ??????????-KStream ?? ????????????-KStream ???? ?????????????????????????????? ??????
???????????????? ?????????? ???????????????????? ?? ???????????? ???????????????? ?? ???????????????????? ???? ?? ???????? `my-kafka-stream-stream-left-join-out`

**Left Join** ??? ???????????????? ?????? ???????????? ???????????? ???? ?????????? ???? ???????????? ?????????????? ???????????? ???? ?????????? ???????????????????????? ???????????????? ???? ?????????? ?????????????? ???????????? ???????????????????????? ????????
???????? ?????????????? ???????????? ?? ?????????? ?????????? ???? ?????????? ?????????????????????????????? ???????????? ?? ???????????? ??????????, ???? ?????????? ?????????????? ???????????????? ???????????? ???? ?????????????????? null ?? ???????????? ??????????


### KStream-KStream Outer Join

???? ?????????????????? ??????????-KStream ?? ????????????-KStream ???? ?????????????????????????????? ??????
???????????????? ?????????????? ???????????????????? ?? ???????????? ???????????????? ?? ???????????????????? ???? ?? ???????? `my-kafka-stream-stream-outer-join-out`

**Outer Join** ??? ???????????????? ?????? ???????????? ???????????? ???? ?????????? ???? ?????????? ???????????? ?????????????? ???????????? ???? ?????????? ???????????????? ???????????? ?????????????? ???????????? ???????????????????????? ????????
???????? ?????????????? ???????????? ?? ?????????? ?????? ???????????? ?????????????? ???? ?????????? ?????????????????????????????? ???????????? ?? ???????????? ??????????????, ???? ?????????? ?????????????? ???????????????? ???????????? ???? ?????????????????? null


### KStream-KTable Inner Join

???? ?????????????????? ??????????-KStream ?? ???????????? ?????????? ?????? KTable ???? ?????????????????????????????? ??????
???????????????? ???????????????????? ???????????????????? ?? ???????????????????? ???? ?? ???????? `my-kafka-stream-table-inner-join-out`

???????????????????? KStream-KTable ???????????? ???????????????? ???????????????????????? ?????? ????????.
KStream ?????????? ???????????????? ?????????????????????? ???? KTable.
???? ?????????? ?????????????????????????? ?????? ?????? ?????????? ???? ??????????????.
?????????????????? ???? ???????????????????? ?????????? ???????????????????? ????????????????????, ?????? ?????????????? ???????????? ?????????? ????????????????, ???????????? ?????? ?????????? ?????????????? ???????????????? ????????????.












