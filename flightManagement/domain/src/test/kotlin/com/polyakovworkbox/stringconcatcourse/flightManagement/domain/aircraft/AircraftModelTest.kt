package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import arrow.core.Either
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AircraftModelTest {

    @Test
    fun `create aircraft model - success`() {
        val name = "Airbus0000"
        val result: Either<EmptyAircraftModelError, AircraftModel> = AircraftModel.from(name)

        result shouldBeRight {
            it.value shouldBe name
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create aircraft model - empty name`(input: String) {
        val result: Either<EmptyAircraftModelError, AircraftModel> = AircraftModel.from(input)

        result shouldBeLeft EmptyAircraftModelError
    }
}