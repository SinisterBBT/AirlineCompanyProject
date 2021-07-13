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
    val price: TicketPrice,
    version: Version
) : AggregateRoot<TicketId>(id, version) {

    companion object {
        fun publishTicket(
            idGenerator: TicketIdGenerator,
            flightIsAnnouncedChecker: FlightIsAnnouncedChecker,
            flightIsToSoonForPublishingChecker: FlightIsToSoonForPublishingChecker,
            flight: Flight,
            price: TicketPrice
        ): Either<WrongTicketError, Ticket> {
            val aircraftRegistrationNumber = flight.aircraft.registrationNumber
            return if (!flightIsAnnouncedChecker.check(aircraftRegistrationNumber, flight.departureDate)) {
                FlightIsNotAnnouncedError.left()
            } else if (flightIsToSoonForPublishingChecker.check(aircraftRegistrationNumber, flight.departureDate)) {
                FlightIsToSoonForPublishingError.left()
            } else {
                Ticket(idGenerator.generate(), flight, price, Version.new()).apply {
                    addEvent(TicketPublishedDomainEvent(this.id))
                }.right()
            }
        }
    }
}

open class WrongTicketError : BusinessError
object FlightIsNotAnnouncedError : WrongTicketError()
object FlightIsToSoonForPublishingError : WrongTicketError()