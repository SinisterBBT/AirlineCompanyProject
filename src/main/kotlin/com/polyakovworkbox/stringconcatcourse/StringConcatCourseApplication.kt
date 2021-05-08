package com.polyakovworkbox.stringconcatcourse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StringConcatCourseApplication

fun main(args: Array<String>) {
	@Suppress("SpreadOperator")
	runApplication<StringConcatCourseApplication>(*args)
}
