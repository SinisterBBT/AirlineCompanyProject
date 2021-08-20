package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.payloadCapacity
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftPayloadCapacityTest {

    @Test
    fun `is equal to other AircraftPayloadCapacity with the same value`() {
        val payloadCapacity = 100500
        val firstValue = payloadCapacity(payloadCapacity)
        val secondValue = payloadCapacity(payloadCapacity)

        (firstValue == secondValue) shouldBe true
    }

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