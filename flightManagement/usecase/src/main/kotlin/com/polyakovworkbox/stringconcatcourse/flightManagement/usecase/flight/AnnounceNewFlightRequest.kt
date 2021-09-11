package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.common.WrongIataCode
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDateIsInThePast
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDateIsInThePast
import java.time.ZonedDateTime

data class AnnounceNewFlightRequest internal constructor(
    val departureAirport: Airport,
    val arrivalAirport: Airport,
    val departureDate: DepartureDate,
    val arrivalDate: ArrivalDate,
    val aircraftId: AircraftId
) {

    companion object {
        fun from(
            departureAirport: String,
            arrivalAirport: String,
            departureDate: ZonedDateTime,
            arrivalDate: ZonedDateTime,
            aircraftId: Long
        ): Either<InvalidFlightParameters, AnnounceNewFlightRequest> {
            return tupled(
                Airport.from(departureAirport).mapLeft { it.toError() },
                Airport.from(arrivalAirport).mapLeft { it.toError() },
                DepartureDate.from(departureDate).mapLeft { it.toError() },
                ArrivalDate.from(arrivalDate).mapLeft { it.toError() },
                AircraftId(aircraftId).right()
            ).map {
                params -> AnnounceNewFlightRequest(params.a, params.b, params.c, params.d, params.e)
            }
        }
    }
}

data class InvalidFlightParameters(val message: String)

fun WrongIataCode.toError() = InvalidFlightParameters("Iata code has incorrect format")
fun DepartureDateIsInThePast.toError() = InvalidFlightParameters("Departure date time cannot be in the past")
fun ArrivalDateIsInThePast.toError() = InvalidFlightParameters("Arrival date time cannot be in the past")