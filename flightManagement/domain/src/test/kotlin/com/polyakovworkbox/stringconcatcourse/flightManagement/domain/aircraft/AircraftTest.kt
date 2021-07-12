package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import aircraftModel
import aircraftId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import registrationNumber
import seatMap

internal class AircraftTest {

    val aircraftId = aircraftId()

    private val idGenerator = object : AircraftIdGenerator {
        override fun generate() = aircraftId
    }

    @Test
    fun `acquire info about new aircraft - success`() {
        val registrationNumber = registrationNumber()
        val model = aircraftModel()
        val seatMap = seatMap()

        val result = Aircraft.acquireNewAircraft(
                idGenerator,
                registrationNumber,
                model,
                seatMap
        )

        result.let {
            it.id shouldBe aircraftId
            it.model shouldBe model
            it.seatMap shouldBe seatMap
        }
    }
}