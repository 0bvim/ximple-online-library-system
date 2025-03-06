package com.ximple.online.library.system.events.store;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ximple.online.library.system.events.models.LibraryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventStoreService {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    public void storeEvent(String eventType, String entityType, UUID entityId, LibraryEvent event) {
        try {
            String jsonData = objectMapper.writeValueAsString(event);
            jdbcTemplate.update(
                    "INSERT INTO event_store (event_type, entity_type, entity_id, data, created_at) VALUES (?, ?, ?, ?::jsonb, ?)",
                    eventType, entityType, entityId, jsonData, LocalDateTime.now()
            );
        } catch (Exception e) {
            log.error("Failed to store event", e);
            throw new RuntimeException("Failed to store event", e);
        }
    }

    @Transactional(readOnly = true)
    public <T extends LibraryEvent> void replayEvents(String entityType, UUID entityId, Class<T> eventClass, Consumer<T> eventProcessor) {
        String sql = "SELECT data FROM event_store WHERE entity_type = ? AND entity_id = ? ORDER BY created_at ASC";

        List<String> jsonEvents = jdbcTemplate.queryForList(sql, String.class, entityType, entityId);

        for (String jsonEvent : jsonEvents) {
            try {
                T event = objectMapper.readValue(jsonEvent, eventClass);
                eventProcessor.accept(event);
            } catch (Exception e) {
                log.error("Error replaying event", e);
            }
        }
    }

    @Transactional
    public void markEventsAsProcessed(UUID entityId) {
        jdbcTemplate.update(
                "UPDATE event_store SET processed = true WHERE entity_id = ? AND processed = false",
                entityId
        );
    }

    @Transactional(readOnly = true)
    public <T extends LibraryEvent> void replayAllEvents(String entityType, Class<T> eventClass, Consumer<T> eventProcessor) {
        String sql = "SELECT data FROM event_store WHERE entity_type = ? ORDER BY created_at ASC";

        List<String> jsonEvents = jdbcTemplate.queryForList(sql, String.class, entityType);

        for (String jsonEvent : jsonEvents) {
            try {
                T event = objectMapper.readValue(jsonEvent, eventClass);
                eventProcessor.accept(event);
            } catch (Exception e) {
                log.error("Error replaying event", e);
            }
        }
    }
}