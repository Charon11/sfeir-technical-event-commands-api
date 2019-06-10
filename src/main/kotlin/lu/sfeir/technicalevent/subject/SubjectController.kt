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

    @GetMapping("/token/{uid}")
    fun token(@PathVariable uid: String): String {
        return firebaseAuthentication.generateToken(uid)
    }

    @PostMapping("/subjects")
    fun add(@RequestBody createCommand: CreateCommand): CreatedEvent {
        return create.create(createCommand)
    }

    @PutMapping("/subjects/{id}/accept")
    fun accept(@PathVariable id: String, @RequestBody acceptCommand: AcceptCommand): Events {
        return accept.accept(id, acceptCommand)
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

    @Throws(HttpClientErrorException::class)
    fun authenticated(token: String?): FirebaseToken {
        if (token.isNullOrEmpty()) throw HttpClientErrorException(HttpStatus.UNAUTHORIZED)
        return firebaseAuthentication.verifyIdToken(token.toString().removePrefix("Bearer "))
    }
}