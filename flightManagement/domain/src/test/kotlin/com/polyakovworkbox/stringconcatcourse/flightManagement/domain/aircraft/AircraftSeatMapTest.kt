package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraftSeatMap
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftSeatMapTest {

    @Test
    fun `AircraftSeatMap is equal to other AircraftSeatMap with the same value`() {
        val firstValue = AircraftSeatMap.from(aircraftSeatMap())
        val secondValue = AircraftSeatMap.from(aircraftSeatMap())

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create seat map - success`() {
        val seatMap = aircraftSeatMap()

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