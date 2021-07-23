package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

object FlightRestorer {

    fun restoreFlight(
        id: FlightId,
        departureAirport: DepartureAirport,
        arrivalAirport: ArrivalAirport,
        departureDate: DepartureDate,
        arrivalDate: ArrivalDate,
        aircraftId: AircraftId,
        version: Version
    ): Flight {
        return Flight(
            id,
            departureAirport,
            arrivalAirport,
            departureDate,
            arrivalDate,
            aircraftId,
            version
        )
    }
}