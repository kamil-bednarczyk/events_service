package sa.common.web;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sa.common.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByUsernameAndDateBetween(String username, LocalDate start, LocalDate end);

    Optional<Event> findByUsernameAndDate(String username, LocalDate date);
}
