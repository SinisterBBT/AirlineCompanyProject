package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.actualArrivalAirport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ActualArrivalAirportTest {

    @Test
    fun `ActualArrivalAirport is equal to other ActualArrivalAirport with the same value`() {
        val actualArrivalAirport = "MSK"
        val firstValue = actualArrivalAirport(actualArrivalAirport)
        val secondValue = actualArrivalAirport(actualArrivalAirport)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create airport - success`() {
        val airport = ActualArrivalAirport.from("LED")

        airport shouldBeRight {
            it.iataCode shouldBe "LED"
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "A", "AB", "ABCD", "A_B", "led"])
    fun `create airport - wrong airport name format`(iataCode: String) {
        val airport = ActualArrivalAirport.from(iataCode)

        airport shouldBeLeft WrongIataCode
    }
}