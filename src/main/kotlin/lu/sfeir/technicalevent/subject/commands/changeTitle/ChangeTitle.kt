package lu.sfeir.technicalevent.subject.commands.changeTitle

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.EventsRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class TitleChangedEvent(val title: String,
                             override val entityId: String,
                             override val _kind: String = EventKind.TITLE_CHANGED) : Events()

data class ChangeTitleCommand(val title: String)

@Component
class ChangeTitle(private val eventsRepository: EventsRepository,
                  private val gCloudPubSubService: GCloudPubSubService) {
    fun changeTitle(entityId: String, changeTitleCommand: ChangeTitleCommand): Mono<TitleChangedEvent> {
        val result = eventsRepository.save(TitleChangedEvent(title = changeTitleCommand.title, entityId = entityId))
        return result.doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.TITLE_CHANGED)) }
    }
}