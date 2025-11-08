package com.angelhack.babycaremall.frontend.presentation.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class PageController {
    @GetMapping("/admin")
    fun admin(model: Model): String {
        model.addAttribute("message", "Hello Thymeleaf")
        return "admin/index"   // src/main/resources/templates/admin/index.html
    }


    @GetMapping(value = ["/app", "/app/"])
    fun appRoot(): String = "forward:/app/index.html"

    @GetMapping("/app/{path:^(?!index\\.html$|_next|assets|.*\\..*$).*$}/**")
    fun spa(): String = "forward:/app/index.html"

}
