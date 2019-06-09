package lu.sfeir.technicalevent.subject

import lu.sfeir.technicalevent.subject.commands.*
import org.springframework.web.bind.annotation.*

@RestController
class SubjectController(private val create: Create,
                        private val changeDescription: ChangeDescription,
                        private val changeSchedules: ChangeSchedule,
                        private val accept: Accept,
                        private val delete: Delete,
                        private val refuse: Refuse,
                        private val changeTitle: ChangeTitle) {

    @PostMapping("/subjects")
    fun add(@RequestBody createCommand: CreateCommand): CreatedEvent {
        return create.create(createCommand)
    }

    @PutMapping("/subjects/{id}/accept")
    fun accept(@PathVariable id: String): AcceptedEvent {
        return accept.accept(id)
    }

    @PutMapping("/subjects/{id}/refuse")
    fun refuse(@PathVariable id: String): RefusedEvent {
        return refuse.refuse(id)
    }
    @PutMapping("/subjects/{id}/delete")
    fun delete(@PathVariable id: String): DeletedEvent {
        return delete.delete(id)
    }
    @PutMapping("/subjects/{id}/change-title")
    fun changeTitle(@PathVariable id: String, @RequestBody changeTitleCommand: ChangeTitleCommand): TitleChangedEvent {
        return changeTitle.changeTitle(id, changeTitleCommand)
    }

    @PutMapping("/subjects/{id}/change-description")
    fun changeDescription(@PathVariable id: String, @RequestBody changeDescriptionCommand: ChangeDescriptionCommand): DescriptionChangedEvent {
        return changeDescription.changeDescription(id, changeDescriptionCommand)
    }

    @PutMapping("/subjects/{id}/change-schedules")
    fun changeSchedules(@PathVariable id: String, @RequestBody changeSchedulesCommand: ChangeSchedulesCommand): ScheduleChangedEvent {
        return changeSchedules.changeSchedules(id, changeSchedulesCommand)
    }

}