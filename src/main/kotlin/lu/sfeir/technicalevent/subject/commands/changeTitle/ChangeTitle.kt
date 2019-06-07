package lu.sfeir.technicalevent.subject.commands.changeTitle

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class TitleChangedEvent(val title: String,
                             override val entityId: String,
                             override val _kind: String = EventKind.TITLE_CHANGED) : Events()

data class ChangeTitleCommand(val title: String)

@Component
class ChangeTitle(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeTitle(entityId: String, changeTitleCommand: ChangeTitleCommand): Mono<TitleChangedEvent> {
        return Mono.just(TitleChangedEvent(title = changeTitleCommand.title, entityId = entityId))
                .doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.TITLE_CHANGED)) }
    }
}