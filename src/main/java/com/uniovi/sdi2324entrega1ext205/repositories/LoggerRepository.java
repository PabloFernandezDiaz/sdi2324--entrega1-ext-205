package com.uniovi.sdi2324entrega1ext205.repositories;


import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoggerRepository extends CrudRepository<LoggerEntry, Long> {


    List<LoggerEntry> findAllByOrderByDateDesc();
    List<LoggerEntry> findAllByLoggerTypeOrderByDateDesc(LoggerEntry.LoggerType type);

    void deleteAllByLoggerType(LoggerEntry.LoggerType type);

}
