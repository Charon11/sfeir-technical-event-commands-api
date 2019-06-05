package lu.sfeir.technicalevent.subject.commands.delete

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class DeletedEvent(val status: String,
                            override val entityId: String,
                        override val _kind: String = EventKind.DELETED) : Events()

@Component
class Delete(private val eventsRepository: EventsRepository,
             private val gCloudPubSubService: GCloudPubSubService) {
    fun delete(entityId: String): Mono<DeletedEvent> {
        val result = eventsRepository.save(DeletedEvent(SubjectStatus.DELETED, entityId = entityId))
        return result.doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.DELETED)) }
    }
}