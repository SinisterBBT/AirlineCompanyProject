package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

interface ReceiveAircraft {
    fun execute(request: ReceiveAircraftRequest): AircraftId
}
