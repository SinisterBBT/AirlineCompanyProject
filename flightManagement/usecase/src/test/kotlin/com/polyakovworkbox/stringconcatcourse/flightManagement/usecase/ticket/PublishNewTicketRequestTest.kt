package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.price
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class PublishNewTicketRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val flightId = flightId()
        val price = price()

        val result = PublishNewTicketRequest.from(
            flightId = flightId.value,
            price = price.price
        )

        val result2 = PublishNewTicketRequest.from(
            flightId = flightId.value,
            price = price.price
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val flightId = flightId()
        val price = price()

        val result = PublishNewTicketRequest.from(
            flightId = flightId.value,
            price = price.price
        )

        val compareToInstance = PublishNewTicketRequest(
            flightId,
            price
        )

        result shouldBeRight {
            it.flightId shouldBe compareToInstance.flightId
            it.price shouldBe compareToInstance.price
        }
    }

    @Test
    fun `has wrong price format`() {
        val flightId = flightId()
        val price = BigDecimal(-5)

        val result = PublishNewTicketRequest.from(
            flightId = flightId.value,
            price = price
        )

        result shouldBeLeft InvalidTicketParameters("Ticket price value has wrong format")
    }
}