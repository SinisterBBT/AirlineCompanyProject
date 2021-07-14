package com.polyakovworkbox.stringconcatcourse.maintenance.domain

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.ActualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.DepartureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime
import java.time.Duration
import kotlin.random.Random

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

fun departureAirport(): DepartureAirport {
    val result = DepartureAirport.from("LED")
    check(result is Either.Right<DepartureAirport>)
    return result.b
}

fun actualArrivalAirport(): ActualArrivalAirport {
    val result = ActualArrivalAirport.from("MSK")
    check(result is Either.Right<ActualArrivalAirport>)
    return result.b
}

fun flightTime(): FlightTime {
    val result = FlightTime.from(Duration.ofHours(5))
    check(result is Either.Right<FlightTime>)
    return result.b
}

// version
fun version() = Version.new()