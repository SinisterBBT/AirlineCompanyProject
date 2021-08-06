package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftRegistrationNumber
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AircraftRegistrationNumberTest {

    @Test
    fun `AircraftRegistrationNumber is equal to other AircraftRegistrationNumber with the same value`() {
        val registrationNumber = "123-456-abc-DEF"
        val firstValue = aircraftRegistrationNumber(registrationNumber)
        val secondValue = aircraftRegistrationNumber(registrationNumber)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create registration number - success`() {
        val registrationNumber = "123-456-abc-DEF"

        val result = AircraftRegistrationNumber.from(registrationNumber)

        result shouldBeRight {
            it.registrationNumber shouldBe registrationNumber
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create registration number - empty number`(input: String) {
        val result = AircraftRegistrationNumber.from(input)

        result shouldBeLeft EmptyRegistrationNumberError
    }
}