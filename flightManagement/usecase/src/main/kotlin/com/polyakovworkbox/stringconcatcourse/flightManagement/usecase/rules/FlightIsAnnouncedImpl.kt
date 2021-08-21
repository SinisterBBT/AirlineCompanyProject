package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.rules

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.FlightExtractor

class FlightIsAnnouncedImpl(
    private val flightExtractor: FlightExtractor
) : FlightIsAnnounced {

    override fun check(flightId: FlightId): Boolean =
        flightExtractor.getByFlightId(flightId) != null
}