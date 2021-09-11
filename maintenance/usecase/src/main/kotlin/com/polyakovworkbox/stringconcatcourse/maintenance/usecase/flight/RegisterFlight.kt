package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight

interface RegisterFlight {
    fun execute(request: RegisterFlightRequest): Flight
}
