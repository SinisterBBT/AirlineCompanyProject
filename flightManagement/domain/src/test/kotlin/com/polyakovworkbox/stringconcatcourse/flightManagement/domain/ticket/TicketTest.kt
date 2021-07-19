package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsNotAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsNotToSoonForPublishing
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.FlightIsToSoonForPublishing
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight
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
        val flight = flight()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsAnnounced,
                FlightIsNotToSoonForPublishing,
                flight,
                price)

        result shouldBeRight {
            it.flight shouldBe flight
            it.price shouldBe price
        }
    }

    @Test
    fun `publish new ticket - flight is not announced`() {
        val flight = flight()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsNotAnnounced,
                FlightIsNotToSoonForPublishing,
                flight,
                price)

        result shouldBeLeft WrongTicketError.FlightIsNotAnnouncedError
    }

    @Test
    fun `publish new ticket - flight is too soon`() {
        val flight = flight()
        val price = price()

        val result = Ticket.publishTicket(
                idGenerator,
                FlightIsAnnounced,
                FlightIsToSoonForPublishing,
                flight,
                price)

        result shouldBeLeft WrongTicketError.FlightIsToSoonForPublishingError
    }
}