package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject

class DepartureAirport(val iataCode: String) : ValueObject {

    companion object {
        private const val IATA_NUMBER_LENGTH = 3

        fun from(iataCode: String): Either<WrongIataCode, DepartureAirport> =
                if (iataCode.length == IATA_NUMBER_LENGTH && iataCode.all { it in 'A'..'Z' }) {
                    DepartureAirport(iataCode).right()
                } else {
                    WrongIataCode.left()
                }
    }
}
