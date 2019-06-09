package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Service
import java.util.*

data class CreatedEvent(val title: String,
                        val description: String? = null,
                        override val entityId: String = UUID.randomUUID().toString(),
                        override val _kind: String = EventKind.CREATED) : Events()  {
    val status: String = SubjectStatus.NEW
}

data class CreateCommand(val title: String,
                         val description: String? = null)

@Service
class Create(private val gCloudPubSubService: GCloudPubSubService) {
    fun create(createCommand: CreateCommand): CreatedEvent {
        val createdEvent = CreatedEvent(title = createCommand.title, description = createCommand.description)
        gCloudPubSubService.sendMessage(createdEvent, ImmutableMap.of("_kind", createdEvent._kind))
        return createdEvent
    }
}

