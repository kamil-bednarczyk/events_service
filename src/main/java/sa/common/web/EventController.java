package sa.common.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sa.common.core.CreateEventCommand;
import sa.common.model.CreateEventDto;
import sa.common.model.Event;
import sa.common.model.EventDto;
import sa.common.model.enums.EventType;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

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

        List<LocalDate> days = LongStream.range(start.toEpochDay(), end.toEpochDay()).mapToObj(LocalDate::ofEpochDay).collect(Collectors.toList());

        Map<LocalDate, EventDto> events = eventRepository.findByUsernameAndDateBetween(username, start.minusDays(1) //  start date adjustment
                , end)
                .stream()
                .map(EventController::convertToEventDto)
                .collect(Collectors.toMap(EventDto::getWhen, e -> e));

        List<EventDto> allEvents = days.stream().filter(day -> !events.keySet().contains(day))
                .map(day -> EventDto.builder()
                .id(UUID.randomUUID().toString())
                .ownerId("")
                .when(day)
                .type(EventType.NO_EVENT.toString()).build()).collect(Collectors.toList());

        allEvents.addAll(events.values());
        allEvents.sort((e1, e2) -> e1.getWhen().isBefore(e2.getWhen()) ? -1 : 1);
        return allEvents;

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
