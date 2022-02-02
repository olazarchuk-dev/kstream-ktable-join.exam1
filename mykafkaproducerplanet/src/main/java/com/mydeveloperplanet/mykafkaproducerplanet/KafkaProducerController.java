package com.mydeveloperplanet.mykafkaproducerplanet;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Properties;

@RestController
public class KafkaProducerController {

    private static final String KEY = "FIXED-KEY";

    @RequestMapping("/sendMessages/")
    public void sendMessages() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        try {
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, null));
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "A"));
            producer.send(new ProducerRecord("my-kafka-left-stream-topic", KEY, "B"));

            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, null));
            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "a"));
            producer.send(new ProducerRecord("my-kafka-right-stream-topic", KEY, "b"));
        } finally {
            producer.close();
        }
    }
}
