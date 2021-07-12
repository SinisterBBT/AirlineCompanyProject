package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

object AircraftRestorer {

    fun restoreAircraft(
        id: AircraftId,
        aircraftRegistrationNumber: AircraftRegistrationNumber,
        model: AircraftModel,
        seatMap: AircraftSeatMap,
        version: Version
    ): Aircraft {
        return Aircraft(
            id,
            aircraftRegistrationNumber,
            model,
            seatMap,
            version
        )
    }
}