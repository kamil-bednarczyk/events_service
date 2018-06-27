package sa.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetEventsInRangeDto {

    private String username;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate start;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate end;

}
