package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket

interface PublishNewTicket {
    fun execute(request: PublishNewTicketRequest): Either<PublishNewTicketCaseError, Ticket>
}

sealed class PublishNewTicketCaseError(open val message: String) {
    object FlightIsNotAnnouncedError : PublishNewTicketCaseError(
        "It is not possible to publish ticket for flight which is not announced yet"
    )
    object FlightIsToSoonForPublishingError : PublishNewTicketCaseError(
        "It is not possible to publish flight which departure is to soon"
    )
}