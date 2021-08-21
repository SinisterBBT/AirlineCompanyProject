package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.rules

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft.AircraftInfoExtractor

class AircraftIsNotInOperationImpl(
    private val extractor: AircraftInfoExtractor
) : AircraftIsNotInOperation {

    override fun check(aircraftId: AircraftId): Boolean =
        extractor.getById(aircraftId) == null
}