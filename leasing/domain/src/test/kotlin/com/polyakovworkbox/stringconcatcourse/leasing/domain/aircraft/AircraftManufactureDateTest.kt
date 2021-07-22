package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftManufactureDate
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class AircraftManufactureDateTest {

    @Test
    fun `AircraftManufactureDate is equal to other AircraftManufactureDate with the same value`() {
        val manufactureDate = LocalDate.of(2010, Month.APRIL, 1)
        val firstValue = aircraftManufactureDate(manufactureDate)
        val secondValue = aircraftManufactureDate(manufactureDate)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create manufacture date - success`() {
        val manufactureDate = LocalDate.of(2010, Month.APRIL, 1)

        val result = AircraftManufactureDate.from(manufactureDate)

        result shouldBeRight {
            it.manufactureDate shouldBe manufactureDate
        }
    }

    @Test
    fun `create manufacture date - date in the future`() {
        val manufactureDate = LocalDate.now().plusDays(1)

        val result = AircraftManufactureDate.from(manufactureDate)

        result shouldBeLeft ManufactureDateInFutureError
    }
}