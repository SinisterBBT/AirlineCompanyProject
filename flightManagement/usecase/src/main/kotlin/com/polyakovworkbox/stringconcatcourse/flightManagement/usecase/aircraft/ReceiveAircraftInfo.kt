package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

interface ReceiveAircraftInfo {
    fun execute(request: ReceiveAircraftInfoRequest): AircraftId
}
