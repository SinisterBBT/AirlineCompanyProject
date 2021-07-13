package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import flight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import ticketId
import ticketPrice
import version

class TicketRestorerTest {

    @Test
    fun `restore ticket - success`() {
        val ticketId = ticketId()
        val flight = flight()
        val price = ticketPrice()
        val version = version()

        val result = TicketRestorer.restoreTicket(
            ticketId,
            flight,
            price,
            version
        )

        result.id shouldBe ticketId
        result.flight shouldBe flight
        result.price shouldBe price
    }
}