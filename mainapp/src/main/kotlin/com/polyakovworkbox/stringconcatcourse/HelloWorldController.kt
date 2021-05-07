package com.polyakovworkbox.stringconcatcourse

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @RequestMapping("/helloworld")
    fun getHelloWorld() : String {
        return "Hello world!"
    }

}