package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

object AircraftRestorer {

    fun restoreAircraft(
        id: AircraftId,
        model: AircraftModel,
        seatMap: AircraftSeatMap,
        version: Version
    ): Aircraft {
        return Aircraft(
            id,
            model,
            seatMap,
            version
        )
    }
}