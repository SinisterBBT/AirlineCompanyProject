package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftSeatCountTest {

    @Test
    fun `AircraftSeatMap is equal to other AircraftSeatMap with the same value`() {
        val seatCount = 50
        val firstValue = AircraftSeatCount.from(seatCount)
        val secondValue = AircraftSeatCount.from(seatCount)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create seat map - success`() {
        val seatCount = 50

        val result = AircraftSeatCount.from(seatCount)

        result shouldBeRight {
            it.seatCount shouldBe seatCount
        }
    }

    @Test
    fun `create seat map - empty seat map`() {
        val result = AircraftSeatCount.from(0)

        result shouldBeLeft WrongSeatMapCount
    }
}