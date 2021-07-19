package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject

class Passenger internal constructor(val fio: Fio, val passportData: PassportData) : ValueObject {
    companion object {
        fun from(
            fio: Fio,
            passportData: PassportData
        ): Passenger {
            return Passenger(fio, passportData)
        }
    }
}