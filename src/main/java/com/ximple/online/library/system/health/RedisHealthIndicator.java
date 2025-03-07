package com.ximple.online.library.system.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory redisConnectionFactory;

    @Override
    public Health health() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            String pong = connection.ping();
            if ("PONG".equals(pong)) {
                return Health.up().withDetail("status", "Redis is running").build();
            } else {
                return Health.down().withDetail("status", "Redis is not responding properly").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("status", "Redis is down").build();
        }
    }
}

