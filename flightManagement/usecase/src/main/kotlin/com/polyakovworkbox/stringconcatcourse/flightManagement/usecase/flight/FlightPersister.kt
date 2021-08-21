package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight

interface FlightPersister {
    fun save(flight: Flight)
}
