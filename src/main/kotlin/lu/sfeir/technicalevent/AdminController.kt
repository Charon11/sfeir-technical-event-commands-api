package lu.sfeir.technicalevent

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping


@RestController
class AdminController {
    @RequestMapping("/")
    fun hello(): String {
        return "redirect:swagger-ui.html"
    }
}