package com.polyakovworkbox.stringconcatcourse.leasing.usecase

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftPayloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftSeatMap
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.Seat
import java.time.LocalDate
import kotlin.random.Random

// Seat map value object
fun correctSeatMapLayout(): List<Seat> {
    return listOf("A1", "A2", "A3", "A4", "A5", "A6",
            "B1", "B2", "B3", "B4", "B5", "B6",
            "C1", "C2", "C3", "C4", "C5", "C6",
            "D1", "D2", "D3", "D4", "D5", "D6",
            "F1", "F2", "F3", "F4", "F5", "F6",
            "G1", "G2", "G3", "G4", "G5", "G6"
    ).map { seatName ->
        seat(seatName)
    }
}

fun seat(seatName: String = "A1"): Seat {
    val result = Seat.from(seatName)
    check(result is Either.Right<Seat>)
    return result.b
}

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun aircraftModel(model: String = "Model ${Random.nextInt()}"): AircraftModel {
    val result = AircraftModel.from(model)
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun payloadCapacity(payloadCapacity: Int = Random.nextInt(Integer.SIZE - 1)): AircraftPayloadCapacity {
    val result = AircraftPayloadCapacity.from(payloadCapacity)
    check(result is Either.Right<AircraftPayloadCapacity>)
    return result.b
}

fun aircraftRegistrationNumber(regNum: String = "R-Number ${Random.nextInt()}"): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from(regNum)
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

fun seatMap(): AircraftSeatMap {
    val result = AircraftSeatMap.from(correctSeatMapLayout())
    check(result is Either.Right<AircraftSeatMap>)
    return result.b
}

fun aircraftContractNumber(contractNumber: String = "С-Number ${Random.nextInt()}"): AircraftContractNumber {
    val result = AircraftContractNumber.from(contractNumber)
    check(result is Either.Right<AircraftContractNumber>)
    return result.b
}

fun aircraftManufactureDate(manufactureDate: LocalDate = defaultManufactureDate()): AircraftManufactureDate {
    val result = AircraftManufactureDate.from(manufactureDate)
    check(result is Either.Right<AircraftManufactureDate>)
    return result.b
}

private fun defaultManufactureDate() = LocalDate.now().minusDays(Random.nextInt(Integer.SIZE - 1).toLong())

// Aircraft restorer
fun version() = Version.new()