package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject

class Passenger internal constructor(val fullName: FullName, val passportData: PassportData) : ValueObject {
    companion object {
        fun from(
            fullName: FullName,
            passportData: PassportData
        ): Passenger {
            return Passenger(fullName, passportData)
        }
    }
}