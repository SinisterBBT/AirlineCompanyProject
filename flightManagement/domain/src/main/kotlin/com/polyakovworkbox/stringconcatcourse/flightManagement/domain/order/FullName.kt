package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class FullName internal constructor(val fio: String) : ValueObject {
    companion object {
        fun from(fio: String): Either<EmptyFioError, FullName> {
            return if (fio.isNotBlank()) {
                FullName(fio).right()
            } else {
                EmptyFioError.left()
            }
        }
    }
}

object EmptyFioError : BusinessError