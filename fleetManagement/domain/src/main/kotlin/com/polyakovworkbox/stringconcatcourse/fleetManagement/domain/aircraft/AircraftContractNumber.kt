package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftContractNumber internal constructor(val contractNumber: String) : ValueObject {

    companion object {
        fun from(contractNumber: String): Either<EmptyContractNumberError, AircraftContractNumber> =
            if (contractNumber.isNotBlank()) {
                AircraftContractNumber(contractNumber).right()
            } else {
                EmptyContractNumberError.left()
            }
    }
}

object EmptyContractNumberError : BusinessError
