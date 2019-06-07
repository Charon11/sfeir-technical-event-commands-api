package lu.sfeir.technicalevent.subject

import lu.sfeir.technicalevent.subject.commands.accept.Accept
import lu.sfeir.technicalevent.subject.commands.accept.AcceptedEvent
import lu.sfeir.technicalevent.subject.commands.changeDescription.ChangeDescription
import lu.sfeir.technicalevent.subject.commands.changeDescription.ChangeDescriptionCommand
import lu.sfeir.technicalevent.subject.commands.changeDescription.DescriptionChangedEvent
import lu.sfeir.technicalevent.subject.commands.changeTitle.ChangeTitle
import lu.sfeir.technicalevent.subject.commands.changeTitle.ChangeTitleCommand
import lu.sfeir.technicalevent.subject.commands.changeTitle.TitleChangedEvent
import lu.sfeir.technicalevent.subject.commands.create.Create
import lu.sfeir.technicalevent.subject.commands.create.CreateCommand
import lu.sfeir.technicalevent.subject.commands.create.CreatedEvent
import lu.sfeir.technicalevent.subject.commands.delete.Delete
import lu.sfeir.technicalevent.subject.commands.delete.DeletedEvent
import lu.sfeir.technicalevent.subject.commands.refused.Refuse
import lu.sfeir.technicalevent.subject.commands.refused.RefusedEvent
import org.springframework.web.bind.annotation.*

@RestController("/subject")
class SubjectController(private val create: Create,
                        private val changeDescription: ChangeDescription,
                        private val accept: Accept,
                        private val delete: Delete,
                        private val refuse: Refuse,
                        private val changeTitle: ChangeTitle) {

    @PostMapping
    fun add(@RequestBody createCommand: CreateCommand): CreatedEvent {
        return create.create(createCommand)
    }

    @PutMapping("/{id}/accept")
    fun accept(@PathVariable id: String): AcceptedEvent {
        return accept.accept(id)
    }

    @PutMapping("/{id}/refuse")
    fun refuse(@PathVariable id: String): RefusedEvent {
        return refuse.refuse(id)
    }
    @DeleteMapping("/{id}/delete")
    fun delete(@PathVariable id: String): DeletedEvent {
        return delete.delete(id)
    }
    @PutMapping("/{id}/change-title")
    fun changeTitle(@PathVariable id: String, @RequestBody changeTitleCommand: ChangeTitleCommand): TitleChangedEvent {
        return changeTitle.changeTitle(id, changeTitleCommand)
    }

    @PutMapping("/{id}/change-description")
    fun changeDescription(@PathVariable id: String, @RequestBody changeDescriptionCommand: ChangeDescriptionCommand): DescriptionChangedEvent {
        return changeDescription.changeDescription(id, changeDescriptionCommand)
    }

}