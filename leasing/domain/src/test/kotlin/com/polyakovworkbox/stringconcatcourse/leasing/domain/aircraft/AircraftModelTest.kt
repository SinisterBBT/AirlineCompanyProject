package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftModel
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AircraftModelTest {

    @Test
    fun `AircraftModel is equal to other AircraftModel with the same value`() {
        val name = "Airbus0000"
        val firstValue = aircraftModel(name)
        val secondValue = aircraftModel(name)

        (firstValue == secondValue) shouldBe true
    }

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