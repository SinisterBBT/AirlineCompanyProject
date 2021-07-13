package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import aircraft
import arrivalAirport
import arrivalDate
import departureAirport
import departureDate
import flightId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import version

class FlightRestorerTest {

    @Test
    fun `restore flight - success`() {
        val flightId = flightId()
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraft = aircraft()
        val version = version()

        val result = FlightRestorer.restoreFlight(
            flightId,
            departureAirport,
            arrivalAirport,
            departureDate,
            arrivalDate,
            aircraft,
            version
        )

        result.id shouldBe flightId
        result.departureAirport shouldBe departureAirport
        result.arrivalAirport shouldBe arrivalAirport
        result.departureDate shouldBe departureDate
        result.arrivalDate shouldBe arrivalDate
        result.aircraft shouldBe aircraft
    }
}