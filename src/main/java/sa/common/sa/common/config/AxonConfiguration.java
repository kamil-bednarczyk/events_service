package sa.common.sa.common.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(mongoTemplate);
    }

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(eventStorageEngine());
    }
}
