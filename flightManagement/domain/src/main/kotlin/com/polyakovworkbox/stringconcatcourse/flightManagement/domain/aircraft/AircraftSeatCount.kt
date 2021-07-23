package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

data class AircraftSeatCount internal constructor(val seatCount: Int) : ValueObject {

    companion object {
        fun from(seatCount: Int): Either<WrongSeatMapCount, AircraftSeatCount> =
            if (seatCount > 0) {
                AircraftSeatCount(seatCount).right()
            } else {
                WrongSeatMapCount.left()
            }
    }
}

object WrongSeatMapCount : BusinessError