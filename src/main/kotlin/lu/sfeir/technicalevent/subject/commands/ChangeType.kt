package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectType
import org.springframework.stereotype.Service

data class TypeChangedEvent(val subjectType: SubjectType,
                            override val entityId: String,
                            override val userId: String,
                            override val _kind: String = EventKind.TYPE_CHANGED): Events()

data class ChangeTypeCommand(val subjectType: SubjectType)

@Service
class ChangeType(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeType(entityId: String, changeTypeCommand: ChangeTypeCommand, token: FirebaseToken): TypeChangedEvent {
        val typeChangedEvent = TypeChangedEvent(
                subjectType = changeTypeCommand.subjectType,
                entityId = entityId,
                userId = token.uid
        )
        gCloudPubSubService.sendMessage(typeChangedEvent, ImmutableMap.of("_kind", typeChangedEvent._kind))
        return typeChangedEvent
    }
}