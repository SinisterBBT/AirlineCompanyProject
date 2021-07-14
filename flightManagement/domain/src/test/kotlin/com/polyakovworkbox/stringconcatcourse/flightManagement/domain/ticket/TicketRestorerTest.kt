package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketPrice
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

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

        result.let {
            it.id shouldBe ticketId
            it.flight shouldBe flight
            it.price shouldBe price
        }
    }
}