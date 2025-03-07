package com.ximple.library.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository RunRepository;
    private final RunRepository runRepository;

    public RunController(RunRepository RunRepository, RunRepository runRepository) {
        this.RunRepository = RunRepository;
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll() {
        return RunRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Run> findById(@PathVariable Integer id) {
        return RunRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        RunRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@PathVariable Integer id, @Valid @RequestBody Run run) {
        RunRepository.save(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        RunRepository.delete(runRepository.findById(id).get());
    }

    @GetMapping("/location/{location}")
    List<Run> findAllByLocation(@PathVariable String location) {
        return RunRepository.findAllByLocation(location);
    }
}
