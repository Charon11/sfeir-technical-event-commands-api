package lu.sfeir.technicalevent.subject.commands.create

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

data class CreatedEvent(val title: String,
                        val description: String? = null,
                        override val entityId: String = UUID.randomUUID().toString()) : Events()  {
    val status: String = SubjectStatus.NEW
}

data class CreateCommand(val title: String,
                         val description: String? = null)

@Component
class Create(private val eventsRepository: EventsRepository,
             private val createdPubSubService: GCloudCreatedPubSubService) {
    fun create(createCommand: CreateCommand): Mono<CreatedEvent> {
        val result = eventsRepository.save(CreatedEvent(title = createCommand.title, description = createCommand.description))
        return result.doOnSuccess { t -> createdPubSubService.sendMessage(t) }
    }
}

