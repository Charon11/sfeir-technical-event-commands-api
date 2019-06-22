package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Service

data class RefusedEvent(val status: String,
                        override val entityId: String,
                        override val userId: String,
                        override val _kind: String = EventKind.REFUSED) : Events()

@Service
class Refuse(private val gCloudPubSubService: GCloudPubSubService) {
    fun refuse(entityId: String, token: FirebaseToken): RefusedEvent {
        val refusedEvent = RefusedEvent(SubjectStatus.REFUSED, entityId = entityId, userId = token.uid)
        gCloudPubSubService.sendMessage(refusedEvent, ImmutableMap.of("_kind", refusedEvent._kind))
        return refusedEvent
    }
}