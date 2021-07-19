package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.Duration

class FlightTime internal constructor(val flightTime: Duration) : ValueObject {

    companion object {
        fun from(flightTime: Duration): Either<FlightTimeNegativeError, FlightTime> {
            return if (flightTime.isNegative) {
                FlightTimeNegativeError.left()
            } else {
                FlightTime(flightTime).right()
            }
        }
    }
}

object FlightTimeNegativeError : BusinessError