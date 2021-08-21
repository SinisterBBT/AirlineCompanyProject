package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId

interface AircraftInfoExtractor {
    fun getById(aircraftId: AircraftId): Aircraft?
}
