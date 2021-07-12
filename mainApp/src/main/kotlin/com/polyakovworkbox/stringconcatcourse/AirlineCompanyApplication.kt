package com.polyakovworkbox.stringconcatcourse

import com.polyakovworkbox.stringconcatcourse.configuration.ApplicationConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApplicationConfiguration::class)
class AirlineCompanyApplication

fun main(args: Array<String>) {
	runApplication<AirlineCompanyApplication>(*args)
}
