package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

class Aircraft internal constructor(
    id: AircraftId,
    val registrationNumber: AircraftRegistrationNumber,
    val model: AircraftModel,
    val seatCount: AircraftSeatCount,
    version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun acquireNewAircraft(
            idGenerator: AircraftIdGenerator,
            registrationNumber: AircraftRegistrationNumber,
            model: AircraftModel,
            seatCount: AircraftSeatCount,
        ): Aircraft {
            return Aircraft(
                    idGenerator.generate(),
                    registrationNumber,
                    model,
                    seatCount,
                    Version.new()
                ).apply {
                    addEvent(AircraftInfoAcquiredEvent(this.id))
                }
        }
    }
}
