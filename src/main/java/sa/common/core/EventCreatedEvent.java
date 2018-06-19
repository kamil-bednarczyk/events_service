package sa.common.core;

import lombok.Value;
import sa.common.model.enums.EventType;

import java.time.LocalDate;

@Value
public class EventCreatedEvent {

    private String id;
    private String ownerId;
    private LocalDate when;
    private EventType type;
}
