package lu.sfeir.technicalevent.subject.commands.accept

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class AcceptedEvent(val status: String,
                         override val entityId: String) : Events()

@Component
class Accept(private val eventsRepository: EventsRepository,
             private val acceptedPubSubService: GCloudAcceptedPubSubService) {
    fun accept(entityId: String): Mono<AcceptedEvent> {
        val result = eventsRepository.save(AcceptedEvent(SubjectStatus.ACCEPTED, entityId = entityId))
        return result.doOnSuccess { t -> acceptedPubSubService.sendMessage(t) }
    }
}