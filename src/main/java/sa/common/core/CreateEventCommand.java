package sa.common.core;

import lombok.Value;
import sa.common.model.enums.EventType;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.LocalDate;

@Value
public class CreateEventCommand {

    @TargetAggregateIdentifier
    private String id;
    private String ownerId;
    private LocalDate when;
    private EventType type;
}
