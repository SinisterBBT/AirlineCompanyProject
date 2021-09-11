package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId

interface FlightExtractor {
    fun getByFlightId(flightId: FlightId): Flight?
}