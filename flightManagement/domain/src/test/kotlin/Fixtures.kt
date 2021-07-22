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
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.FullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderRestorer
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderState
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.PassportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsAnnounced
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.FlightIsToSoonForPublishing
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

private fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length).map { allowedChars.random() }.joinToString("")
}

// Seat map value object
fun aircraftSeatMap(): List<Seat> {
     return listOf("A1", "A2", "A3", "A4", "A5", "A6",
             "B1", "B2", "B3", "B4", "B5", "B6",
             "C1", "C2", "C3", "C4", "C5", "C6",
             "D1", "D2", "D3", "D4", "D5", "D6",
             "F1", "F2", "F3", "F4", "F5", "F6",
             "G1", "G2", "G3", "G4", "G5", "G6").map { seatName ->
                 val seat = Seat.from(seatName)
                 check(seat is Either.Right<Seat>)
                 seat.b
     }
}

fun seat(seatNumber: String = "A1"): Seat {
    val result = Seat.from(seatNumber)
    check(result is Either.Right<Seat>)
    return result.b
}

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun aircraftRegistrationNumber(regNumber: String = "R-Number ${Random.nextInt()}"): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from(regNumber)
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

fun aircraftModel(model: String = "Model ${Random.nextInt()}"): AircraftModel {
    val result = AircraftModel.from(model)
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun seatMap(): AircraftSeatMap {
    val result = AircraftSeatMap.from(aircraftSeatMap())
    check(result is Either.Right<AircraftSeatMap>)
    return result.b
}

val aircraftIdGenerator = object : AircraftIdGenerator {
    override fun generate() = aircraftId()
}

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

fun aircraft(): Aircraft {
    return Aircraft.acquireNewAircraft(aircraftIdGenerator, aircraftRegistrationNumber(), aircraftModel(), seatMap())
}

fun departureAirport(departureAirport: String = "LED"): DepartureAirport {
    val result = DepartureAirport.from(departureAirport)
    check(result is Either.Right<DepartureAirport>)
    return result.b
}

fun arrivalAirport(arrivalAirport: String = "MSK"): ArrivalAirport {
    val result = ArrivalAirport.from(arrivalAirport)
    check(result is Either.Right<ArrivalAirport>)
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

object AircraftIsInOperation : AircraftIsNotInOperation {
    override fun check(registrationNumber: AircraftRegistrationNumber) = true
}

object AircraftDoesNotExist : AircraftIsNotInOperation {
    override fun check(registrationNumber: AircraftRegistrationNumber) = false
}

object AircraftIsNotInFlight : AircraftIsAlreadyInFlight {
    override fun check(registrationNumber: AircraftRegistrationNumber) = false
}

object AircraftIsAlreadyInFlight : AircraftIsAlreadyInFlight {
    override fun check(registrationNumber: AircraftRegistrationNumber) = true
}

object AirportAllowsFlight : AirportAllowsFlight {
    override fun check(departureDate: DepartureDate) = true
}

object AirportDoesNotAllowFlight : AirportAllowsFlight {
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

object FlightIsAnnounced : FlightIsAnnounced {
    override fun check(flightId: FlightId) = true
}

object FlightIsNotAnnounced : FlightIsAnnounced {
    override fun check(flightId: FlightId) = false
}

object FlightIsToSoonForPublishing : FlightIsToSoonForPublishing {
    override fun check(flightId: FlightId) = true
}

object FlightIsNotToSoonForPublishing : FlightIsToSoonForPublishing {
    override fun check(flightId: FlightId) = false
}

// Passenger VO
fun fullName(fullName: String = defaultFullName()): FullName {
    val result = FullName.from(fullName)
    check(result is Either.Right<FullName>)
    return result.b
}

private fun defaultFullName() =
        "${getRandomString(Random.nextInt(3, 30))} " +
                "${getRandomString(Random.nextInt(3, 30))} " +
                getRandomString(Random.nextInt(3, 30))

fun passportData(passportData: String = "${Random.nextInt()} ${Random.nextInt()}"): PassportData {
    val result = PassportData.from(passportData)
    check(result is Either.Right<PassportData>)
    return result.b
}

// OrderItem VO
fun passenger(fullName: FullName = fullName(), passportData: PassportData = passportData()) =
        Passenger.from(fullName, passportData)

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

fun email(emailString: String = defaultEmail()): Email {
    val email = Email.from(emailString)
    check(email is Either.Right<Email>)
    return email.b
}

private fun defaultEmail() = "${getRandomString(Random.nextInt(3, 1000))}@${getRandomString(Random.nextInt(3, 30))}"

fun orderItem(passenger: Passenger = passenger(), ticket: Ticket = ticket()) = OrderItem.from(passenger, ticket)

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