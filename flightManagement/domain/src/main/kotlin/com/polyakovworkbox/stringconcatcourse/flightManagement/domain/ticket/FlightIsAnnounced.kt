package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId

interface FlightIsAnnounced {
    fun check(flightId: FlightId): Boolean
}