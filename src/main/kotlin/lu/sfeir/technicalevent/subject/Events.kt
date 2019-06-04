package lu.sfeir.technicalevent.subject

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Document(collection = "subject-events")
abstract class Events {
    @Id
    val id: String = UUID.randomUUID().toString()
    val _ts: Instant = Instant.now()
    abstract val entityId: String

}

@Repository
interface EventsRepository : ReactiveCrudRepository<Events, String>