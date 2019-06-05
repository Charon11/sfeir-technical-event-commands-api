package lu.sfeir.technicalevent.subject.commands.changeDescription

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class DescriptionChangedEvent(val description: String,
                                   override val entityId: String,
                                   override val _kind: String = EventKind.DESCRIPTION_CHANGED) : Events()

data class ChangeDescriptionCommand(val description: String)

@Component
class ChangeDescription(private val eventsRepository: EventsRepository,
                        private val gCloudPubSubService: GCloudPubSubService) {
    fun changeDescription(entityId: String, changeDescriptionCommand: ChangeDescriptionCommand): Mono<DescriptionChangedEvent> {
        val result = eventsRepository.save(DescriptionChangedEvent(description = changeDescriptionCommand.description, entityId = entityId))
        return result.doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.DESCRIPTION_CHANGED)) }
    }
}