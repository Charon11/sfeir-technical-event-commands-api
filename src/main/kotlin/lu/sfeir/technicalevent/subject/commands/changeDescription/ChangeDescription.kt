package lu.sfeir.technicalevent.subject.commands.changeDescription

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class DescriptionChangedEvent(val description: String,
                                   override val entityId: String,
                                   override val _kind: String = EventKind.DESCRIPTION_CHANGED) : Events()

data class ChangeDescriptionCommand(val description: String)

@Component
class ChangeDescription(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeDescription(entityId: String, changeDescriptionCommand: ChangeDescriptionCommand): Mono<DescriptionChangedEvent> {
        return Mono.just(DescriptionChangedEvent(description = changeDescriptionCommand.description, entityId = entityId))
            .doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.DESCRIPTION_CHANGED)) }
    }
}