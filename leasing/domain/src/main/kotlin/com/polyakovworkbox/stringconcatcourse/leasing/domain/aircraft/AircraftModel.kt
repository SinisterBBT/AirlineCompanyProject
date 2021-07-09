package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftModel internal constructor(val value: String) : ValueObject {

    companion object {
        fun from(name: String): Either<EmptyAircraftModelError, AircraftModel> =
            if (name.isNotBlank()) {
                AircraftModel(name).right()
            } else {
                EmptyAircraftModelError.left()
            }
    }
}

object EmptyAircraftModelError : BusinessError