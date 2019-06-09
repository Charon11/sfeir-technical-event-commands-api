package lu.sfeir.technicalevent

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping



@RestController
class AdminController {
    @GetMapping("/")
    fun hello(): String {
        return "Hello Sfeir Technical Events"
    }
}