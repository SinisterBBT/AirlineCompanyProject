package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightIdGenerator

class RegisterFlightUseCase(
    private val flightPersister: FlightPersister,
    private val idGenerator: FlightIdGenerator,
) : RegisterFlight {

    override fun execute(request: RegisterFlightRequest): Flight =
        Flight.registerFlight(
            idGenerator,
            request.departureAirport,
            request.actualArrivalAirport,
            request.flightTime
        ).let {
            flightPersister.save(it)
            it
        }
}