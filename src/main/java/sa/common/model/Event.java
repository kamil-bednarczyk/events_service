package sa.common.model;

import lombok.Builder;
import lombok.Data;
import sa.common.model.enums.EventType;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Builder
@Data
public class Event {
    @Id
    private String id;
    private String username;
    private LocalDate date;
    private EventType type;
}
