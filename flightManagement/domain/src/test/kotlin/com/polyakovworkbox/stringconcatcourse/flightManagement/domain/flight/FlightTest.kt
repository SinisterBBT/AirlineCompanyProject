package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AircraftDoesNotExist
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AircraftIsInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AircraftIsNotInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.AirportDoesNotAllowFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flightId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FlightTest {

    val flightId = flightId()

    private val idGenerator = object : FlightIdGenerator {
        override fun generate() = flightId
    }

    @Test
    fun `announce new flight - success`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraft = aircraft()

        val newFlight = Flight.announceNewFlight(
            idGenerator,
            AircraftIsInOperation,
            AircraftIsNotInFlight,
            AirportAllowsFlight,
            departureAirport,
            arrivalAirport,
            departureDate,
            arrivalDate,
            aircraft)

        newFlight shouldBeRight {
            it.departureAirport shouldBe departureAirport
            it.arrivalAirport shouldBe arrivalAirport
            it.departureDate shouldBe departureDate
            it.arrivalDate shouldBe arrivalDate
        }
    }

    @Test
    fun `announce new flight - aircraft is unknown`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraft = aircraft()

        val newFlight = Flight.announceNewFlight(
                idGenerator,
                AircraftDoesNotExist,
                AircraftIsNotInFlight,
                AirportAllowsFlight,
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraft)

        newFlight shouldBeLeft CannotAnnounceFlightError.AircraftIsNotInOperationError
    }

    @Test
    fun `announce new flight - aircraft is already in flight`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraft = aircraft()

        val newFlight = Flight.announceNewFlight(
                idGenerator,
                AircraftIsInOperation,
                AircraftIsAlreadyInFlight,
                AirportAllowsFlight,
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraft)

        newFlight shouldBeLeft CannotAnnounceFlightError.AircraftIsAlreadyInFlightError
    }

    @Test
    fun `announce new flight - airport declines our flight request`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraft = aircraft()

        val newFlight = Flight.announceNewFlight(
                idGenerator,
                AircraftIsInOperation,
                AircraftIsNotInFlight,
                AirportDoesNotAllowFlight,
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraft)

        newFlight shouldBeLeft CannotAnnounceFlightError.AirportDoesNotAllowFlightError
    }
}