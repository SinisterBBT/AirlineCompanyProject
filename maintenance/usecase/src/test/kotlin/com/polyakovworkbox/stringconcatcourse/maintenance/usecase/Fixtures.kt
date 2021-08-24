package com.polyakovworkbox.stringconcatcourse.maintenance.usecase

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightRestorer
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightState
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight.FlightTime
import com.polyakovworkbox.stringconcatcourse.maintenance.usecase.flight.FlightPersister
import java.time.Duration
import kotlin.random.Random

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

private val idGenerator = object : FlightIdGenerator {
    override fun generate() = flightId()
}

fun departureAirport(departureAirport: String = "LED"): Airport {
    val result = Airport.from(departureAirport)
    check(result is Either.Right<Airport>)
    return result.b
}

fun actualArrivalAirport(arrivalAirport: String = "MSK"): Airport {
    val result = Airport.from(arrivalAirport)
    check(result is Either.Right<Airport>)
    return result.b
}

fun flightTime(flightTime: Duration = Duration.ofHours(Random.nextLong(1, 24))): FlightTime {
    val result = FlightTime.from(flightTime)
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

class TestFlightPersister : HashMap<FlightId, Flight>(), FlightPersister {
    override fun save(flight: Flight) {
        this[flight.id] = flight
    }
}