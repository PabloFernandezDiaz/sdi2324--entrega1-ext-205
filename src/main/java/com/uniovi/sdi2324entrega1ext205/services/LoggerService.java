package com.uniovi.sdi2324entrega1ext205.services;


import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.repositories.LoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class LoggerService {
    private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);
    private final LoggerRepository loggerRepository;

    public LoggerService(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    public void addEntry(LoggerEntry loggerEntry){

        loggerRepository.save(loggerEntry);
        logger.info(loggerEntry.toString());
    }
    public void addEntry(String description, Timestamp date, LoggerEntry.LoggerType logType){
        LoggerEntry loggerEntry = new LoggerEntry(description,date,logType);
        loggerRepository.save(loggerEntry);
        logger.info(loggerEntry.toString());
    }
    public void addEntry(String description, LoggerEntry.LoggerType logType){
        LoggerEntry loggerEntry = new LoggerEntry(description,new Timestamp(System.currentTimeMillis()),logType);
        loggerRepository.save(loggerEntry);
        logger.info(loggerEntry.toString());
    }
    public List<LoggerEntry> getAllEntries(){
        return loggerRepository.findAllByOrderByDateDesc();
    }
    public List<LoggerEntry> getAllEntriesByType(String type){
        if(Arrays.stream(LoggerEntry.LoggerType.values()).noneMatch(t->t.name().equals(type))){
            return getAllEntries();
        }

        return loggerRepository.findAllByLoggerTypeOrderByDateDesc(LoggerEntry.LoggerType.valueOf(type));
    }

    @Transactional
    public void deleteAllEntriesByType(String type){
        if(Arrays.stream(LoggerEntry.LoggerType.values()).anyMatch(t->t.name().equals(type)
               ) || type.equals(LoggerEntry.ALL))
            if(type.equals(LoggerEntry.ALL))
                deleteAllEntries();
            else
                loggerRepository.deleteAllByLoggerType(LoggerEntry.LoggerType.valueOf(type));

    }
    public void deleteAllEntries(){
        loggerRepository.deleteAll();
    }

}
