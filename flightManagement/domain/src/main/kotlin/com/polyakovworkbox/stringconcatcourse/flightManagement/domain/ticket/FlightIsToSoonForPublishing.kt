package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId

interface FlightIsToSoonForPublishing {
    fun check(flightId: FlightId): Boolean
}