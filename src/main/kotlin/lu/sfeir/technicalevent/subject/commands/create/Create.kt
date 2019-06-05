package lu.sfeir.technicalevent.subject.commands.create

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

data class CreatedEvent(val title: String,
                        val description: String? = null,
                        override val entityId: String = UUID.randomUUID().toString(),
                        override val _kind: String = EventKind.CREATED) : Events()  {
    val status: String = SubjectStatus.NEW
}

data class CreateCommand(val title: String,
                         val description: String? = null)

@Component
class Create(private val eventsRepository: EventsRepository,
             private val gCloudPubSubService: GCloudPubSubService) {
    fun create(createCommand: CreateCommand): Mono<CreatedEvent> {
        val result = eventsRepository.save(CreatedEvent(title = createCommand.title, description = createCommand.description))
        return result.doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.CREATED)) }
    }
}

