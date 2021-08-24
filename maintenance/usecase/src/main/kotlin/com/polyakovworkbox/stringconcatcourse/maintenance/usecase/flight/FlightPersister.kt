package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight

interface FlightPersister {
    fun save(flight: Flight)
}
