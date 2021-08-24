package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightState
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flightId
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flightTime
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class MarkFlightArrivedUseCaseTest {

    @Test
    fun `successfully marked as arrived`() {
        val flightId = flightId()
        val flightTime = flightTime()

        val result = MarkFlightArrivedUseCase(
            TestFlightExtractor
        ).markArrived(
            flightId,
            flightTime
        )

        result shouldBeRight {
            it.state shouldBe FlightState.ARRIVED
        }
    }

    @Test
    fun `flight to mark arrived is not found`() {
        val flightId = flightId()
        val flightTime = flightTime()

        val result = MarkFlightArrivedUseCase(
            TestFlightNotFoundExtractor
        ).markArrived(
            flightId,
            flightTime
        )

        result shouldBeLeft MarkFlightArrivedCaseError.FlightNotFoundError
    }
}

object TestFlightExtractor : FlightExtractor {
    override fun getByFlightId(flightId: FlightId): Flight = flight()
}

object TestFlightNotFoundExtractor : FlightExtractor {
    override fun getByFlightId(flightId: FlightId): Flight? = null
}