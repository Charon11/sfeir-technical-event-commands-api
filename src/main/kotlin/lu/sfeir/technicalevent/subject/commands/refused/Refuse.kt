package lu.sfeir.technicalevent.subject.commands.refused

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import lu.sfeir.technicalevent.subject.SubjectStatus
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

data class RefusedEvent(val status: String,
                        override val entityId: String,
                        override val _kind: String = EventKind.REFUSED) : Events()

@Component
class Refuse(private val gCloudPubSubService: GCloudPubSubService) {
    fun refuse(entityId: String): Mono<RefusedEvent> {
       return Mono.just(RefusedEvent(SubjectStatus.REFUSED, entityId = entityId))
            .doOnSuccess { t -> gCloudPubSubService.sendMessage(t, ImmutableMap.of("_kind", EventKind.REFUSED)) }
    }
}