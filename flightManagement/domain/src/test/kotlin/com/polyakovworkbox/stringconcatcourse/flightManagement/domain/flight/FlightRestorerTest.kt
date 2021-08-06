package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FlightRestorerTest {

    @Test
    fun `restore flight - success`() {
        val flightId = flightId()
        val departureAirport = departureAirport()
        val arrivalAirport = arrivalAirport()
        val departureDate = departureDate()
        val arrivalDate = arrivalDate()
        val aircraftId = aircraftId()
        val version = version()

        val result = FlightRestorer.restoreFlight(
            flightId,
            departureAirport,
            arrivalAirport,
            departureDate,
            arrivalDate,
            aircraftId,
            version
        )

        result.id shouldBe flightId
        result.departureAirport shouldBe departureAirport
        result.arrivalAirport shouldBe arrivalAirport
        result.departureDate shouldBe departureDate
        result.arrivalDate shouldBe arrivalDate
        result.aircraftId shouldBe aircraftId
    }
}