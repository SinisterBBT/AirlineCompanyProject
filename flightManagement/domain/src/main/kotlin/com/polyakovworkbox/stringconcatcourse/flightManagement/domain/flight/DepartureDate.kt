package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.time.ZonedDateTime

data class DepartureDate internal constructor(val departureDate: ZonedDateTime) : ValueObject {

    companion object {
        private const val MINUTES_FROM_NOW_WHEN_NEW_DEPARTURE_ANNOUNCEMENT_IS_FORBIDDEN: Long = 60

        fun from(departureDate: ZonedDateTime): Either<DepartureDateToSoonError, DepartureDate> {
            return if (departureDate.isBefore(ZonedDateTime.now(departureDate.zone)
                            .plusMinutes(MINUTES_FROM_NOW_WHEN_NEW_DEPARTURE_ANNOUNCEMENT_IS_FORBIDDEN))) {
                DepartureDateToSoonError.left()
            } else {
                DepartureDate(departureDate).right()
            }
        }
    }

    fun isWithinTheTimeFromNowPlusMinutes(minutes: Long) =
            departureDate.isBefore(ZonedDateTime.now(departureDate.zone).plusMinutes(minutes))
}

object DepartureDateToSoonError : BusinessError