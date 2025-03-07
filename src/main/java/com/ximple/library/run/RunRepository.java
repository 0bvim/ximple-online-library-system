package com.ximple.library.run;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {

    private final List<Run> runs = new ArrayList<>();

    List<Run> findAll() {
        return runs;
    }

    Optional<Run> findById(Integer id) {
        return runs.stream()
                .filter(run -> run.id().equals(id))
                .findFirst();
    }

    void create(Run run) {
        runs.add(run);
    }

    void update(Run run, Integer id) {
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(value -> runs.set(runs.indexOf(value), run));
    }

    void delete(Integer id) {
        Optional<Run> existingRun = findById(id);
        existingRun.ifPresent(run -> runs.remove(run));
    }

    @PostConstruct
    private void init() {
        runs.add(new Run(1, "Morning Run", LocalDateTime.now(), LocalDateTime.now(), 5, Location.OUTDOOR));
        runs.add(new Run(2, "Evening Run", LocalDateTime.now(), LocalDateTime.now(), 10, Location.OUTDOOR));
        runs.add(new Run(3, "Night Run", LocalDateTime.now(), LocalDateTime.now(), 10, Location.OUTDOOR));
        runs.add(new Run(4, "Sunday Run", LocalDateTime.now(), LocalDateTime.now(), 10, Location.OUTDOOR));
        runs.add(new Run(5, "Monday Run", LocalDateTime.now(), LocalDateTime.now(), 8, Location.INDOOR));
        runs.add(new Run(6, "Tuesday Run", LocalDateTime.now(), LocalDateTime.now(), 7, Location.OUTDOOR));
        runs.add(new Run(7, "Wednesday Run", LocalDateTime.now(), LocalDateTime.now(), 6, Location.INDOOR));
        runs.add(new Run(8, "Thursday Run", LocalDateTime.now(), LocalDateTime.now(), 9, Location.OUTDOOR));
        runs.add(new Run(9, "Friday Run", LocalDateTime.now(), LocalDateTime.now(), 5, Location.INDOOR));
        runs.add(new Run(10, "Saturday Run", LocalDateTime.now(), LocalDateTime.now(), 12, Location.OUTDOOR));
    }

}
