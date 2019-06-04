package lu.sfeir.technicalevent.subject

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class SubjectRouting {

    @Bean
    fun subjectRouter(subjectCommandHandler: SubjectEventHandler) = router {
        ("/subject" and accept(MediaType.APPLICATION_JSON)).nest {
            POST("/", subjectCommandHandler::add)
            PUT("/{id}/change-description", subjectCommandHandler::changeDescription)
            PUT("/{id}/change-title", subjectCommandHandler::changeTitle)
            PUT("/{id}/accept", subjectCommandHandler::accept)
            PUT("/{id}/refuse", subjectCommandHandler::refuse)
            PUT("/{id}/delete", subjectCommandHandler::delete)
        }
    }
}