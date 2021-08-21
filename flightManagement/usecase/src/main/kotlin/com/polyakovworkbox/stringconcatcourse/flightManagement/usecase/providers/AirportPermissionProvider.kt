package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.providers

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate

interface AirportPermissionProvider {
    fun isDepartureAllowed(departureDate: DepartureDate): Boolean
}
