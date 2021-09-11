package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.rules

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.providers.AirportPermissionProvider

class AirportAllowsFlightImpl(
    private val airportPermissionProvider: AirportPermissionProvider
) : AirportAllowsFlight {

    override fun check(departureDate: DepartureDate): Boolean =
        airportPermissionProvider.isDepartureAllowed(departureDate)
}