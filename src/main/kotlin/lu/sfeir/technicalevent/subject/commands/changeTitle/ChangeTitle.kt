package lu.sfeir.technicalevent.subject.commands.changeTitle

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

data class TitleChangedEvent(val title: String,
                             override val entityId: String,
                             override val _kind: String = EventKind.TITLE_CHANGED) : Events()

data class ChangeTitleCommand(val title: String)

@Service
class ChangeTitle(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeTitle(entityId: String, changeTitleCommand: ChangeTitleCommand): TitleChangedEvent {
        val titleChangedEvent = TitleChangedEvent(title = changeTitleCommand.title, entityId = entityId)
        gCloudPubSubService.sendMessage(titleChangedEvent, ImmutableMap.of("_kind", EventKind.TITLE_CHANGED))
        return titleChangedEvent
    }
}