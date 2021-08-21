package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.TestTicketPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.departureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.FlightExtractor
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class PublishNewTicketUseCaseTest {

    @Test
    fun `successfully published`() {
        val flightId = flightId()
        val price = price()

        val ticketPersister = TestTicketPersister()
        val id = TestTicketIdGenerator.id

        val result = PublishNewTicketUseCase(
            ticketPersister,
            TestTicketIdGenerator,
            FlightIsAnnounced,
            TestFlightExtractor
        ).execute(
            PublishNewTicketRequest(
                flightId,
                price
            )
        )

        result shouldBeRight {
            it.id shouldBe id
            it.flightId shouldBe flightId
            it.price shouldBe price
        }

        val ticket = ticketPersister[id]
        ticket.shouldNotBeNull()
    }

    @Test
    fun `flight is not announced`() {
        val flightId = flightId()
        val price = price()

        val ticketPersister = TestTicketPersister()

        val result = PublishNewTicketUseCase(
            ticketPersister,
            TestTicketIdGenerator,
            FlightIsNotAnnounced,
            TestFlightExtractor
        ).execute(
            PublishNewTicketRequest(
                flightId,
                price
            )
        )

        result shouldBeLeft PublishNewTicketCaseError.FlightIsNotAnnouncedError
        ticketPersister.shouldBeEmpty()
    }
}

object TestTicketIdGenerator : TicketIdGenerator {
    val id = ticketId()
    override fun generate() = id
}

object TestFlightExtractor : FlightExtractor {
    override fun getByAircraftId(aircraftId: AircraftId): Flight = throw UnsupportedOperationException()
    override fun getByFlightId(flightId: FlightId): Flight =
        flight(departureDate(ZonedDateTime.now().plusDays(100)), flightId)
}

object FlightIsAnnounced : FlightIsAnnounced {
    override fun check(flightId: FlightId) = true
}

object FlightIsNotAnnounced : FlightIsAnnounced {
    override fun check(flightId: FlightId) = false
}