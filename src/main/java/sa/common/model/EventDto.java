package sa.common.model;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class EventDto{

    private String id;
    private String ownerId;
    private LocalDate when;
    private String type;
}
