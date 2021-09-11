package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.arrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.arrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.departureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.departureDate
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class AnnounceNewFlightRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport.iataCode,
            departureDate.departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        val result2 = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport.iataCode,
            departureDate.departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport.iataCode,
            departureDate.departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        val compareToInstance = AnnounceNewFlightRequest(
            departureAirport,
            arrivalAirport,
            departureDate,
            arrivalDate,
            aircraftId
        )

        result shouldBeRight {
            it.departureAirport shouldBe compareToInstance.departureAirport
            it.arrivalAirport shouldBe compareToInstance.arrivalAirport
            it.departureDate shouldBe compareToInstance.departureDate
            it.arrivalDate shouldBe compareToInstance.arrivalDate
            it.aircraftId shouldBe compareToInstance.aircraftId
        }
    }

    @Test
    fun `has wrong iata code of departure airport`() {
        val departureAirport = ""
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport,
            arrivalAirport.iataCode,
            departureDate.departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        result shouldBeLeft InvalidFlightParameters("Iata code has incorrect format")
    }

    @Test
    fun `has wrong iata code of arrival airport`() {
        val departureAirport = departureAirport()
        val arrivalAirport = ""
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport,
            departureDate.departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        result shouldBeLeft InvalidFlightParameters("Iata code has incorrect format")
    }

    @Test
    fun `has departure date in the past`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = ZonedDateTime.now().minusDays(5)
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport.iataCode,
            departureDate,
            arrivalDate.arrivalDate,
            aircraftId.value
        )

        result shouldBeLeft InvalidFlightParameters("Departure date time cannot be in the past")
    }

    @Test
    fun `has arrival date in the past`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = ZonedDateTime.now().minusDays(5)
        val aircraftId = aircraftId()

        val result = AnnounceNewFlightRequest.from(
            departureAirport.iataCode,
            arrivalAirport.iataCode,
            departureDate.departureDate,
            arrivalDate,
            aircraftId.value
        )

        result shouldBeLeft InvalidFlightParameters("Arrival date time cannot be in the past")
    }
}