package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PassportDataTest {

    @Test
    fun `create passport data - success`() {
        val passportData = "1111 1111111"

        val result = PassportData.from(passportData)

        result shouldBeRight {
            it.passportData shouldBe passportData
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create passport data - empty`(passportData: String) {
        val result = PassportData.from(passportData)

        result shouldBeLeft EmptyPassportDataError
    }
}