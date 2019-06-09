package lu.sfeir.technicalevent.subject

import java.time.Instant
import java.util.*

abstract class Events {
    val id: String = UUID.randomUUID().toString()
    val _ts: Instant = Instant.now()
    abstract val entityId: String
    abstract val _kind: String

}


object EventKind {
    const val ACCEPTED = "accepted"
    const val CREATED = "created"
    const val REFUSED = "refused"
    const val DELETED = "deleted"
    const val DESCRIPTION_CHANGED = "descriptionChanged"
    const val SCHEDULES_CHANGED = "schedulesChanged"
    const val TITLE_CHANGED = "titleChanged"
}