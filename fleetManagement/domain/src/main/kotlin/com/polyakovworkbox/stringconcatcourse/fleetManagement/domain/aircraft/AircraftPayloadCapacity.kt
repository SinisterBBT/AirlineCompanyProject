package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftPayloadCapacity internal constructor(val payloadCapacity: Int) : ValueObject {

    companion object {
        fun from(payloadCapacity: Int): Either<NegativePayloadCapacity, AircraftPayloadCapacity> =
            if (payloadCapacity >= 0) {
                AircraftPayloadCapacity(payloadCapacity).right()
            } else {
                NegativePayloadCapacity.left()
            }
    }
}

object NegativePayloadCapacity : BusinessError