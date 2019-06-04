package lu.sfeir.technicalevent.subject.commands.delete

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class DeletedEvent(val status: String,
                            override val entityId: String) : Events()

@Component
class Delete(private val eventsRepository: EventsRepository,
             private val deletedPubSubService: GCloudDeletedPubSubService) {
    fun delete(entityId: String): Mono<DeletedEvent> {
        val result = eventsRepository.save(DeletedEvent(SubjectStatus.DELETED, entityId = entityId))
        return result.doOnSuccess { t -> deletedPubSubService.sendMessage(t) }
    }
}