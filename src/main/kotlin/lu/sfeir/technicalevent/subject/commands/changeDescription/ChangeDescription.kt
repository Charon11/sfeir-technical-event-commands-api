package lu.sfeir.technicalevent.subject.commands.changeDescription

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class DescriptionChangedEvent(val description: String,
                                   override val entityId: String) : Events()

data class ChangeDescriptionCommand(val description: String)

@Component
class ChangeDescription(private val eventsRepository: EventsRepository,
                        private val descriptionChangedPubSubService: GCloudDescriptionChangedPubSubService) {
    fun changeDescription(entityId: String, changeDescriptionCommand: ChangeDescriptionCommand): Mono<DescriptionChangedEvent> {
        val result = eventsRepository.save(DescriptionChangedEvent(description = changeDescriptionCommand.description, entityId = entityId))
        return result.doOnSuccess { t -> descriptionChangedPubSubService.sendMessage(t) }
    }
}