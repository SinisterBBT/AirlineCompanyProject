package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import aircraftId
import aircraftModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import registrationNumber
import seatMap
import version

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val aircraftId = aircraftId()
        val registrationNumber = registrationNumber()
        val model = aircraftModel()
        val seatMap = seatMap()
        val version = version()

        val result = AircraftRestorer.restoreAircraft(
            aircraftId,
            registrationNumber,
            model,
            seatMap,
            version
        )

        result.id shouldBe aircraftId
        result.registrationNumber shouldBe registrationNumber
        result.model shouldBe model
        result.seatMap shouldBe seatMap
    }
}