package sa.common.axon;

import lombok.Value;
import sa.common.model.enums.EventType;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.time.LocalDate;

@Value
public class CreateEventCommand {

    @TargetAggregateIdentifier
    private String id;
    private String username;
    private LocalDate when;
    private EventType type;
}
