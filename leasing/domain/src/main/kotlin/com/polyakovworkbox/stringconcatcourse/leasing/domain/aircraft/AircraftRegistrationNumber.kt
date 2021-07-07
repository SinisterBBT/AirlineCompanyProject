package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftRegistrationNumber internal constructor(val registrationNumber: String) : ValueObject {

    companion object {
        fun from(registrationNumber: String): Either<EmptyRegistrationNumberError, AircraftRegistrationNumber> =
            if (registrationNumber.isNotBlank()) {
                AircraftRegistrationNumber(registrationNumber).right()
            } else {
                EmptyRegistrationNumberError.left()
            }
    }
}

object EmptyRegistrationNumberError : BusinessError
