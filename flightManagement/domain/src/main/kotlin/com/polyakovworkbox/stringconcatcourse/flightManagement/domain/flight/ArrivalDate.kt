package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.ZonedDateTime

data class ArrivalDate internal constructor(val arrivalDate: ZonedDateTime) : ValueObject {

    companion object {
        fun from(arrivalDate: ZonedDateTime): Either<ArrivalDateIsInThePast, ArrivalDate> {
            return if (arrivalDate.isBefore(ZonedDateTime.now(arrivalDate.zone))) {
                ArrivalDateIsInThePast.left()
            } else {
                ArrivalDate(arrivalDate).right()
            }
        }
    }

    fun isAfter(departureDate: DepartureDate): Boolean = arrivalDate.isAfter(departureDate.departureDate)
}

object ArrivalDateIsInThePast : BusinessError