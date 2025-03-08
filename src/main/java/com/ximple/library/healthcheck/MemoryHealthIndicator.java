package com.ximple.library.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {
  @Override
  public Health health() {
    long freeMemory = Runtime.getRuntime().freeMemory();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long usedMemory = totalMemory - freeMemory;

    if (usedMemory < totalMemory * 0.9) {
      return Health.up().withDetail("memory", "Sufficient memory available").build();
    } else {
      return Health.down().withDetail("memory", "Memory usage is high").build();
    }
  }
}