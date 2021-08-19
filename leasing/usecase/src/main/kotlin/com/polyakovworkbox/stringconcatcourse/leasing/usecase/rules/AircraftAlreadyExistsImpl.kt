package com.polyakovworkbox.stringconcatcourse.leasing.usecase.rules

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftAlreadyExists
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft.AircraftExtractor

class AircraftAlreadyExistsImpl(
    val extractor: AircraftExtractor
) : AircraftAlreadyExists {
    override fun check(registrationNumber: AircraftRegistrationNumber): Boolean {
        val aircraft = extractor.getByRegistrationNumber(registrationNumber)
        return aircraft != null
    }
}