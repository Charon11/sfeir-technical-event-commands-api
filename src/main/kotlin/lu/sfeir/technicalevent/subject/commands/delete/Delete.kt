package lu.sfeir.technicalevent.subject.commands.delete

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

data class DeletedEvent(val status: String,
                            override val entityId: String,
                        override val _kind: String = EventKind.DELETED) : Events()

@Service
class Delete(private val gCloudPubSubService: GCloudPubSubService) {
    fun delete(entityId: String): DeletedEvent {
        val deletedEvent = DeletedEvent(SubjectStatus.DELETED, entityId = entityId)
        gCloudPubSubService.sendMessage(deletedEvent, ImmutableMap.of("_kind", EventKind.DELETED))
        return deletedEvent
    }
}