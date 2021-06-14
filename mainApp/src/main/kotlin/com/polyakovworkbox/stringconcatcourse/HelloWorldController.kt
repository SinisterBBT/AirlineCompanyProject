package com.polyakovworkbox.stringconcatcourse

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @RequestMapping("/")
    fun getHelloWorld(): String {
        val content = "ci vis pacem " + "para bellum"
        return content
    }

}
