package com.polyakovworkbox.stringconcatcourse.common.types.common

import com.polyakovworkbox.stringconcatcourse.common.types.airport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AirportTest {

    @Test
    fun `ArrivalAirport is equal to other ArrivalAirport with the same value`() {
        val firstValue = airport()
        val secondValue = airport()

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create airport - success`() {
        val airport = Airport.from("LED")

        airport shouldBeRight {
            it.iataCode shouldBe "LED"
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "A", "AB", "ABCD", "A_B", "led"])
    fun `create airport - wrong airport name format`(iataCode: String) {
        val airport = Airport.from(iataCode)

        airport shouldBeLeft WrongIataCode
    }
}