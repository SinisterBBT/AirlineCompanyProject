package com.polyakovworkbox.stringconcatcourse.flightManagement.domain

import arrow.core.Either

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftSeatMap
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Seat
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlightChecker
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperationChecker
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlightChecker
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Fio
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderRestorer
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderState
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.PassportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsAnnouncedChecker
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsToSoonForPublishingChecker
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length).map { allowedChars.random() }.joinToString("")
}

// Seat map value object
fun correctSeatMapLayout(): ArrayList<Seat> {
    val result = ArrayList<Seat>()

     listOf("A1", "A2", "A3", "A4", "A5", "A6",
             "B1", "B2", "B3", "B4", "B5", "B6",
             "C1", "C2", "C3", "C4", "C5", "C6",
             "D1", "D2", "D3", "D4", "D5", "D6",
             "F1", "F2", "F3", "F4", "F5", "F6",
             "G1", "G2", "G3", "G4", "G5", "G6").forEach { seatName ->
         Seat.from(seatName).also {
             check(it is Either.Right<Seat>)
             result.add(it.b)
        }
     }

    return result
}

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun registrationNumber(): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from("R-Number ${Random.nextInt()}")
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

fun aircraftModel(): AircraftModel {
    val result = AircraftModel.from("Model ${Random.nextInt()}")
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun seatMap(): AircraftSeatMap {
    val result = AircraftSeatMap.from(correctSeatMapLayout())
    check(result is Either.Right<AircraftSeatMap>)
    return result.b
}

val aircraftIdGenerator = object : AircraftIdGenerator {
    override fun generate() = aircraftId()
}

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

fun aircraft(): Aircraft {
    return Aircraft.acquireNewAircraft(aircraftIdGenerator, registrationNumber(), aircraftModel(), seatMap())
}

fun departureAirport(): DepartureAirport {
    val result = DepartureAirport.from("LED")
    check(result is Either.Right<DepartureAirport>)
    return result.b
}

fun arrivalAirport(): ArrivalAirport {
    val result = ArrivalAirport.from("MSK")
    check(result is Either.Right<ArrivalAirport>)
    return result.b
}

fun departureDate(): DepartureDate {
    val result = DepartureDate.from(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45))
    check(result is Either.Right<DepartureDate>)
    return result.b
}

fun arrivalDate(): ArrivalDate {
    val result = ArrivalDate.from(
            ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45).plusHours(2))
    check(result is Either.Right<ArrivalDate>)
    return result.b
}

object AircraftIsInOperation : AircraftIsNotInOperationChecker {
    override fun check(registrationNumber: AircraftRegistrationNumber) = true
}

object AircraftDoesNotExist : AircraftIsNotInOperationChecker {
    override fun check(registrationNumber: AircraftRegistrationNumber) = false
}

object AircraftIsNotInFlight : AircraftIsAlreadyInFlightChecker {
    override fun check(registrationNumber: AircraftRegistrationNumber) = false
}

object AircraftIsAlreadyInFlight : AircraftIsAlreadyInFlightChecker {
    override fun check(registrationNumber: AircraftRegistrationNumber) = true
}

object AirportAllowsFlight : AirportAllowsFlightChecker {
    override fun check(departureDate: DepartureDate) = true
}

object AirportDoesNotAllowFlight : AirportAllowsFlightChecker {
    override fun check(departureDate: DepartureDate) = false
}

val flightIdGenerator = object : FlightIdGenerator {
    override fun generate() = flightId()
}

// Ticket aggregate
fun ticketId() = TicketId(Random.nextLong())

fun flight(): Flight {
    val result = Flight.announceNewFlight(
            flightIdGenerator,
            AircraftIsInOperation,
            AircraftIsNotInFlight,
            AirportAllowsFlight,
            departureAirport(),
            arrivalAirport(),
            departureDate(),
            arrivalDate(),
            aircraft()
    )

    check(result is Either.Right<Flight>)
    return result.b
}

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}

object FlightIsAnnounced : FlightIsAnnouncedChecker {
    override fun check(flightId: FlightId) = true
}

object FlightIsNotAnnounced : FlightIsAnnouncedChecker {
    override fun check(flightId: FlightId) = false
}

object FlightIsToSoonForPublishing : FlightIsToSoonForPublishingChecker {
    override fun check(flightId: FlightId) = true
}

object FlightIsNotToSoonForPublishing : FlightIsToSoonForPublishingChecker {
    override fun check(flightId: FlightId) = false
}

// Passenger VO
fun fio(): Fio {
    val result = Fio.from(
            "${getRandomString(Random.nextInt(3, 30))} " +
                "${getRandomString(Random.nextInt(3, 30))} " +
                    getRandomString(Random.nextInt(3, 30)))
    check(result is Either.Right<Fio>)
    return result.b
}

fun passportData(): PassportData {
    val result = PassportData.from("${Random.nextInt()} ${Random.nextInt()}")
    check(result is Either.Right<PassportData>)
    return result.b
}

// OrderItem VO
fun passenger() = Passenger.from(fio(), passportData())

private val ticketIdGenerator = object : TicketIdGenerator {
    override fun generate() = ticketId()
}

fun ticket(price: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Ticket {
    val ticket = Ticket.publishTicket(
            ticketIdGenerator, FlightIsAnnounced, FlightIsNotToSoonForPublishing, flight(), price(price))
    check(ticket is Either.Right<Ticket>)
    return ticket.b
}

// Order
fun orderId() = OrderId(Random.nextLong())

fun email(): Email {
    val email = Email.from("${getRandomString(Random.nextInt(3, 1000))}@${getRandomString(Random.nextInt(3, 30))}")
    check(email is Either.Right<Email>)
    return email.b
}

fun orderItem(ticket: Ticket = ticket()) = OrderItem.from(passenger(), ticket)

fun order(
    state: OrderState = OrderState.COMPLETED,
    orderItems: List<OrderItem> = listOf(orderItem()),
): Order {
    return OrderRestorer.restoreOrder(
            id = orderId(),
            email = email(),
            orderItems = orderItems,
            state = state,
            version = version()
    )
}

// version
fun version() = Version.new()