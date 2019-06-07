package lu.sfeir.technicalevent.subject.commands.accept

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class AcceptedEvent(val status: String,
                         override val entityId: String,
                         override val _kind: String = EventKind.ACCEPTED) : Events()

@Component
class Accept(private val gCloudPubSubService: GCloudPubSubService) {
    fun accept(entityId: String): Mono<AcceptedEvent> {
        return Mono.just(AcceptedEvent(SubjectStatus.ACCEPTED, entityId = entityId))
                .doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.ACCEPTED)) }
    }
}