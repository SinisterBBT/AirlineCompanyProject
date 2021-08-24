package com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.common.WrongIataCode
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTimeNegativeError
import java.time.Duration

data class RegisterFlightRequest(
    val departureAirport: Airport,
    val actualArrivalAirport: Airport,
    val flightTime: FlightTime
) {

    companion object {
        fun from(
            departureAirport: String,
            actualArrivalAirport: String,
            flightTime: Duration
        ): Either<InvalidFlightParameters, RegisterFlightRequest> {
            return tupled(
                Airport.from(departureAirport).mapLeft { it.toError() },
                Airport.from(actualArrivalAirport).mapLeft { it.toError() },
                FlightTime.from(flightTime).mapLeft { it.toError() }
            ).map {
                params -> RegisterFlightRequest(params.a, params.b, params.c)
            }
        }
    }
}

data class InvalidFlightParameters(val message: String)

fun WrongIataCode.toError() = InvalidFlightParameters("Iata code has incorrect format")
fun FlightTimeNegativeError.toError() = InvalidFlightParameters("Flight time cannot be negative")