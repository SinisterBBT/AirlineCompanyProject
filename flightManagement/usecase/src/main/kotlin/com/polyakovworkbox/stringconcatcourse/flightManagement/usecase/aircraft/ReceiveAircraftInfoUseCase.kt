package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator

class ReceiveAircraftInfoUseCase(
    private val aircraftInfoPersister: AircraftInfoPersister,
    private val idGenerator: AircraftIdGenerator,
) : ReceiveAircraftInfo {

    override fun execute(request: ReceiveAircraftInfoRequest): AircraftId {
        return Aircraft.receiveNewAircraftInfo(
            idGenerator,
            request.registrationNumber,
            request.model,
            request.seatCount
        ).let {
            aircraftInfoPersister.save(it)
            it.id
        }
    }
}