package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalAirport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ArrivalAirportTest {

    @Test
    fun `ArrivalAirport is equal to other ArrivalAirport with the same value`() {
        val firstValue = arrivalAirport()
        val secondValue = arrivalAirport()

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create airport - success`() {
        val airport = ArrivalAirport.from("LED")

        airport shouldBeRight {
            it.iataCode shouldBe "LED"
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "A", "AB", "ABCD", "A_B", "led"])
    fun `create airport - wrong airport name format`(iataCode: String) {
        val airport = ArrivalAirport.from(iataCode)

        airport shouldBeLeft WrongIataCode
    }
}