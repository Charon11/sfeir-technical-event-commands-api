package lu.sfeir.technicalevent.subject.commands.changeTitle

import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class TitleChangedEvent(val title: String,
                             override val entityId: String) : Events()

data class ChangeTitleCommand(val title: String)

@Component
class ChangeTitle(private val eventsRepository: EventsRepository,
                  private val titleChangedPubSubService: GCloudTitleChangedPubSubService) {
    fun changeTitle(entityId: String, changeTitleCommand: ChangeTitleCommand): Mono<TitleChangedEvent> {
        val event = TitleChangedEvent(title = changeTitleCommand.title, entityId = entityId)
        event.copy(entityId = entityId)
        val result = eventsRepository.save(event)
        return result.doOnSuccess { t -> titleChangedPubSubService.sendMessage(t) }
    }
}