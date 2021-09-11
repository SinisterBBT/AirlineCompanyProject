package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.TestFlightPersister
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.actualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.departureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flightId
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flightTime
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RegisterFlightUseCaseTest {

    @Test
    fun `successfully registered`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val flightPersister = TestFlightPersister()
        val id = TestFlightIdGenerator.id

        val result = RegisterFlightUseCase(
            flightPersister,
            TestFlightIdGenerator
        ).execute(
            RegisterFlightRequest(
                departureAirport,
                actualArrivalAirport,
                flightTime
            )
        )

        result.let {
            it.id shouldBe id
            it.departureAirport shouldBe departureAirport
            it.actualArrivalAirport shouldBe actualArrivalAirport
            it.flightTime shouldBe flightTime
        }
    }
}

object TestFlightIdGenerator : FlightIdGenerator {
    val id = flightId()
    override fun generate() = id
}