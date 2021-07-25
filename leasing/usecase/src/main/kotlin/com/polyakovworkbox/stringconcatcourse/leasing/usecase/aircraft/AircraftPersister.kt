package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.Aircraft

interface AircraftPersister {
    fun save(aircraft: Aircraft)
}