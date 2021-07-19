package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class ArrivalDateTest {

    @Test
    fun `arrival date create - success`() {
        val validTimeForPlannedArrival = ZonedDateTime.now().plusHours(2)
        val arrivalDate = ArrivalDate.from(validTimeForPlannedArrival)

        arrivalDate shouldBeRight {
            it.arrivalDate shouldBe validTimeForPlannedArrival
        }
    }

    @Test
    fun `arrival date create - too late`() {
        val timeNow = ZonedDateTime.now()
        val arrivalDate = ArrivalDate.from(timeNow)

        arrivalDate shouldBeLeft ArrivalDateToSoonError
    }
}