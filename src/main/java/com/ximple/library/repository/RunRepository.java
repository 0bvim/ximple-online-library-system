package com.ximple.library.repository;

import com.ximple.library.model.Run;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

    @Query
    List<Run> findAllByLocation(String location);
}
