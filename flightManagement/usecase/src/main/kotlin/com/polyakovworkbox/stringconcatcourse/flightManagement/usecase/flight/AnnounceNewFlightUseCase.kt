package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.CannotAnnounceFlightError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightIdGenerator

class AnnounceNewFlightUseCase(
    private val flightPersister: FlightPersister,
    private val idGenerator: FlightIdGenerator,
    private val aicraftIsAlreadyInFlight: AircraftIsAlreadyInFlight,
    private val aircraftIsNotInOperation: AircraftIsNotInOperation,
    private val airportAllowsFlight: AirportAllowsFlight
) : AnnounceNewFlight {

    override fun execute(request: AnnounceNewFlightRequest): Either<AnnounceNewFlightCaseError, Flight> =
        Flight.announceNewFlight(
            idGenerator = idGenerator,
            aircraftIsAlreadyInFlight = aicraftIsAlreadyInFlight,
            airportAllowsFlight = airportAllowsFlight,
            aircraftIsNotInOperation = aircraftIsNotInOperation,
            departureAirport = request.departureAirport,
            arrivalAirport = request.arrivalAirport,
            departureDate = request.departureDate,
            arrivalDate = request.arrivalDate,
            aircraftId = request.aircraftId
        ).mapLeft { e -> e.toError() }.map {
            flight -> flightPersister.save(flight)
            flight
        }
}

fun CannotAnnounceFlightError.toError(): AnnounceNewFlightCaseError {
    return when (this) {
        CannotAnnounceFlightError.ArrivalDateCannotBeBeforeDepartureDate ->
            AnnounceNewFlightCaseError.ArrivalDateCannotBeBeforeDepartureDate
        CannotAnnounceFlightError.AircraftIsAlreadyInFlightError ->
            AnnounceNewFlightCaseError.AircraftIsAlreadyInFlightError
        CannotAnnounceFlightError.AircraftIsNotInOperationError ->
            AnnounceNewFlightCaseError.AircraftIsNotInOperationError
        CannotAnnounceFlightError.AirportDoesNotAllowFlightError ->
            AnnounceNewFlightCaseError.AirportDoesNotAllowFlightError
    }
}