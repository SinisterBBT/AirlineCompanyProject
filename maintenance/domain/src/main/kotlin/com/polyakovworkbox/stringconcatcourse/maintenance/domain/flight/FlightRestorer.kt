package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

object FlightRestorer {

    fun restoreFlight(
        id: FlightId,
        departureAirport: DepartureAirport,
        actualArrivalAirport: ActualArrivalAirport,
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