package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftRegistrationNumber

interface AircraftExtractor {
    fun getByRegistrationNumber(registrationNumber: AircraftRegistrationNumber): Aircraft?
}
