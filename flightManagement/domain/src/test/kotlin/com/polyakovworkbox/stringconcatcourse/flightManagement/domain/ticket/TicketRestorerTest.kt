package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TicketRestorerTest {

    @Test
    fun `restore ticket - success`() {
        val ticketId = ticketId()
        val flightId = flightId()
        val price = price()
        val version = version()

        val result = TicketRestorer.restoreTicket(
            ticketId,
            flightId,
            price,
            version
        )

        result.let {
            it.id shouldBe ticketId
            it.flightId shouldBe flightId
            it.price shouldBe price
        }
    }
}