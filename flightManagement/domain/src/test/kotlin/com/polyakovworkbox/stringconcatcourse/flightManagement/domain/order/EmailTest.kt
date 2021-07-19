package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class EmailTest {

    @Test
    fun `create email - success`() {
        val email = "someEmail@gmail.com"

        val result = Email.from(email)

        result shouldBeRight {
            it.email shouldBe email
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "someEmail", "1@", "@1", "1 @1"])
    fun `create email - wrong format`(email: String) {
        val result = Email.from(email)

        result shouldBeLeft EmailIsNotValidError
    }
}