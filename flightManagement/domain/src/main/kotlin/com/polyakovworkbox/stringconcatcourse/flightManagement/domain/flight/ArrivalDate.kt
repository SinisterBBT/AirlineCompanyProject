package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.ZonedDateTime

class ArrivalDate(val arrivalDate: ZonedDateTime) : ValueObject {

    companion object {
        fun from(arrivalDate: ZonedDateTime): Either<ArrivalDateToSoonError, ArrivalDate> {
            return if (arrivalDate.isBefore(ZonedDateTime.now(arrivalDate.zone).plusHours(1))) {
                ArrivalDateToSoonError.left()
            } else {
                ArrivalDate(arrivalDate).right()
            }
        }
    }
}

object ArrivalDateToSoonError : BusinessError