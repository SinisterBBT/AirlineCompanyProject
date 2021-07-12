package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import correctSeatMapLayout
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftSeatMapTest {

    @Test
    fun `create seat map - success`() {
        val seatMap = correctSeatMapLayout()

        val result = AircraftSeatMap.from(seatMap)

        result shouldBeRight {
            it.seatMap shouldBe seatMap
        }
    }

    @Test
    fun `create seat map - empty seat map`() {
        val result = AircraftSeatMap.from(arrayListOf())

        result shouldBeLeft WrongSeatMapLayout
    }
}