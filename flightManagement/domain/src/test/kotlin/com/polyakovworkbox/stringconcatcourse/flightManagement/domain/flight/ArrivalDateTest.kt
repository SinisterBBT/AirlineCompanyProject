package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.arrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.defaultArrivalDate
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class ArrivalDateTest {

    @Test
    fun `ArrivalDate is equal to other ArrivalDate with the same value`() {
        val arrivalDate = defaultArrivalDate()
        val firstValue = arrivalDate(arrivalDate)
        val secondValue = arrivalDate(arrivalDate)

        (firstValue == secondValue) shouldBe true
    }

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