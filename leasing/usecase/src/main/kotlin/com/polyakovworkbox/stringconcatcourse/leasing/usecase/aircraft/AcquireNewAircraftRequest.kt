package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.getOrElse
import arrow.core.left
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftPayloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftSeatMap
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.EmptyAircraftModelError
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.EmptyContractNumberError
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.EmptyRegistrationNumberError
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.WrongSeatNumberFormatError
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.ManufactureDateInFutureError
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.NegativePayloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.Seat
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.WrongSeatMapLayout
import java.time.LocalDate

data class AcquireNewAircraftRequest internal constructor(
    val model: AircraftModel,
    val payloadCapacity: AircraftPayloadCapacity,
    val registrationNumber: AircraftRegistrationNumber,
    val seatMap: AircraftSeatMap,
    val contractNumber: AircraftContractNumber,
    val manufactureDate: AircraftManufactureDate
) {

    companion object {
        fun from(
            model: String,
            payloadCapacity: Int,
            registrationNumber: String,
            seatMap: List<String>,
            contractNumber: String,
            manufactureDate: LocalDate
        ): Either<InvalidAircraftParameters, AcquireNewAircraftRequest> {
            return tupled(
                AircraftModel.from(model).mapLeft { it.toError() },
                AircraftPayloadCapacity.from(payloadCapacity).mapLeft { it.toError() },
                AircraftRegistrationNumber.from(registrationNumber).mapLeft { it.toError() },
                AircraftSeatMap.from(
                    seatMap.map {
                            seat -> Seat.from(seat).getOrElse {
                                return WrongSeatNumberFormatError.toError().left()
                            }
                    }
                ).mapLeft { it.toError() },
                AircraftContractNumber.from(contractNumber).mapLeft { it.toError() },
                AircraftManufactureDate.from(manufactureDate).mapLeft { it.toError() }
            ).map {
                params -> AcquireNewAircraftRequest(params.a, params.b, params.c, params.d, params.e, params.f)
            }
        }
    }
}

data class InvalidAircraftParameters(val message: String)

fun EmptyAircraftModelError.toError() = InvalidAircraftParameters("Aircraft model cannot be empty")
fun NegativePayloadCapacity.toError() = InvalidAircraftParameters("Payload capacity cannot be negative")
fun EmptyRegistrationNumberError.toError() = InvalidAircraftParameters("Aircraft registration number cannot be empty")
fun WrongSeatMapLayout.toError() = InvalidAircraftParameters("Seat map layout cannot be without any seat in it")
fun WrongSeatNumberFormatError.toError() = InvalidAircraftParameters("Seat number is empty or has wrong format")
fun EmptyContractNumberError.toError() = InvalidAircraftParameters("Contract number cannot be empty")
fun ManufactureDateInFutureError.toError() = InvalidAircraftParameters("Manufacture date must be in the past")