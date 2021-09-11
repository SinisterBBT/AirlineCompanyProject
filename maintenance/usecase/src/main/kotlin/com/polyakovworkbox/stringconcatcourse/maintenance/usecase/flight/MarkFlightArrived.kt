package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime

interface MarkFlightArrived {
    fun markArrived(flightId: FlightId, actualFlightTime: FlightTime): Either<MarkFlightArrivedCaseError, Flight>
}

sealed class MarkFlightArrivedCaseError(open val message: String) {
    object FlightNotFoundError : MarkFlightArrivedCaseError("Flight to mark it arrived is not found")
}