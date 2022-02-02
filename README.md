# mykafkajoinsplanet
The corresponding blog post for this repository can be found here: https://mydeveloperplanet.com/2019/10/30/kafka-streams-joins-explored/


---

* `Tutorial`: https://mydeveloperplanet.com/2019/10/30/kafka-streams-joins-explored/
* `Repo`: https://github.com/mydeveloperplanet/mykafkajoinsplanet


---

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

Давайте посмотрим на `KafkaProducerController`
Мы можем запустить поток данных, вызвав URL-адрес `http://localhost:8081/sendMessages`

Мы создали два HashMaps:
- один для LEFT-потока
- и один для RIGHT-потока,
содержащего данные с определенной отметкой времени.

После этого мы создаем цикл-for в котором мы отправляем сообщение в одну из тем каждые 10 секунд.
Используются HashMaps, чтобы определить, должны ли данные публиковаться в теме.
Левая-тема KStream: `my-kafka-left-stream-topic` и правая-тема KStream `my-kafka-right-stream-topic`.

### KStream-KStream Inner Join

Мы прочитаем левое-KStream и правое-KStream из соответствующих тем
выполним внутреннее соединение с обоими потоками и опубликуем их в теме `my-kafka-stream-stream-inner-join-out`

**Inner Join** — означает что каждая запись с одной стороны создаст запись со всеми совпадающими записями (соответствующими ключу) с другой стороны внутри настроенного окна


### KStream-KStream Left Join

Мы прочитаем левое-KStream и правое-KStream из соответствующих тем
выполним левое соединение с обоими потоками и опубликуем их в теме `my-kafka-stream-stream-left-join-out`

**Left Join** — означает что каждая запись на одной из сторон создаст запись со всеми совпадающими записями на левой стороне внутри настроенного окна
Если входная запись в левой части не имеет соответствующей записи в правой части, то будет создана выходная запись со значением null в правой части


### KStream-KStream Outer Join

Мы прочитаем левое-KStream и правое-KStream из соответствующих тем
выполним внешнее соединение с обоими потоками и опубликуем их в теме `my-kafka-stream-stream-outer-join-out`

**Outer Join** — означает что каждая запись на одной из обеих сторон создаст запись со всеми записями другой стороны внутри настроенного окна
Если входная запись с левой или правой стороны не имеет соответствующей записи с другой стороны, то будет создана выходная запись со значением null


### KStream-KTable Inner Join

Мы прочитаем левый-KStream и правый поток как KTable из соответствующих тем
выполним внутреннее соединение и опубликуем их в теме `my-kafka-stream-table-inner-join-out`

Соединения KStream-KTable всегда являются соединениями без окна.
KStream будет дополнен информацией из KTable.
Мы можем рассматривать это как поиск по таблице.
Поскольку мы используем здесь внутреннее соединение, обе стороны должны иметь значение, прежде чем будет создана выходная запись.












