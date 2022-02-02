package com.mydeveloperplanet.mykafkaproducerplanet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
public class KafkaProducerController {

    private static final String KEY = "FIXED-KEY";

    private static final Map<Integer, String> LEFT;
    static {
        LEFT = new HashMap<>();
        LEFT.put(1, null);
        LEFT.put(3, "A");
        LEFT.put(5, "B");
        LEFT.put(7, null);
        LEFT.put(9, "C");
        LEFT.put(12, null);
        LEFT.put(15, "D");
    }

    private static final Map<Integer, String> RIGHT;
    static {
        RIGHT = new HashMap<>();
        RIGHT.put(2, null);
        RIGHT.put(4, "a");
        RIGHT.put(6, "b");
        RIGHT.put(8, null);
        RIGHT.put(10, "c");
        RIGHT.put(11, null);
        RIGHT.put(13, null);
        RIGHT.put(14, "d");
    }

    @RequestMapping("/sendMessages/")
    public void sendMessages() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        try {
/*
0  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-left-stream-topic'
1  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'A' to 'my-kafka-left-stream-topic'
2  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'B' to 'my-kafka-left-stream-topic'
3  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-left-stream-topic'
4  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'C' to 'my-kafka-left-stream-topic'
5  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-left-stream-topic'
6  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'D' to 'my-kafka-left-stream-topic'

0  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-right-stream-topic'
1  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'a' to 'my-kafka-right-stream-topic'
2  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'b' to 'my-kafka-right-stream-topic'
3  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-right-stream-topic'
4  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'c' to 'my-kafka-right-stream-topic'
5  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-right-stream-topic'
6  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'null' to 'my-kafka-right-stream-topic'
7  INFO 27 --- com.mydeveloperplanet.mykafkaproducerplanet.KafkaProducerController : Send a Message 'd' to 'my-kafka-right-stream-topic'
*/
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, null));
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "A"));
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "B"));
//            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, null));
//            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "C"));
//            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, null));
//            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "D"));

            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, null));
            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "a"));
            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "b"));
//            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, null));
//            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "c"));
//            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, null));
//            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, null));
//            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "d"));
        } finally {
            producer.close();
        }
    }
}
