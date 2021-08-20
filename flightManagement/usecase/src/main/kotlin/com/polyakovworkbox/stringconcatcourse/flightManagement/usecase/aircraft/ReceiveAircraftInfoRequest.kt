package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count
import com.polyakovworkbox.stringconcatcourse.common.types.common.NegativeValueError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.EmptyAircraftModelError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.EmptyRegistrationNumberError

data class ReceiveAircraftInfoRequest internal constructor(
    val registrationNumber: AircraftRegistrationNumber,
    val model: AircraftModel,
    val seatCount: Count
) {
    companion object {
        fun from(
            registrationNumber: String,
            model: String,
            seatCount: Int
        ): Either<InvalidAircraftInfoParameters, ReceiveAircraftInfoRequest> {
            return tupled(
                AircraftRegistrationNumber.from(registrationNumber).mapLeft { it.toError() },
                AircraftModel.from(model).mapLeft { it.toError() },
                Count.from(seatCount).mapLeft { it.toError() }
            ).map {
                params -> ReceiveAircraftInfoRequest(params.a, params.b, params.c)
            }
        }
    }
}

data class InvalidAircraftInfoParameters(val message: String)

fun EmptyRegistrationNumberError.toError() = InvalidAircraftInfoParameters("Registration number cannot be empty")
fun EmptyAircraftModelError.toError() = InvalidAircraftInfoParameters("Aircraft model cannot be empty")
fun NegativeValueError.toError() = InvalidAircraftInfoParameters("Seat count cannot be negative")