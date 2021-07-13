package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject

class ArrivalAirport internal constructor(val iataCode: String) : ValueObject {

    companion object {
        private const val IATA_NUMBER_LENGTH = 3

        fun from(iataCode: String): Either<WrongIataCode, ArrivalAirport> =
                if (iataCode.length == IATA_NUMBER_LENGTH && iataCode.all { it in 'A'..'Z' }) {
                    ArrivalAirport(iataCode).right()
                } else {
                    WrongIataCode.left()
                }
    }
}