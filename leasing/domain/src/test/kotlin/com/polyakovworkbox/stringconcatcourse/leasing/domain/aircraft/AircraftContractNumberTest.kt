package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftContractNumber
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AircraftContractNumberTest {

    @Test
    fun `is equal to other AircraftContractNumber with the same value`() {
        val contractNumber = "123-45"
        val firstValue = aircraftContractNumber(contractNumber)
        val secondValue = aircraftContractNumber(contractNumber)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create contract number - success`() {
        val contractNumber = "123-45"

        val result = AircraftContractNumber.from(contractNumber)

        result shouldBeRight {
            it.contractNumber shouldBe contractNumber
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create contract number - empty contract`(input: String) {
        val result = AircraftContractNumber.from(input)

        result shouldBeLeft EmptyContractNumberError
    }
}