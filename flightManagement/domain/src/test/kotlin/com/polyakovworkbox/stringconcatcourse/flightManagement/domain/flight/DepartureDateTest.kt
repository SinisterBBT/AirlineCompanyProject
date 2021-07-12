package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class DepartureDateTest {

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