package sa.common.core;

import lombok.extern.log4j.Log4j2;
import sa.common.model.Event;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import sa.common.web.EventRepository;

@Log4j2
@Component
public class EventProcessor {

    private final EventRepository eventRepository;

    public EventProcessor(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @EventHandler
    public void on(EventCreatedEvent event) {
        eventRepository.save(Event.builder()
                .id(event.getId())
                .ownerId(event.getUsername())
                .type(event.getType())
                .when(event.getWhen())
                .build());
        log.info("Event persisted in database: " + event.toString());
    }

}
