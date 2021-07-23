package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.seatCount
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val aircraftId = aircraftId()
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatMap = seatCount()
        val version = version()

        val result = AircraftRestorer.restoreAircraft(
            aircraftId,
            registrationNumber,
            model,
            seatMap,
            version
        )

        result.let {
            it.id shouldBe aircraftId
            it.registrationNumber shouldBe registrationNumber
            it.model shouldBe model
            it.seatCount shouldBe seatMap
        }
    }
}