package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft.AircraftInfoPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.AircraftIsInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.AircraftIsNotInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.flight.FlightPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket.TicketPersister
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun aircraftModel(model: String = "Model ${Random.nextInt()}"): AircraftModel {
    val result = AircraftModel.from(model)
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun seatCount(seatCount: Int = Random.nextInt(Integer.SIZE - 1)): Count {
    val result = Count.from(seatCount)
    check(result is Either.Right<Count>)
    return result.b
}

fun aircraftRegistrationNumber(regNum: String = "R-Number ${Random.nextInt()}"): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from(regNum)
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

// Aircraft persister
class TestAircraftInfoPersister : HashMap<AircraftId, Aircraft>(), AircraftInfoPersister {
    override fun save(aircraft: Aircraft) {
        this[aircraft.id] = aircraft
    }
}

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

fun departureAirport(departureAirport: String = "LED"): Airport {
    val result = Airport.from(departureAirport)
    check(result is Either.Right<Airport>)
    return result.b
}

fun arrivalAirport(arrivalAirport: String = "MSK"): Airport {
    val result = Airport.from(arrivalAirport)
    check(result is Either.Right<Airport>)
    return result.b
}

fun departureDate(zonedDateTime: ZonedDateTime = defaultDepartureDate()): DepartureDate {
    val result = DepartureDate.from(zonedDateTime)
    check(result is Either.Right<DepartureDate>)
    return result.b
}

fun defaultDepartureDate(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45)

fun arrivalDate(zonedDateTime: ZonedDateTime = defaultArrivalDate()): ArrivalDate {
    val result = ArrivalDate.from(zonedDateTime)
    check(result is Either.Right<ArrivalDate>)
    return result.b
}

fun defaultArrivalDate(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45).plusHours(2)

class TestFlightPersister : HashMap<FlightId, Flight>(), FlightPersister {
    override fun save(flight: Flight) {
        this[flight.id] = flight
    }
}

// Ticket aggregate
fun ticketId(ticketId: Long = Random.nextLong()) = TicketId(ticketId)

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}

class TestTicketPersister : HashMap<TicketId, Ticket>(), TicketPersister {
    override fun save(ticket: Ticket) {
        this[ticket.id] = ticket
    }
}

val flightIdGenerator = object : FlightIdGenerator {
    override fun generate() = flightId()
}

class FlightIdPseudoGenerator(val flightId: FlightId) : FlightIdGenerator {
    override fun generate() = flightId
}

fun flight(departureDate: DepartureDate = departureDate(defaultDepartureDate()), flightId: FlightId?): Flight {
    val result = Flight.announceNewFlight(
        if (flightId == null) flightIdGenerator else FlightIdPseudoGenerator(flightId),
        AircraftIsInOperation,
        AircraftIsNotInFlight,
        AirportAllowsFlight,
        departureAirport(),
        arrivalAirport(),
        departureDate,
        arrivalDate(departureDate.departureDate.plusHours(10)),
        aircraftId()
    )

    check(result is Either.Right<Flight>)
    return result.b
}