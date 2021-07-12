package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftSeatMap internal constructor(val seatMap: ArrayList<Seat>) : ValueObject {

    companion object {
        fun from(seatMap: ArrayList<Seat>): Either<WrongSeatMapLayout, AircraftSeatMap> =
            if (seatMap.isNotEmpty()) {
                AircraftSeatMap(seatMap).right()
            } else {
                WrongSeatMapLayout.left()
            }
    }
}

object WrongSeatMapLayout : BusinessError