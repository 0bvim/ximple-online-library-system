package com.ximple.online.library.system.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaHealthIndicator implements HealthIndicator {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Health health() {
        try {
            // Check if Kafka brokers are available
            kafkaTemplate.getProducerFactory().createProducer().close();
            return Health.up().withDetail("status", "Kafka is running").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("status", "Kafka is down").build();
        }
    }
}
