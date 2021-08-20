package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.leasing.domain.seat
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SeatTest {

    @Test
    fun `is equal to other Seat with the same value`() {
        val firstValue = seat()
        val secondValue = seat()

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create aircraft model - success`() {
        val seatNumber = "A1"
        val result: Either<WrongSeatNumberFormatError, Seat> = Seat.from(seatNumber)

        result shouldBeRight {
            it.seatNumber shouldBe seatNumber
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "A", "1", "A123", "a1", "AB", "_"])
    fun `create aircraft model - empty name`(input: String) {
        val result: Either<WrongSeatNumberFormatError, Seat> = Seat.from(input)

        result shouldBeLeft WrongSeatNumberFormatError
    }
}