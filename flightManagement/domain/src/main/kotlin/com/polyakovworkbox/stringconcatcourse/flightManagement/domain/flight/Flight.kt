package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft

class Flight internal constructor(
    id: FlightId,
    val departureAirport: DepartureAirport,
    val arrivalAirport: ArrivalAirport,
    val departureDate: DepartureDate,
    val arrivalDate: ArrivalDate,
    val aircraft: Aircraft,
    version: Version
) : AggregateRoot<FlightId>(id, version) {

    companion object {
        fun announceNewFlight(
            idGenerator: FlightIdGenerator,
            aircraftIsNotInOperationChecker: AircraftIsNotInOperationChecker,
            aircraftIsAlreadyInFlightChecker: AircraftIsAlreadyInFlightChecker,
            airportAllowsFlightChecker: AirportAllowsFlightChecker,
            departureAirport: DepartureAirport,
            arrivalAirport: ArrivalAirport,
            departureDate: DepartureDate,
            arrivalDate: ArrivalDate,
            aircraft: Aircraft
        ): Either<CannotAnnounceFlightError, Flight> {
            return if (!aircraftIsNotInOperationChecker.check(aircraft.registrationNumber)) {
                AircraftIsNotInOperationError.left()
            } else if (aircraftIsAlreadyInFlightChecker.check(aircraft.registrationNumber)) {
                AircraftIsAlreadyInFlightError.left()
            } else if (!airportAllowsFlightChecker.check(departureDate)) {
                AirportDoesNotAllowFlightError.left()
            } else {
                Flight(
                        idGenerator.generate(),
                        departureAirport,
                        arrivalAirport,
                        departureDate,
                        arrivalDate,
                        aircraft,
                        Version.new()
                ).apply {
                    addEvent(FlightAnnouncedDomainEvent(this.id))
                }.right()
            }
        }
    }
}

open class CannotAnnounceFlightError : BusinessError
object AircraftIsNotInOperationError : CannotAnnounceFlightError()
object AircraftIsAlreadyInFlightError : CannotAnnounceFlightError()
object AirportDoesNotAllowFlightError : CannotAnnounceFlightError()