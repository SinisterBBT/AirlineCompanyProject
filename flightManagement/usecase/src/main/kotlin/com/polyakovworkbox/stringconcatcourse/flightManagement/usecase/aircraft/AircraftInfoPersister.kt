package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft

interface AircraftInfoPersister {
    fun save(aircraft: Aircraft)
}
