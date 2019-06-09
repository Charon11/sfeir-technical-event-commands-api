package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Service

data class AcceptedEvent(val status: String,
                         override val entityId: String,
                         override val _kind: String = EventKind.ACCEPTED) : Events()

@Service
class Accept(private val gCloudPubSubService: GCloudPubSubService) {
    fun accept(entityId: String): AcceptedEvent {
        val accept = AcceptedEvent(SubjectStatus.ACCEPTED, entityId = entityId)
        gCloudPubSubService.sendMessage(accept, ImmutableMap.of("_kind", accept._kind))
        return accept
    }
}