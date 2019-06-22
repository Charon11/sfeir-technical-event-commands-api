package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Service

data class TitleChangedEvent(val title: String,
                             override val entityId: String,
                             override val userId: String,
                             override val _kind: String = EventKind.TITLE_CHANGED) : Events()

data class ChangeTitleCommand(val title: String)

@Service
class ChangeTitle(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeTitle(entityId: String, changeTitleCommand: ChangeTitleCommand, token: FirebaseToken): TitleChangedEvent {
        val titleChangedEvent = TitleChangedEvent(title = changeTitleCommand.title, entityId = entityId,
                userId = token.uid)
        gCloudPubSubService.sendMessage(titleChangedEvent, ImmutableMap.of("_kind", titleChangedEvent._kind))
        return titleChangedEvent
    }
}