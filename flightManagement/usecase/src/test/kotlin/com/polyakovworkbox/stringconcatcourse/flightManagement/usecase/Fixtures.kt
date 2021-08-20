package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft.AircraftInfoPersister
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