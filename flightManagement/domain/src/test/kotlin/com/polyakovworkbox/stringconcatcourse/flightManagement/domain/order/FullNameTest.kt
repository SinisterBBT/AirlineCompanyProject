package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.fullName
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FullNameTest {

    @Test
    fun `is equal to other FullName with the same value`() {
        val firstValue = fullName("Ivanov Ivan Ivanovich")
        val secondValue = fullName("Ivanov Ivan Ivanovich")

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create full name - success`() {
        val fio = "Ivanov Ivan Ivanovich"

        val result = FullName.from(fio)

        result shouldBeRight {
            it.fio shouldBe fio
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create full name - empty`(fio: String) {
        val result = FullName.from(fio)

        result shouldBeLeft EmptyFioError
    }
}