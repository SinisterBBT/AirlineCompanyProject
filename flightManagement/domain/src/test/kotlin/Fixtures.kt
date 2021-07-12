import arrow.core.Either

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftSeatMap
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Seat
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureAirport
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.DepartureDate
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.ArrivalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random

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

private val idGenerator = object : AircraftIdGenerator {
    override fun generate() = aircraftId()
}

// Flight aggregate
fun flightId() = FlightId(Random.nextLong())

fun aircraft(): Aircraft {
    return Aircraft.acquireNewAircraft(idGenerator, registrationNumber(), aircraftModel(), seatMap())
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
    val result = ArrivalDate.from(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).plusDays(45).plusHours(2))
    check(result is Either.Right<ArrivalDate>)
    return result.b
}

// Aircraft restorer
fun version() = Version.new()