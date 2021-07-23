package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

interface AircraftIsAlreadyInFlight {
    fun check(aircraftId: AircraftId): Boolean
}