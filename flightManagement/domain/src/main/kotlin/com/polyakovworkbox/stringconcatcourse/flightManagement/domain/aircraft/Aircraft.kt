package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

class Aircraft internal constructor(
    id: AircraftId,
    val model: AircraftModel,
    val seatMap: AircraftSeatMap,
    version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun acquireNewAircraft(
            idGenerator: AircraftIdGenerator,
            model: AircraftModel,
            seatMap: AircraftSeatMap,
        ): Aircraft {
            return Aircraft(
                    idGenerator.generate(),
                    model,
                    seatMap,
                    Version.new()
                ).apply {
                    addEvent(AircraftInfoAcquiredEvent(this.id))
                }
        }
    }
}
