package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.TestFlightPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.arrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.arrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.departureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.departureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flightId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class AnnounceNewFlightUseCaseTest {

    @Test
    fun `successfully added`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val flightPersister = TestFlightPersister()
        val id = TestFlightIdGenerator.id

        val result = AnnounceNewFlightUseCase(
            flightPersister,
            TestFlightIdGenerator,
            AircraftIsNotInFlight,
            AircraftIsInOperation,
            AirportAllowsFlight
        ).execute(
            AnnounceNewFlightRequest(
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraftId
            )
        )

        result shouldBeRight {
            it.id shouldBe id
            it.departureAirport shouldBe departureAirport
            it.arrivalAirport shouldBe arrivalAirport
            it.departureDate shouldBe departureDate
            it.arrivalDate shouldBe arrivalDate
            it.aircraftId shouldBe aircraftId
        }

        val flight = flightPersister[id]
        flight.shouldNotBeNull()
    }

    @Test
    fun `aircraft already in flight`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val flightPersister = TestFlightPersister()

        val result = AnnounceNewFlightUseCase(
            flightPersister,
            TestFlightIdGenerator,
            AircraftIsAlreadyInFlight,
            AircraftIsInOperation,
            AirportAllowsFlight
        ).execute(
            AnnounceNewFlightRequest(
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraftId
            )
        )

        result shouldBeLeft AnnounceNewFlightCaseError.AircraftIsAlreadyInFlightError
        flightPersister.shouldBeEmpty()
    }

    @Test
    fun `aircraft is not in operation`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val flightPersister = TestFlightPersister()

        val result = AnnounceNewFlightUseCase(
            flightPersister,
            TestFlightIdGenerator,
            AircraftIsNotInFlight,
            AircraftIsNotInOperation,
            AirportAllowsFlight
        ).execute(
            AnnounceNewFlightRequest(
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraftId
            )
        )

        result shouldBeLeft AnnounceNewFlightCaseError.AircraftIsNotInOperationError
        flightPersister.shouldBeEmpty()
    }

    @Test
    fun `airport does not allow flight`() {
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()

        val flightPersister = TestFlightPersister()

        val result = AnnounceNewFlightUseCase(
            flightPersister,
            TestFlightIdGenerator,
            AircraftIsNotInFlight,
            AircraftIsInOperation,
            AirportDoesNotAllowFlight
        ).execute(
            AnnounceNewFlightRequest(
                departureAirport,
                arrivalAirport,
                departureDate,
                arrivalDate,
                aircraftId
            )
        )

        result shouldBeLeft AnnounceNewFlightCaseError.AirportDoesNotAllowFlightError
        flightPersister.shouldBeEmpty()
    }
}

object TestFlightIdGenerator : FlightIdGenerator {
    val id = flightId()
    override fun generate() = id
}

object AircraftIsAlreadyInFlight : AircraftIsAlreadyInFlight {
    override fun check(aircraftId: AircraftId) = true
}

object AircraftIsNotInFlight : AircraftIsAlreadyInFlight {
    override fun check(aircraftId: AircraftId) = false
}

object AircraftIsInOperation : AircraftIsNotInOperation {
    override fun check(aircraftId: AircraftId) = true
}

object AircraftIsNotInOperation : AircraftIsNotInOperation {
    override fun check(aircraftId: AircraftId) = false
}

object AirportAllowsFlight : AirportAllowsFlight {
    override fun check(departureDate: DepartureDate) = true
}

object AirportDoesNotAllowFlight : AirportAllowsFlight {
    override fun check(departureDate: DepartureDate) = false
}