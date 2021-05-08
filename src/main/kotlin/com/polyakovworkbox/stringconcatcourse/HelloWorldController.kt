package com.polyakovworkbox.stringconcatcourse

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @RequestMapping("/")
    fun getHelloWorld(): String {
        @Suppress("FunctionOnlyReturningConstant")
        return "ci vis pacem para bellum"
    }

}
