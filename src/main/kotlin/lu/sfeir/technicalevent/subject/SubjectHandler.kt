package lu.sfeir.technicalevent.subject

import lu.sfeir.technicalevent.subject.commands.accept.Accept
import lu.sfeir.technicalevent.subject.commands.changeDescription.ChangeDescription
import lu.sfeir.technicalevent.subject.commands.changeDescription.ChangeDescriptionCommand
import lu.sfeir.technicalevent.subject.commands.changeTitle.ChangeTitle
import lu.sfeir.technicalevent.subject.commands.changeTitle.ChangeTitleCommand
import lu.sfeir.technicalevent.subject.commands.create.Create
import lu.sfeir.technicalevent.subject.commands.create.CreateCommand
import lu.sfeir.technicalevent.subject.commands.delete.Delete
import lu.sfeir.technicalevent.subject.commands.refused.Refuse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class SubjectEventHandler(private val create: Create,
                          private val changeDescription: ChangeDescription,
                          private val accept: Accept,
                          private val delete: Delete,
                          private val refuse: Refuse,
                          private val changeTitle: ChangeTitle) {
    fun add(request: ServerRequest): Mono<ServerResponse> {
        val subject = request.bodyToMono<CreateCommand>()
        return subject.flatMap { s -> ok().body(create.create(s)) }
    }

    fun changeDescription(request: ServerRequest): Mono<ServerResponse> {
        val description = request.bodyToMono<ChangeDescriptionCommand>()
        val id = request.pathVariable("id")
        return description.flatMap { d ->
            ok().body(changeDescription.changeDescription(id, d))
        }
    }
    fun changeTitle(request: ServerRequest): Mono<ServerResponse> {
        val title = request.bodyToMono<ChangeTitleCommand>()
        val id = request.pathVariable("id")
        return title.flatMap { t ->
            ok().body(changeTitle.changeTitle(id, t))
        }
    }

    fun accept(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return ok().body(accept.accept(id))
    }

    fun refuse(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return ok().body(refuse.refuse(id))
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        val id = request.pathVariable("id")
        return ok().body(delete.delete(id))
    }
}