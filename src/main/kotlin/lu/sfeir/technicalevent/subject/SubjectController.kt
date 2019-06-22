package lu.sfeir.technicalevent.subject

import com.google.firebase.auth.FirebaseToken
import com.google.firebase.tasks.Tasks
import lu.sfeir.technicalevent.firebase.FirebaseAuthentication
import lu.sfeir.technicalevent.subject.commands.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.toMono
import reactor.core.publisher.whenComplete

@RestController
class SubjectController(private val firebaseAuthentication: FirebaseAuthentication,
                        private val create: Create,
                        private val changeDescription: ChangeDescription,
                        private val changeSchedules: ChangeSchedule,
                        private val accept: Accept,
                        private val delete: Delete,
                        private val refuse: Refuse,
                        private val changeTitle: ChangeTitle) {

    @GetMapping("/auth")
    fun auth(@RequestHeader(value = "Authorization", required = false) token: String?): String {
        return authenticated(token).name
    }

    @PostMapping("/subjects")
    fun add(@RequestHeader(value = "Authorization", required = false) token: String?, @RequestBody createCommand: CreateCommand): CreatedEvent {
        return create.create(createCommand, authenticated(token))
    }

    @PutMapping("/subjects/{id}/accept")
    fun accept(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String, @RequestBody acceptCommand: AcceptCommand): Events {
        return accept.accept(id, acceptCommand, authenticated(token))
    }

    @PutMapping("/subjects/{id}/refuse")
    fun refuse(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String): RefusedEvent {
        return refuse.refuse(id, authenticated(token))
    }

    @PutMapping("/subjects/{id}/delete")
    fun delete(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String): DeletedEvent {
        return delete.delete(id, authenticated(token))
    }

    @PutMapping("/subjects/{id}/change-title")
    fun changeTitle(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String, @RequestBody changeTitleCommand: ChangeTitleCommand): TitleChangedEvent {
        return changeTitle.changeTitle(id, changeTitleCommand, authenticated(token))
    }

    @PutMapping("/subjects/{id}/change-description")
    fun changeDescription(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String, @RequestBody changeDescriptionCommand: ChangeDescriptionCommand): DescriptionChangedEvent {
        return changeDescription.changeDescription(id, changeDescriptionCommand, authenticated(token))
    }

    @PutMapping("/subjects/{id}/change-schedules")
    fun changeSchedules(@RequestHeader(value = "Authorization", required = false) token: String?, @PathVariable id: String, @RequestBody changeSchedulesCommand: ChangeSchedulesCommand): ScheduleChangedEvent {
        return changeSchedules.changeSchedules(id, changeSchedulesCommand, authenticated(token))
    }

    @Throws(HttpClientErrorException::class)
    fun authenticated(token: String?): FirebaseToken {
        if (token.isNullOrEmpty()) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
        return firebaseAuthentication.verifyIdToken(token.toString().removePrefix("Bearer "))
    }
}