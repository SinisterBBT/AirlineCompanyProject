package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsNotAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsNotToSoonForPublishing
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsToSoonForPublishing
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class TicketTest {

    val ticketId = ticketId()

    private val idGenerator = object : TicketIdGenerator {
        override fun generate() = ticketId
    }

    @Test
    fun `publish new ticket - success`() {
        val flightId = flightId()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsAnnounced,
                FlightIsNotToSoonForPublishing,
                flightId,
                price)

        result shouldBeRight {
            it.flightId shouldBe flightId
            it.price shouldBe price
        }
    }

    @Test
    fun `publish new ticket - flight is not announced`() {
        val flightId = flightId()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsNotAnnounced,
                FlightIsNotToSoonForPublishing,
                flightId,
                price)

        result shouldBeLeft WrongTicketError.FlightIsNotAnnouncedError
    }

    @Test
    fun `publish new ticket - flight is too soon`() {
        val flightId = flightId()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsAnnounced,
                FlightIsToSoonForPublishing,
                flightId,
                price)

        result shouldBeLeft WrongTicketError.FlightIsToSoonForPublishingError
    }
}