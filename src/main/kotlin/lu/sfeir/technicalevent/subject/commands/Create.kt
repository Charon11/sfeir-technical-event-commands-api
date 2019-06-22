package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import com.google.firebase.auth.FirebaseToken
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import lu.sfeir.technicalevent.subject.SubjectType
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

data class CreatedEvent(val title: String,
                        val description: String? = null,
                        val subjectType: SubjectType,
                        val schedules: List<Instant>? = emptyList(),
                        val userName: String,
                        override val entityId: String = UUID.randomUUID().toString(),
                        override val userId: String,
                        override val _kind: String = EventKind.CREATED) : Events() {
    val status: String = SubjectStatus.NEW
}

data class CreateCommand(val title: String,
                         val description: String? = null,
                         val subjectType: SubjectType,
                         val schedules: List<Instant>? = emptyList())

@Service
class Create(private val gCloudPubSubService: GCloudPubSubService) {
    fun create(createCommand: CreateCommand, token: FirebaseToken): CreatedEvent {
        val createdEvent = CreatedEvent(
                title = createCommand.title,
                description = createCommand.description,
                subjectType = createCommand.subjectType,
                schedules = createCommand.schedules,
                userId = token.uid,
                userName = token.name)
        gCloudPubSubService.sendMessage(createdEvent, ImmutableMap.of("_kind", createdEvent._kind))
        return createdEvent
    }
}

