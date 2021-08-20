package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftId

interface AcquireNewAircraft {

    fun execute(request: AcquireNewAircraftRequest): Either<AcquireNewAircraftCaseError, AircraftId>
}

sealed class AcquireNewAircraftCaseError(open val message: String) {
    object AlreadyExists : AcquireNewAircraftCaseError("Aircraft with the given registration number already registered")
}