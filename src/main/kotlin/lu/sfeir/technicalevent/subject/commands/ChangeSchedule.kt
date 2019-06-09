package lu.sfeir.technicalevent.subject.commands

import com.google.common.collect.ImmutableMap
import lu.sfeir.technicalevent.gcloud.GCloudPubSubService
import lu.sfeir.technicalevent.subject.EventKind
import lu.sfeir.technicalevent.subject.Events
import org.springframework.stereotype.Service
import java.time.Instant


data class ScheduleChangedEvent(val schedules: List<Instant>,
                                override val entityId: String,
                                override val _kind: String = EventKind.SCHEDULES_CHANGED) : Events()

data class ChangeSchedulesCommand(val schedules: List<Instant>)

@Service
class ChangeSchedule(private val gCloudPubSubService: GCloudPubSubService) {
    fun changeSchedules(entityId: String, changeSchedulesCommand: ChangeSchedulesCommand) : ScheduleChangedEvent {
        val shechuleChangedEvent = ScheduleChangedEvent(changeSchedulesCommand.schedules, entityId)
        gCloudPubSubService.sendMessage(shechuleChangedEvent, ImmutableMap.of("_kind", shechuleChangedEvent._kind))
        return shechuleChangedEvent
    }
}