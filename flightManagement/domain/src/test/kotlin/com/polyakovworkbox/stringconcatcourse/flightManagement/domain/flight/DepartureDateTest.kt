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
    fun `is equal to other DepartureDate with the same value`() {
        val departureDate = defaultDepartureDate()
        val firstValue = departureDate(departureDate)
        val secondValue = departureDate(departureDate)

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
    fun `departure date create - in the past`() {
        val timeNow = ZonedDateTime.now().minusDays(1)
        val departureDate = DepartureDate.from(timeNow)

        departureDate shouldBeLeft DepartureDateIsInThePast
    }

    @Test
    fun `departure is within given date`() {
        val timeTillDeparture = ZonedDateTime.now().plusMinutes(90)
        val departureDate = departureDate(timeTillDeparture)

        val result = departureDate.isWithinTheTimeFromNowPlusMinutes(100)

        result shouldBe true
    }

    @Test
    fun `departure is not within given date`() {
        val timeTillDeparture = ZonedDateTime.now().plusMinutes(100)
        val departureDate = departureDate(timeTillDeparture)

        val result = departureDate.isWithinTheTimeFromNowPlusMinutes(90)

        result shouldBe false
    }
}