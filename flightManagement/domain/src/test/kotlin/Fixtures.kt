package com.polyakovworkbox.stringconcatcourse.flightManagement.domain

import arrow.core.Either

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsAlreadyInFlight
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AircraftIsNotInOperation
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.AirportAllowsFlight
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
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

private fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length).map { allowedChars.random() }.joinToString("")
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

fun seatCount(seatCount: Int = Random.nextInt(1, 2000)): Count {
    val result = Count.from(seatCount)
    check(result is Either.Right<Count>)
    return result.b
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

fun toLateDepartureDate(): DepartureDate = DepartureDate(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(30))

fun arrivalDate(zonedDateTime: ZonedDateTime = defaultArrivalDate()): ArrivalDate {
    val result = ArrivalDate.from(zonedDateTime)
    check(result is Either.Right<ArrivalDate>)
    return result.b
}

fun defaultArrivalDate(): ZonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45).plusHours(2)

object AircraftIsInOperation : AircraftIsNotInOperation {
    override fun check(aircraftId: AircraftId) = true
}

object AircraftDoesNotExist : AircraftIsNotInOperation {
    override fun check(aircraftId: AircraftId) = false
}

object AircraftIsNotInFlight : AircraftIsAlreadyInFlight {
    override fun check(aircraftId: AircraftId) = false
}

object AircraftIsAlreadyInFlight : AircraftIsAlreadyInFlight {
    override fun check(aircraftId: AircraftId) = true
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
fun ticketId(ticketId: Long = Random.nextLong()) = TicketId(ticketId)

fun flight(departureDate: DepartureDate = departureDate(defaultDepartureDate())): Flight {
    val result = Flight.announceNewFlight(
            flightIdGenerator,
            AircraftIsInOperation,
            AircraftIsNotInFlight,
            AirportAllowsFlight,
            departureAirport(),
            arrivalAirport(),
            departureDate,
            arrivalDate(),
            aircraftId()
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

fun passportData(passportData: String = defaultPassportData()): PassportData {
    val result = PassportData.from(passportData)
    check(result is Either.Right<PassportData>)
    return result.b
}

private fun defaultPassportData() = "${Random.nextInt(1111, 9999)} ${Random.nextInt(111111, 999999)}"

// OrderItem VO
fun passenger(fullName: FullName = fullName(), passportData: PassportData = passportData()) =
        Passenger.from(fullName, passportData)

// Order
fun orderId() = OrderId(Random.nextLong())

fun email(emailString: String = defaultEmail()): Email {
    val email = Email.from(emailString)
    check(email is Either.Right<Email>)
    return email.b
}

private fun defaultEmail() = "${getRandomString(Random.nextInt(3, 1000))}@${getRandomString(Random.nextInt(3, 30))}"

fun orderItem(
    passenger: Passenger = passenger(),
    ticketId: TicketId = ticketId(),
    price: Price = price()
) = OrderItem.from(passenger, ticketId, price)

fun order(
    state: OrderState = OrderState.WAITING_FOR_PAYMENT,
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