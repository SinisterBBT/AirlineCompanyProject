package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

class Flight internal constructor(
    id: FlightId,
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    val departureDate: DepartureDate,
    val arrivalDate: ArrivalDate,
    val aircraftId: AircraftId,
    version: Version
) : AggregateRoot<FlightId>(id, version) {

    companion object {
        fun announceNewFlight(
            idGenerator: FlightIdGenerator,
            aircraftIsNotInOperation: AircraftIsNotInOperation,
            aircraftIsAlreadyInFlight: AircraftIsAlreadyInFlight,
            airportAllowsFlight: AirportAllowsFlight,
            departureAirport: Airport,
            arrivalAirport: Airport,
            departureDate: DepartureDate,
            arrivalDate: ArrivalDate,
            aircraftId: AircraftId
        ): Either<CannotAnnounceFlightError, Flight> {
            return when {
                !arrivalDate.isAfter(departureDate) ->
                    CannotAnnounceFlightError.ArrivalDateCannotBeBeforeDepartureDate.left()
                !aircraftIsNotInOperation.check(aircraftId) ->
                    CannotAnnounceFlightError.AircraftIsNotInOperationError.left()
                aircraftIsAlreadyInFlight.check(aircraftId) ->
                    CannotAnnounceFlightError.AircraftIsAlreadyInFlightError.left()
                !airportAllowsFlight.check(departureDate) ->
                    CannotAnnounceFlightError.AirportDoesNotAllowFlightError.left()
                else -> Flight(
                    idGenerator.generate(),
                    departureAirport,
                    arrivalAirport,
                    departureDate,
                    arrivalDate,
                    aircraftId,
                    Version.new()
                ).apply {
                    addEvent(FlightAnnouncedDomainEvent(this.id))
                }.right()
            }
        }
    }
}

sealed class CannotAnnounceFlightError : BusinessError {
    object ArrivalDateCannotBeBeforeDepartureDate : CannotAnnounceFlightError()
    object AircraftIsNotInOperationError : CannotAnnounceFlightError()
    object AircraftIsAlreadyInFlightError : CannotAnnounceFlightError()
    object AirportDoesNotAllowFlightError : CannotAnnounceFlightError()
}
