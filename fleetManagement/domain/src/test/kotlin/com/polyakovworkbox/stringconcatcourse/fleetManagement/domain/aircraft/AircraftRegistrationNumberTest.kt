package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AircraftRegistrationNumberTest {

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