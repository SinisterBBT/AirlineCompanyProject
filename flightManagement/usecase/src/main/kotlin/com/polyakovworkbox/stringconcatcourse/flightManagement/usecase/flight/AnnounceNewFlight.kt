package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight

interface AnnounceNewFlight {
    fun execute(request: AnnounceNewFlightRequest): Either<AnnounceNewFlightCaseError, Flight>
}

sealed class AnnounceNewFlightCaseError(open val message: String) {
    object ArrivalDateCannotBeBeforeDepartureDate : AnnounceNewFlightCaseError("Arrival cannot happen before departure")
    object AircraftIsNotInOperationError : AnnounceNewFlightCaseError(
        "Given aircraft is not listed among operational aircrafts"
    )
    object AircraftIsAlreadyInFlightError : AnnounceNewFlightCaseError("Given aircraft is already occupied")
    object AirportDoesNotAllowFlightError : AnnounceNewFlightCaseError("Given airport forbids the proposed flight")
}