package org.example.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.example.manager.RentManager;
import org.example.model.Rent;
//import org.example.generated.Rent;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer {
    private static final List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();

    private static String topics;

    private static final RentManager rm = RentManager.getInstance();

    public static void initConsumerGroup() {
        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "grupa");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        for(int i = 0; i < 2; i++) {
            KafkaConsumer<UUID, String> consumer = new KafkaConsumer<>(consumerConfig);
            consumer.subscribe(List.of(topics));
            consumerGroup.add(consumer);
        }
    }

    private static void consume(KafkaConsumer<UUID, String> consumer) {
        initConsumerGroup();
        try {
            consumer.poll(0);
            Set<TopicPartition> consumerAssigment = consumer.assignment();
            System.out.println(consumer.groupMetadata().memberId() + " " + consumerAssigment);
//            consumer.seekToBeginning(consumerAssigment);

            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
            MessageFormat formattter = new MessageFormat("Konsument {5}, Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość " +
                    "{4}");
            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);
                for (ConsumerRecord<UUID, String> record : records) {
                    String result = formattter.format(new Object[]{
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value(),
                            consumer.groupMetadata().memberId()
                    });
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    Rent rent = objectMapper.readValue(record.value(), Rent.class);
                    System.out.println(result);
                    rm.registerExistingRent(rent);
                    consumer.commitSync();
                }
            }

        } catch (WakeupException e) {
            System.out.println("Job finished");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void consumeTopicsByGroup(String name) throws InterruptedException {
        topics = name;
        initConsumerGroup();
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
                executorService.execute(() -> consume(consumer));
            }
            Thread.sleep(10000);
            for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
                consumer.wakeup();
            }
        }
    }

    public static boolean doesTopicExist(String topicName) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        try (AdminClient adminClient = AdminClient.create(config)) {
            Set<String> topicNames = adminClient.listTopics().names().get();
            return topicNames.contains(topicName);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Nie udało się sprawdzić istnienia tematu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
