package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class PassportData internal constructor(val passportData: String) : ValueObject {
    companion object {
        fun from(passportData: String): Either<EmptyPassportDataError, PassportData> {
            return if (passportData.isNotBlank()) {
                PassportData(passportData).right()
            } else {
                EmptyPassportDataError.left()
            }
        }
    }
}

object EmptyPassportDataError : BusinessError