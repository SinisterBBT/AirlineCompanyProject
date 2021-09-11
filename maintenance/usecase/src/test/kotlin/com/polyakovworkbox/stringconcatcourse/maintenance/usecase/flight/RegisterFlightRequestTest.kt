package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.actualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.departureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flightTime
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Duration

internal class RegisterFlightRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val result = RegisterFlightRequest.from(
            departureAirport.iataCode,
            actualArrivalAirport.iataCode,
            flightTime.flightTime
        )

        val result2 = RegisterFlightRequest.from(
            departureAirport.iataCode,
            actualArrivalAirport.iataCode,
            flightTime.flightTime
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val result = RegisterFlightRequest.from(
            departureAirport.iataCode,
            actualArrivalAirport.iataCode,
            flightTime.flightTime
        )

        val compareToInstance = RegisterFlightRequest(
            departureAirport,
            actualArrivalAirport,
            flightTime
        )

        result shouldBeRight {
            it.departureAirport shouldBe compareToInstance.departureAirport
            it.actualArrivalAirport shouldBe compareToInstance.actualArrivalAirport
            it.flightTime shouldBe compareToInstance.flightTime
        }
    }

    @Test
    fun `has wrong iata code of departure airport`() {
        val departureAirport = ""
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val result = RegisterFlightRequest.from(
            departureAirport,
            actualArrivalAirport.iataCode,
            flightTime.flightTime
        )

        result shouldBeLeft InvalidFlightParameters("Iata code has incorrect format")
    }

    @Test
    fun `has wrong iata code of actual arrival airport`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = ""
        val flightTime = flightTime()

        val result = RegisterFlightRequest.from(
            departureAirport.iataCode,
            actualArrivalAirport,
            flightTime.flightTime
        )

        result shouldBeLeft InvalidFlightParameters("Iata code has incorrect format")
    }

    @Test
    fun `has negative flight duration`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = Duration.ofHours(-5)

        val result = RegisterFlightRequest.from(
            departureAirport.iataCode,
            actualArrivalAirport.iataCode,
            flightTime
        )

        result shouldBeLeft InvalidFlightParameters("Flight time cannot be negative")
    }
}