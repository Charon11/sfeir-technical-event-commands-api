package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Service

data class DescriptionChangedEvent(val description: String,
                                   override val entityId: String,
                                   override val _kind: String = EventKind.DESCRIPTION_CHANGED) : Events()

data class ChangeDescriptionCommand(val description: String)

@Service
class ChangeDescription(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeDescription(entityId: String, changeDescriptionCommand: ChangeDescriptionCommand): DescriptionChangedEvent {
        val descriptionChangedEvent = DescriptionChangedEvent(description = changeDescriptionCommand.description, entityId = entityId)
        gCloudPubSubService.sendMessage(descriptionChangedEvent, ImmutableMap.of("_kind", descriptionChangedEvent._kind))
        return descriptionChangedEvent
    }
}