package sa.common.core;

import sa.common.model.enums.EventType;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class EventAggregate {

    @AggregateIdentifier
    private String id;
    private String ownerId;
    private LocalDate when;
    private EventType type;


    @CommandHandler
    public EventAggregate(CreateEventCommand cmd) {
        apply(new EventCreatedEvent(cmd.getId(), cmd.getOwnerId(), cmd.getWhen(), cmd.getType()));
    }

    @EventSourcingHandler
    public void on(EventCreatedEvent event) {
        this.id = event.getId();
        this.ownerId = event.getOwnerId();
        this.when = event.getWhen();
        this.type = event.getType();
    }
}
