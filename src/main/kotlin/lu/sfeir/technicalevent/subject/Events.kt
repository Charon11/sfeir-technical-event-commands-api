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
    abstract val _kind: String

}

@Repository
interface EventsRepository : ReactiveCrudRepository<Events, String>

object EventKind {
    const val ACCEPTED = "accepted"
    const val CREATED = "created"
    const val REFUSED = "refused"
    const val DELETED = "deleted"
    const val DESCRIPTION_CHANGED = "descriptionChanged"
    const val TITLE_CHANGED = "titleChanged"
}