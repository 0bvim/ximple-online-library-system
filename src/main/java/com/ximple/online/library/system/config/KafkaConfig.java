package com.ximple.online.library.system.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Kafka topic for book events
    @Bean
    public NewTopic booksTopic() {
        return TopicBuilder.name("books-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // Kafka topic for reservation events
    @Bean
    public NewTopic reservationsTopic() {
        return TopicBuilder.name("reservations-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // Kafka topic for review events
    @Bean
    public NewTopic reviewsTopic() {
        return TopicBuilder.name("reviews-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    // Configure the producer factory
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Create a KafkaTemplate for producing events
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}