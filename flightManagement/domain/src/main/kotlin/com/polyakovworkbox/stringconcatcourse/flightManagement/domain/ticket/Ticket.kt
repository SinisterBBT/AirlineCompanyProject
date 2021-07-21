package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight

class Ticket internal constructor(
    id: TicketId,
    val flight: Flight,
    val price: Price,
    version: Version
) : AggregateRoot<TicketId>(id, version) {

    companion object {
        fun publishTicket(
            idGenerator: TicketIdGenerator,
            flightIsAnnounced: FlightIsAnnounced,
            flightIsToSoonForPublishing: FlightIsToSoonForPublishing,
            flight: Flight,
            price: Price
        ): Either<WrongTicketError, Ticket> {
            return when {
                !flightIsAnnounced.check(flight.id) ->
                    WrongTicketError.FlightIsNotAnnouncedError.left()
                flightIsToSoonForPublishing.check(flight.id) ->
                    WrongTicketError.FlightIsToSoonForPublishingError.left()
                else ->
                    Ticket(idGenerator.generate(), flight, price, Version.new()).apply {
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