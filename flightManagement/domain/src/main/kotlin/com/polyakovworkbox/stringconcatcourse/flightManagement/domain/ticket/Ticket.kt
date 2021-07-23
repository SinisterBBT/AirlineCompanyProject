package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId

class Ticket internal constructor(
    id: TicketId,
    val flightId: FlightId,
    val price: Price,
    version: Version
) : AggregateRoot<TicketId>(id, version) {

    companion object {
        fun publishTicket(
            idGenerator: TicketIdGenerator,
            flightIsAnnounced: FlightIsAnnounced,
            flightIsToSoonForPublishing: FlightIsToSoonForPublishing,
            flightId: FlightId,
            price: Price
        ): Either<WrongTicketError, Ticket> {
            return when {
                !flightIsAnnounced.check(flightId) ->
                    WrongTicketError.FlightIsNotAnnouncedError.left()
                flightIsToSoonForPublishing.check(flightId) ->
                    WrongTicketError.FlightIsToSoonForPublishingError.left()
                else ->
                    Ticket(idGenerator.generate(), flightId, price, Version.new()).apply {
                        addEvent(TicketPublishedDomainEvent(this.id))
                    }.right()
            }
        }
    }
}

sealed class WrongTicketError : BusinessError {
    object FlightIsNotAnnouncedError : WrongTicketError()
    object FlightIsToSoonForPublishingError : WrongTicketError()
}