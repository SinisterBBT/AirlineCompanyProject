package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.defaultDepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.departureDate
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class DepartureDateTest {

    @Test
    fun `DepartureDate is equal to other DepartureDate with the same value`() {
        val arrivalDate = defaultDepartureDate()
        val firstValue = departureDate(arrivalDate)
        val secondValue = departureDate(arrivalDate)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `departure date create - success`() {
        val validTimeForPlannedDeparture = ZonedDateTime.now().plusHours(2)
        val departureDate = DepartureDate.from(validTimeForPlannedDeparture)

        departureDate shouldBeRight {
            it.departureDate shouldBe validTimeForPlannedDeparture
        }
    }

    @Test
    fun `departure date create - too late`() {
        val timeNow = ZonedDateTime.now()
        val departureDate = DepartureDate.from(timeNow)

        departureDate shouldBeLeft DepartureDateToSoonError
    }
}