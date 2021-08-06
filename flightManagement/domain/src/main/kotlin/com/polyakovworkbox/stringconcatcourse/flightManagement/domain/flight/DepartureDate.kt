package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.ZonedDateTime

data class DepartureDate internal constructor(val departureDate: ZonedDateTime) : ValueObject {

    companion object {

        fun from(departureDate: ZonedDateTime): Either<DepartureDateIsInThePast, DepartureDate> {
            return if (departureDate.isBefore(ZonedDateTime.now(departureDate.zone))) {
                DepartureDateIsInThePast.left()
            } else {
                DepartureDate(departureDate).right()
            }
        }
    }

    fun isWithinTheTimeFromNowPlusMinutes(minutes: Long) =
            departureDate.isBefore(ZonedDateTime.now(departureDate.zone).plusMinutes(minutes))
}

object DepartureDateIsInThePast : BusinessError