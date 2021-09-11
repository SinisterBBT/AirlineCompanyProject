package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime

class MarkFlightArrivedUseCase(
    private val flightExtractor: FlightExtractor
) : MarkFlightArrived {

    override fun markArrived(
        flightId: FlightId,
        actualFlightTime: FlightTime
    ): Either<MarkFlightArrivedCaseError, Flight> {
        val result = flightExtractor.getByFlightId(flightId)
        return if (result != null) {
            result.arrived(actualFlightTime)
            result.right()
        } else {
            MarkFlightArrivedCaseError.FlightNotFoundError.left()
        }
    }
}