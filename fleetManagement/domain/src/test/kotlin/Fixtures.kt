package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import java.time.LocalDate
import kotlin.random.Random

// Seat map value object
fun correctSeatMapLayout(): ArrayList<ArrayList<ArrayList<Char>>> {
    return arrayListOf(arrayListOf(
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S'),
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S'),
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S'),
        arrayListOf('X', 'X', 'X', 'X', 'X', 'X', 'X'),
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S'),
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S'),
        arrayListOf('S', 'S', 'S', 'X', 'S', 'S', 'S')))
}

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun aircraftModel(): AircraftModel {
    val result = AircraftModel.from("Model ${Random.nextInt()}")
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun payloadCapacity(): AircraftPayloadCapacity {
    val result = AircraftPayloadCapacity.from(Random.nextInt(Integer.SIZE - 1))
    check(result is Either.Right<AircraftPayloadCapacity>)
    return result.b
}

fun registrationNumber(): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from("R-Number ${Random.nextInt()}")
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

fun seatMap(): AircraftSeatMap {
    val result = AircraftSeatMap.from(correctSeatMapLayout())
    check(result is Either.Right<AircraftSeatMap>)
    return result.b
}

fun contractNumber(): AircraftContractNumber {
    val result = AircraftContractNumber.from("С-Number ${Random.nextInt()}")
    check(result is Either.Right<AircraftContractNumber>)
    return result.b
}

fun manufactureDate(): AircraftManufactureDate {
    val result = AircraftManufactureDate.from(LocalDate.now().minusDays(Random.nextInt(Integer.SIZE - 1).toLong()))
    check(result is Either.Right<AircraftManufactureDate>)
    return result.b
}

// Aircraft restorer
fun version() = Version.new()