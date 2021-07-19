package com.polyakovworkbox.stringconcatcourse.maintenance.domain

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.ActualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.DepartureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightRestorer
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightState
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime
import java.time.Duration
import kotlin.random.Random

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

private val idGenerator = object : FlightIdGenerator {
    override fun generate() = flightId()
}

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
    val result = FlightTime.from(Duration.ofHours(Random.nextLong(1, 24)))
    check(result is Either.Right<FlightTime>)
    return result.b
}

fun flight(state: FlightState = FlightState.REGISTERED) =
        FlightRestorer.restoreFlight(
                idGenerator.generate(),
                departureAirport(),
                actualArrivalAirport(),
                flightTime(),
                state,
                version()
        )

// version
fun version() = Version.new()