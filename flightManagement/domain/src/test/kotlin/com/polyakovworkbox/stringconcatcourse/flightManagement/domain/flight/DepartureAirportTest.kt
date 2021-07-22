package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureAirport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class DepartureAirportTest {

    @Test
    fun `DepartureAirport is equal to other DepartureAirport with the same value`() {
        val firstValue = departureAirport()
        val secondValue = departureAirport()

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create airport - success`() {
        val airport = DepartureAirport.from("LED")

        airport shouldBeRight {
            it.iataCode shouldBe "LED"
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "A", "AB", "ABCD", "A_B", "led"])
    fun `create airport - wrong airport name format`(iataCode: String) {
        val airport = DepartureAirport.from(iataCode)

        airport shouldBeLeft WrongIataCode
    }
}