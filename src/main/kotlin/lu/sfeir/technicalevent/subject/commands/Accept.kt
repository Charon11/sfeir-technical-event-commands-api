package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Service
import java.time.Instant

data class AcceptedEvent(val status: String,
                         val link: String? = null,
                         val scheduleDate: Instant,
                         override val entityId: String,
                         override val userId: String,
                         override val _kind: String = EventKind.ACCEPTED) : Events()

data class AcceptCommand(val scheduleDate: Instant,val link: String? = null)

@Service
class Accept(private val gCloudPubSubService: GCloudPubSubService) {
    fun accept(entityId: String, acceptCommand: AcceptCommand, token: FirebaseToken): AcceptedEvent {
        val accept = AcceptedEvent(SubjectStatus.ACCEPTED,
                link = acceptCommand.link,
                scheduleDate = acceptCommand.scheduleDate,
                entityId = entityId,
                userId = token.uid)
        gCloudPubSubService.sendMessage(accept, ImmutableMap.of("_kind", accept._kind))
        return accept
    }
}