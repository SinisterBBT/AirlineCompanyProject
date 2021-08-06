package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count

object AircraftRestorer {

    fun restoreAircraft(
        id: AircraftId,
        aircraftRegistrationNumber: AircraftRegistrationNumber,
        model: AircraftModel,
        seatCount: Count,
        version: Version
    ): Aircraft {
        return Aircraft(
            id,
            aircraftRegistrationNumber,
            model,
            seatCount,
            version
        )
    }
}