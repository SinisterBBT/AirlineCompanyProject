package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

data class Seat internal constructor(val seatNumber: String) : ValueObject {

    companion object {
        fun from(seatNumber: String): Either<WrongSeatNumberFormatError, Seat> =
                if (seatNumber.isNotBlank() && hasOnlyValidSymbols(seatNumber)) {
                    Seat(seatNumber).right()
                } else {
                    WrongSeatNumberFormatError.left()
                }

        private fun hasOnlyValidSymbols(seatNumber: String): Boolean {
            val regex = """[A-Z][0-9]{1,2}""".toRegex()
            return regex.matches(seatNumber)
        }
    }
}

object WrongSeatNumberFormatError : BusinessError