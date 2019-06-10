package lu.sfeir.technicalevent

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException


@ControllerAdvice
class ExceptionHandlingController {

    @ExceptionHandler(HttpClientErrorException::class)
    fun unauthorized(e: HttpClientErrorException): ResponseEntity<*> {
        return ResponseEntity(e.message, e.statusCode)
    }

    @ExceptionHandler(Exception::class)
    fun defaultHandler(e: Exception): ResponseEntity<*> {
        return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}