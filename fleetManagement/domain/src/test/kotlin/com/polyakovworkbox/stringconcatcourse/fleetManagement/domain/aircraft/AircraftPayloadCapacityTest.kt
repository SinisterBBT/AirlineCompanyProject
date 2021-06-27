package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftPayloadCapacityTest {

    @Test
    fun `create payload capacity - success`() {
        val payloadCapacity = 100500

        val result = AircraftPayloadCapacity.from(payloadCapacity)

        result shouldBeRight {
            it.payloadCapacity shouldBe payloadCapacity
        }
    }

    @Test
    fun `create payload capacity - negative capacity`() {
        val payloadCapacity = -100500

        val result = AircraftPayloadCapacity.from(payloadCapacity)

        result shouldBeLeft NegativePayloadCapacity
    }
}