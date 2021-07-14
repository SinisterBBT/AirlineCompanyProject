package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.actualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.departureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightTime
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FlightTest {

    val flightId = flightId()

    private val idGenerator = object : FlightIdGenerator {
        override fun generate() = flightId
    }

    @Test
    fun `register flight - success`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val result = Flight.registerFlight(idGenerator, departureAirport, actualArrivalAirport, flightTime)

        result.let {
            it.departureAirport shouldBe departureAirport
            it.actualArrivalAirport shouldBe actualArrivalAirport
            it.flightTime shouldBe flightTime
        }
    }
}