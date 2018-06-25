package sa.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sa.common.model.enums.EventType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {

    @NotBlank
    private String username;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate when;
    private EventType type;
}
