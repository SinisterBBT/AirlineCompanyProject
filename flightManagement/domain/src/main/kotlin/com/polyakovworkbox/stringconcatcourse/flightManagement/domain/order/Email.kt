package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class Email internal constructor(val email: String) : ValueObject {

    companion object {
        fun from(email: String): Either<EmailIsNotValidError, Email> {
            val regex = """[^\s]+@[^\s]+""".toRegex()
            return if (regex.matches(email)) {
                Email(email).right()
            } else {
                EmailIsNotValidError.left()
            }
        }
    }
}

object EmailIsNotValidError : BusinessError