package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import arrow.core.Either
import arrow.core.left
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.WrongTicketError
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.FlightExtractor

class PublishNewTicketUseCase(
    private val ticketPersister: TicketPersister,
    private val idGenerator: TicketIdGenerator,
    private val flightIsAnnounced: FlightIsAnnounced,
    private val flightExtractor: FlightExtractor
) : PublishNewTicket {

    override fun execute(request: PublishNewTicketRequest): Either<PublishNewTicketCaseError, Ticket> {
        return Ticket.publishTicket(
            idGenerator,
            flightIsAnnounced,
            flightExtractor.getByFlightId(request.flightId)
                ?: return PublishNewTicketCaseError.FlightIsNotAnnouncedError.left(),
            request.price
        ).mapLeft { e -> e.toError() }.map {
            ticket -> ticketPersister.save(ticket)
            ticket
        }
    }
}

fun WrongTicketError.toError(): PublishNewTicketCaseError {
    return when (this) {
        WrongTicketError.FlightIsNotAnnouncedError ->
            PublishNewTicketCaseError.FlightIsNotAnnouncedError
        WrongTicketError.FlightIsToSoonForPublishingError ->
            PublishNewTicketCaseError.FlightIsToSoonForPublishingError
    }
}