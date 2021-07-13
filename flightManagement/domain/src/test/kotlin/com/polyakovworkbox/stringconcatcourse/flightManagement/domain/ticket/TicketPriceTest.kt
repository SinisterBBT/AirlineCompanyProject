package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class TicketPriceTest {

    @Test
    fun `ticket published - success`() {
        val price = BigDecimal.ONE

        val result = TicketPrice.from(price)

        result shouldBeRight {
            it.price shouldBe price
        }
    }

    @Test
    fun `ticket published - with negative price`() {
        val price = BigDecimal(-1)

        val result = TicketPrice.from(price)

        result shouldBeLeft NegativeTicketPriceError
    }
}