package com.uniovi.sdi2324entrega1ext205.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Log")
public class LoggerEntry {
    @Id
    @GeneratedValue
    private Long id;


    public String description;

    public Timestamp date;

    public LoggerEntry() {

    }

    public enum LoggerType {LOGOUT, LOGIN_ERR, LOGIN_EX, PET, ALTA}

    @Transient
    public static final String ALL ="ALL";
    @Enumerated(EnumType.STRING)
    public LoggerType loggerType;

    public LoggerEntry(String description, Timestamp date, LoggerType loggerType) {
        this.description = description;
        this.date = date;
        this.loggerType = loggerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public LoggerType getLoggerType() {
        return loggerType;
    }

    public void setLoggerType(LoggerType loggerType) {
        this.loggerType = loggerType;
    }

//    @Override
//    public String toString() {
//        return "Type= " + loggerType +  ", date= " + date + ", description= " + description;
//    }
    @Override
    public String toString() {
        return loggerType + ":"+description;
    }
}
