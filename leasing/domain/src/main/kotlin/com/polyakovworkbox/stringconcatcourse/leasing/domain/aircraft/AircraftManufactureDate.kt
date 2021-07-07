package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.LocalDate

class AircraftManufactureDate internal constructor(val manufactureDate: LocalDate) : ValueObject {

    companion object {
        fun from(manufactureDate: LocalDate): Either<ManufactureDateInFutureError, AircraftManufactureDate> =
            if (manufactureDate.isBefore(LocalDate.now().plusDays(1))) {
                AircraftManufactureDate(manufactureDate).right()
            } else {
                ManufactureDateInFutureError.left()
            }
    }
}

object ManufactureDateInFutureError : BusinessError