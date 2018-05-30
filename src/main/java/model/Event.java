package model;

import model.enums.EventType;

import java.time.LocalDate;

public class Event {
    private String id;
    private String ownerId;
    private LocalDate when;
    private EventType eventType;
}
