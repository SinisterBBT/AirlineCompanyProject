package com.polyakovworkbox.stringconcatcourse.maintenance.domain.com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.actualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.departureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightRestorer
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightTime
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FlightRestorerTest {

    @Test
    fun `restore flight - success`() {
        val flightId = flightId()
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()
        val version = version()

        val result = FlightRestorer.restoreFlight(
            flightId,
            departureAirport,
            actualArrivalAirport,
            flightTime,
            version
        )

        result.let {
            it.id shouldBe flightId
            it.departureAirport shouldBe departureAirport
            it.actualArrivalAirport shouldBe actualArrivalAirport
            it.flightTime shouldBe flightTime
        }

    }
}