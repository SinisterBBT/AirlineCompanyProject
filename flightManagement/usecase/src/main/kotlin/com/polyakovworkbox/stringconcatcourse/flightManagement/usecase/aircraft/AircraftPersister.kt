package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft

interface AircraftPersister {
    fun save(aircraft: Aircraft)
}
