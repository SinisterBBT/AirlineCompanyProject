package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

object AircraftRestorer {

    fun restoreAircraft(
        id: AircraftId,
        model: AircraftModel,
        payloadCapacity: AircraftPayloadCapacity,
        registrationNumber: AircraftRegistrationNumber,
        seatMap: AircraftSeatMap,
        contractNumber: AircraftContractNumber,
        manufactureDate: AircraftManufactureDate,
        version: Version
    ): Aircraft {
        return Aircraft(
            id,
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate,
            version
        )
    }
}