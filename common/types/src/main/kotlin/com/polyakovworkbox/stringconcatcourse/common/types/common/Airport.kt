package com.polyakovworkbox.stringconcatcourse.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

data class Airport internal constructor(val iataCode: String) : ValueObject {

    companion object {
        private const val IATA_NUMBER_LENGTH = 3

        fun from(iataCode: String): Either<WrongIataCode, Airport> =
                if (iataCode.length == IATA_NUMBER_LENGTH && iataCode.all { it in 'A'..'Z' }) {
                    Airport(iataCode).right()
                } else {
                    WrongIataCode.left()
                }
    }
}

object WrongIataCode : BusinessError