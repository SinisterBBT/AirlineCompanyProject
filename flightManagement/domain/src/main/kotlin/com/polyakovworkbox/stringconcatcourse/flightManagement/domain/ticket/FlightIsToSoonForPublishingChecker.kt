package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate

interface FlightIsToSoonForPublishingChecker {
    fun check(aircraftRegistrationNumber: AircraftRegistrationNumber, departureDate: DepartureDate): Boolean
}