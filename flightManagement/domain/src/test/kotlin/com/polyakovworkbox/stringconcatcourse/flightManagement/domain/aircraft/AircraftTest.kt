package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.seatCount
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class AircraftTest {

    val aircraftId = aircraftId()

    private val idGenerator = object : AircraftIdGenerator {
        override fun generate() = aircraftId
    }

    @Test
    fun `acquire info about new aircraft - success`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatMap = seatCount()

        val result = Aircraft.receiveNewAircraftInfo(
                idGenerator,
                registrationNumber,
                model,
                seatMap
        )

        result.let {
            it.id shouldBe aircraftId
            it.model shouldBe model
            it.seatCount shouldBe seatMap
        }
    }
}