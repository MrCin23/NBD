package org.example.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
//import org.example.generated.Client;
import org.example.model.Rent;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Producer {
    private static KafkaProducer<UUID, String> producer;

    public static void initProducer () {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put (ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producer = new KafkaProducer<>(producerConfig);
    }

    public static void createTopic (String topic) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka1:9292, kafka1:9392");
        int partitionsNumber = 3;
        short replicationFactor = 3;
        try (Admin admin = Admin.create(properties)) {
            NewTopic newTopic = new NewTopic(topic, partitionsNumber, replicationFactor);
            CreateTopicsOptions options = new CreateTopicsOptions()
                    .timeoutMs(1000)
                    .validateOnly(false)
                    .retryOnQuotaViolation(true);
            CreateTopicsResult result = admin.createTopics(List.of(newTopic), options);
            KafkaFuture<Void> futureResult = result.values().get(topic);
            futureResult.get();
        } catch (ExecutionException ee) {
            System.out.println(ee.getCause());
            //assertThat(ee.getCause(), is( instanceof (TopicExistsException.class))); ??????
        }
    }

    public static void sendMessage (String topic, Rent rent) throws InterruptedException {
        try {
            initProducer();
            if(!doesTopicExist(topic)){
                createTopic(topic);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String rentString = objectMapper.writeValueAsString(rent);
            ProducerRecord<UUID, String> record = new ProducerRecord<>(topic, rent.getEntityId().getUuid(), rentString);
            Future<RecordMetadata> sent = producer.send(record);
            RecordMetadata recordMetadata = sent.get();
            System.out.println(recordMetadata);
        } catch (ExecutionException | InterruptedException e) {
            System.out.println(e.getCause());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteTopic(String topicName) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");

        try (AdminClient adminClient = AdminClient.create(config)) {
            adminClient.deleteTopics(Collections.singletonList(topicName)).all().get();
            System.out.println("Temat '" + topicName + "' został usunięty.");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Nie udało się usunąć tematu: " + e.getMessage());
            e.printStackTrace();
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
