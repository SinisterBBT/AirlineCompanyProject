package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.rules

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.FlightExtractor

class AircraftIsAlreadyInFlightImpl(
    private val extractor: FlightExtractor
) : AircraftIsAlreadyInFlight {

    override fun check(aircraftId: AircraftId): Boolean =
        extractor.getByAircraftId(aircraftId) != null
}