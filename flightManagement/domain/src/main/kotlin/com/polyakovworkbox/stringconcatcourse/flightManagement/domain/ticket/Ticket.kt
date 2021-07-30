package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId

class Ticket internal constructor(
    id: TicketId,
    val flightId: FlightId,
    val price: Price,
    version: Version
) : AggregateRoot<TicketId>(id, version) {

    companion object {
        private const val MINUTES_BEFORE_DEPARTURE_WHEN_TICKETS_CANNOT_BE_PUBLISHED: Long = 60

        fun publishTicket(
            idGenerator: TicketIdGenerator,
            flightIsAnnounced: FlightIsAnnounced,
            flight: Flight,
            price: Price
        ): Either<WrongTicketError, Ticket> {
            return when {
                !flightIsAnnounced.check(flight.id) ->
                    WrongTicketError.FlightIsNotAnnouncedError.left()
                flight.departureDate.isWithinTheTimeFromNowPlusMinutes(
                        MINUTES_BEFORE_DEPARTURE_WHEN_TICKETS_CANNOT_BE_PUBLISHED) ->
                    WrongTicketError.FlightIsToSoonForPublishingError.left()
                else ->
                    Ticket(idGenerator.generate(), flight.id, price, Version.new()).apply {
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