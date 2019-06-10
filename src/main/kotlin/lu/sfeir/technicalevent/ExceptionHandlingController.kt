package lu.sfeir.technicalevent

import com.google.firebase.auth.FirebaseAuthException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import java.util.concurrent.ExecutionException


@ControllerAdvice
class ExceptionHandlingController {

    @ExceptionHandler(HttpClientErrorException::class)
    fun unauthorized(e: HttpClientErrorException): ResponseEntity<*> {
        return ResponseEntity(e.message, e.statusCode)
    }

    @ExceptionHandler(ExecutionException::class)
    fun firebaseHandler(e: ExecutionException): ResponseEntity<*> {
        return if (e.message == "com.google.firebase.auth.FirebaseAuthException: Token is not for this app") {
            ResponseEntity(e.message, HttpStatus.FORBIDDEN)
        } else {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }

    }

    @ExceptionHandler(Exception::class)
    fun defaultHandler(e: Exception): ResponseEntity<*> {
        return ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}