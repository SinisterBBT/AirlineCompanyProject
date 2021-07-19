package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FioTest {

    @Test
    fun `create fio - success`() {
        val fio = "Ivanov Ivan Ivanovich"

        val result = Fio.from(fio)

        result shouldBeRight {
            it.fio shouldBe fio
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create fio - empty`(fio: String) {
        val result = Fio.from(fio)

        result shouldBeLeft EmptyFioError
    }
}