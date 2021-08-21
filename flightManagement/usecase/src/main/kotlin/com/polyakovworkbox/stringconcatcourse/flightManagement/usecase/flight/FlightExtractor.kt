package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight

interface FlightExtractor {
    fun getByAircraftId(aircraftId: AircraftId): Flight?
}
