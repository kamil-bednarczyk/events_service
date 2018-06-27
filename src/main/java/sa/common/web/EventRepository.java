package sa.common.web;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sa.common.model.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByUsernameAndDateBetween(String username, LocalDate start, LocalDate end);
}
