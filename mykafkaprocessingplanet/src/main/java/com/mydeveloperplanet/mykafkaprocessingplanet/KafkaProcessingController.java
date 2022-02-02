package com.mydeveloperplanet.mykafkaprocessingplanet;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Properties;

@RestController
public class KafkaProcessingController {

    private KafkaStreams streamsInnerJoin;
    private KafkaStreams streamsLeftJoin;
    private KafkaStreams streamsOuterJoin;
    private KafkaStreams streamTableInnerJoin;
    private KafkaStreams streamTableLeftJoin;

    @RequestMapping("/startStreamStreamInnerJoin/")
    public void startStreamStreamInnerJoin() {
        System.out.println("stream-stream-inner-join"); // TODO: #1.
/*
CreateTime:1570367637661 FIXED-KEY left=A, right=a
CreateTime:1570367647662 FIXED-KEY left=B, right=a
CreateTime:1570367657663 FIXED-KEY left=A, right=b
CreateTime:1570367657663 FIXED-KEY left=B, right=b
CreateTime:1570367687664 FIXED-KEY left=C, right=a
CreateTime:1570367687664 FIXED-KEY left=C, right=b
CreateTime:1570367697665 FIXED-KEY left=A, right=c
CreateTime:1570367697665 FIXED-KEY left=B, right=c
CreateTime:1570367697665 FIXED-KEY left=C, right=c
CreateTime:1570367737668 FIXED-KEY left=A, right=d
CreateTime:1570367737668 FIXED-KEY left=B, right=d
CreateTime:1570367737668 FIXED-KEY left=C, right=d
CreateTime:1570367747668 FIXED-KEY left=D, right=a
CreateTime:1570367747668 FIXED-KEY left=D, right=b
CreateTime:1570367747668 FIXED-KEY left=D, right=c
CreateTime:1570367747668 FIXED-KEY left=D, right=d
 */
        stop();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-stream-inner-join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> LEFT_SOURCE  = builder.stream("my-kafka-left-stream-topic");
        KStream<String, String> RIGHT_SOURCE = builder.stream("my-kafka-right-stream-topic");

        KStream<String, String> joined = LEFT_SOURCE
                .join(RIGHT_SOURCE,
                      (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue, /* ValueJoiner */
                      JoinWindows.of(Duration.ofMinutes(5)),
                      Joined.with(Serdes.String(),  /* key */
                                  Serdes.String(),  /* left value */
                                  Serdes.String())  /* right value */
        );

        joined.to("my-kafka-stream-stream-inner-join-out");
        joined.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        final Topology topology = builder.build();
        streamsInnerJoin = new KafkaStreams(topology, props);
        streamsInnerJoin.start();
/*
FIXED-KEY left=A, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=A, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=a
FIXED-KEY left=B, right=b
FIXED-KEY left=B, right=c
FIXED-KEY left=B, right=d
FIXED-KEY left=B, right=a
FIXED-KEY left=B, right=b
FIXED-KEY left=B, right=c
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=a
FIXED-KEY left=C, right=b
FIXED-KEY left=C, right=c
FIXED-KEY left=C, right=d
FIXED-KEY left=C, right=a
FIXED-KEY left=C, right=b
FIXED-KEY left=C, right=c
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=a
FIXED-KEY left=D, right=b
FIXED-KEY left=D, right=c
FIXED-KEY left=D, right=d
FIXED-KEY left=D, right=a
FIXED-KEY left=D, right=b
FIXED-KEY left=D, right=c
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
*/
    }

    @RequestMapping("/startStreamStreamLeftJoin/")
    public void startStreamStreamLeftJoin() {
        System.out.println("stream-stream-left-join");
        stop();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-stream-left-join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> LEFT_SOURCE = builder.stream("my-kafka-left-stream-topic");
        KStream<String, String> RIGHT_SOURCE = builder.stream("my-kafka-right-stream-topic");

        KStream<String, String> joined = LEFT_SOURCE
                .leftJoin(RIGHT_SOURCE,
                          (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue, /* ValueJoiner */
                          JoinWindows.of(Duration.ofMinutes(5)),
                          Joined.with(Serdes.String(),  /* key */
                                      Serdes.String(),  /* left value */
                                      Serdes.String())  /* right value */
        );

        joined.to("my-kafka-stream-stream-left-join-out");
        joined.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        final Topology topology = builder.build();
        streamsLeftJoin = new KafkaStreams(topology, props);
        streamsLeftJoin.start();
/*
FIXED-KEY left=A, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=a
FIXED-KEY left=B, right=b
FIXED-KEY left=B, right=c
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=a
FIXED-KEY left=C, right=b
FIXED-KEY left=C, right=c
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=a
FIXED-KEY left=D, right=b
FIXED-KEY left=D, right=c
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
 */
    }

    @RequestMapping("/startStreamStreamOuterJoin/")
    public void startStreamStreamOuterJoin() {
        System.out.println("stream-stream-outer-join");
        stop();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-stream-outer-join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> LEFT_SOURCE = builder.stream("my-kafka-left-stream-topic");
        KStream<String, String> RIGHT_SOURCE = builder.stream("my-kafka-right-stream-topic");

        KStream<String, String> joined = LEFT_SOURCE
                .outerJoin(RIGHT_SOURCE,
                           (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue, /* ValueJoiner */
                           JoinWindows.of(Duration.ofMinutes(5)),
                           Joined.with(Serdes.String(),  /* key */
                                       Serdes.String(),  /* left value */
                                       Serdes.String())  /* right value */
        );

        joined.to("my-kafka-stream-stream-outer-join-out");
        joined.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        final Topology topology = builder.build();
        streamsOuterJoin = new KafkaStreams(topology, props);
        streamsOuterJoin.start();
/*
FIXED-KEY left=A, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=A, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=a
FIXED-KEY left=B, right=b
FIXED-KEY left=B, right=c
FIXED-KEY left=B, right=d
FIXED-KEY left=B, right=a
FIXED-KEY left=B, right=b
FIXED-KEY left=B, right=c
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=a
FIXED-KEY left=C, right=b
FIXED-KEY left=C, right=c
FIXED-KEY left=C, right=d
FIXED-KEY left=C, right=a
FIXED-KEY left=C, right=b
FIXED-KEY left=C, right=c
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=a
FIXED-KEY left=D, right=b
FIXED-KEY left=D, right=c
FIXED-KEY left=D, right=d
FIXED-KEY left=D, right=a
FIXED-KEY left=D, right=b
FIXED-KEY left=D, right=c
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=a
FIXED-KEY left=B, right=a
FIXED-KEY left=C, right=a
FIXED-KEY left=D, right=a
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=b
FIXED-KEY left=B, right=b
FIXED-KEY left=C, right=b
FIXED-KEY left=D, right=b
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=c
FIXED-KEY left=B, right=c
FIXED-KEY left=C, right=c
FIXED-KEY left=D, right=c
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
 */
    }

    @RequestMapping("/startStreamTableInnerJoin/")
    public void startStreamTableInnerJoin() {
        System.out.println("stream-table-inner-join"); // TODO: #2.
/*
CreateTime:1570372799769 FIXED-KEY left=B, right=a
CreateTime:1570372899774 FIXED-KEY left=D, right=d
 */
        stop();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-table-inner-join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> LEFT_SOURCE = builder.stream("my-kafka-left-stream-topic");
        KTable<String, String> RIGHT_SOURCE = builder.table("my-kafka-right-stream-topic");

        KStream<String, String> joined = LEFT_SOURCE
                .join(RIGHT_SOURCE,
                      (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue /* ValueJoiner */
         );

        joined.to("my-kafka-stream-table-inner-join-out");
        joined.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        final Topology topology = builder.build();
        streamTableInnerJoin = new KafkaStreams(topology, props);
        streamTableInnerJoin.start();
/*
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d
 */
    }

    @RequestMapping("/startStreamTableLeftJoin/")
    public void startStreamTableLeftJoin() {
        System.out.println("stream-table-left-join");
/*
CreateTime:1570373267768 FIXED-KEY left=A, right=null
CreateTime:1570373287769 FIXED-KEY left=B, right=a
CreateTime:1570373327771 FIXED-KEY left=C, right=null
CreateTime:1570373387774 FIXED-KEY left=D, right=d
 */
        stop();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-table-left-join");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> LEFT_SOURCE = builder.stream("my-kafka-left-stream-topic");
        KTable<String, String> RIGHT_SOURCE = builder.table("my-kafka-right-stream-topic");

        KStream<String, String> joined = LEFT_SOURCE
                .leftJoin(RIGHT_SOURCE,
                          (leftValue, rightValue) -> "left=" + leftValue + ", right=" + rightValue /* ValueJoiner */
         );

        joined.to("my-kafka-stream-table-left-join-out");
        joined.foreach(new ForeachAction<String, String>() {
            public void apply(String key, String value) {
                System.out.println(key + " " + value);
            }
        });

        final Topology topology = builder.build();
        streamTableLeftJoin = new KafkaStreams(topology, props);
        streamTableLeftJoin.start();
/*
FIXED-KEY left=A, right=d
FIXED-KEY left=B, right=d
FIXED-KEY left=C, right=d
FIXED-KEY left=D, right=d

 */
    }

    private void stop () {
        if (streamsInnerJoin != null) {
            streamsInnerJoin.close();
        }
        if (streamsLeftJoin != null) {
            streamsLeftJoin.close();
        }
        if (streamsOuterJoin != null) {
            streamsOuterJoin.close();
        }
        if (streamTableInnerJoin != null) {
            streamTableInnerJoin.close();
        }
        if (streamTableLeftJoin != null) {
            streamTableLeftJoin.close();
        }
    }

}
