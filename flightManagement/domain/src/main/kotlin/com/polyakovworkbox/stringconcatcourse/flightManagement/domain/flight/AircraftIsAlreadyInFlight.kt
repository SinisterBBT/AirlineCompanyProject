package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber

interface AircraftIsAlreadyInFlight {
    fun check(registrationNumber: AircraftRegistrationNumber): Boolean
}