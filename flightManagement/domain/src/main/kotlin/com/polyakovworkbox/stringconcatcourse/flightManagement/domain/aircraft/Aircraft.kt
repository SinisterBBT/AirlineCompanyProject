package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count

class Aircraft internal constructor(
    id: AircraftId,
    val registrationNumber: AircraftRegistrationNumber,
    val model: AircraftModel,
    val seatCount: Count,
    version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun receiveNewAircraftInfo(
            idGenerator: AircraftIdGenerator,
            registrationNumber: AircraftRegistrationNumber,
            model: AircraftModel,
            seatCount: Count,
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
