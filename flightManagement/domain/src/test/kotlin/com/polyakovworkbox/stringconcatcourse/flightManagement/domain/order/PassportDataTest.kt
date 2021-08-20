package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passportData
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PassportDataTest {

    @Test
    fun `is equal to other PassportData with the same value`() {
        val passportData = "1234 123456"
        val firstValue = passportData(passportData)
        val secondValue = passportData(passportData)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create passport data - success`() {
        val passportData = "1234 123456"

        val result = PassportData.from(passportData)

        result shouldBeRight {
            it.passportData shouldBe passportData
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "123 123456", "1234 12345", "12345 123456",
        "1234 1234567", "1234123456", "12 34 123456"])
    fun `create passport data - wrong format`(passportData: String) {
        val result = PassportData.from(passportData)

        result shouldBeLeft EmptyPassportDataError
    }
}