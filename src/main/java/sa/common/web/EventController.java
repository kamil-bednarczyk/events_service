package sa.common.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sa.common.core.CreateEventCommand;
import sa.common.model.CreateEventDto;
import sa.common.model.Event;
import sa.common.model.EventDto;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;
    private final CommandGateway commandGateway;

    public EventController(EventRepository eventRepository, CommandGateway commandGateway) {
        this.eventRepository = eventRepository;
        this.commandGateway = commandGateway;
    }

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream().map(EventController::convertToEventDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-range/{username}/{start}/{end}")
    public List<EventDto> getEventsBetween(@PathVariable("username") String username,
                                           @PathVariable("start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                           @PathVariable("end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end) {
        return eventRepository.findByUsernameAndDateBetween(username, start, end)
                .stream()
                .map(EventController::convertToEventDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void createEvent(@RequestBody @Valid List<CreateEventDto> dtos) {
        dtos.forEach(dto ->
                commandGateway.send(new CreateEventCommand(UUID.randomUUID().toString(),
                        dto.getUsername(), dto.getWhen(), dto.getType())));
    }

    public static EventDto convertToEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .ownerId(event.getUsername())
                .type(event.getType().toString())
                .when(event.getDate())
                .build();
    }
}
