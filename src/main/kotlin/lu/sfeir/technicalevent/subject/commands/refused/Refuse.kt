package lu.sfeir.technicalevent.subject.commands.refused

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class RefusedEvent(val status: String,
                        override val entityId: String) : Events()

@Component
class Refuse(private val eventsRepository: EventsRepository,
             private val refusedPubSubService: GCloudRefusedPubSubService) {
    fun refuse(entityId: String): Mono<RefusedEvent> {
        val result = eventsRepository.save(RefusedEvent(SubjectStatus.REFUSED, entityId = entityId))
        return result.doOnSuccess { t -> refusedPubSubService.sendMessage(t) }
    }
}