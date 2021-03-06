package sa.common.axon;

import lombok.extern.log4j.Log4j2;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import sa.common.model.Event;
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
        eventRepository.findByUsernameAndDate(event.getUsername(), event.getWhen()).ifPresent(eventRepository::delete);

        eventRepository.save(Event.builder()
                .id(event.getId())
                .username(event.getUsername())
                .type(event.getType())
                .date(event.getWhen())
                .build());
        log.info("Event persisted in database: " + event.toString());
    }


}
