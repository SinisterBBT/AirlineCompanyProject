package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport

object FlightRestorer {

    fun restoreFlight(
        id: FlightId,
        departureAirport: Airport,
        actualArrivalAirport: Airport,
        flightTime: FlightTime,
        state: FlightState,
        version: Version
    ): Flight {
        return Flight(
            id,
            departureAirport,
            actualArrivalAirport,
            flightTime,
            version
        ).apply {
            this.state = state
        }
    }
}