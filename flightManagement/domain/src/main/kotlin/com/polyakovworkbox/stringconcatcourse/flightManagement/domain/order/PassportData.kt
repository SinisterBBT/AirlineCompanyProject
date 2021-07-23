package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

data class PassportData internal constructor(val passportData: String) : ValueObject {
    companion object {
        private const val PASSPORT_SERIAL_LENGTH = 4
        private const val PASSPORT_NUMBER_LENGTH = 6

        fun from(passportData: String): Either<EmptyPassportDataError, PassportData> {
            if (passportData.isBlank()) {
                return EmptyPassportDataError.left()
            }
            val passportNoParts = passportData.split(" ")
            return if (passportNoParts.size == 2 &&
                    passportNoParts[0].length == PASSPORT_SERIAL_LENGTH &&
                    passportNoParts[1].length == PASSPORT_NUMBER_LENGTH) {
                        PassportData(passportData).right()
            } else {
                EmptyPassportDataError.left()
            }
        }
    }
}

object EmptyPassportDataError : BusinessError