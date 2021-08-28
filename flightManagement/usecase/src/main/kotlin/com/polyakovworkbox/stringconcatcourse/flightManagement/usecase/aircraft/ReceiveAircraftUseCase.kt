package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator

class ReceiveAircraftUseCase(
    private val aircraftPersister: AircraftPersister,
    private val idGenerator: AircraftIdGenerator,
) : ReceiveAircraft {

    override fun execute(request: ReceiveAircraftRequest): AircraftId {
        return Aircraft.receiveNewAircraft(
            idGenerator,
            request.registrationNumber,
            request.model,
            request.seatCount
        ).let {
            aircraftPersister.save(it)
            it.id
        }
    }
}