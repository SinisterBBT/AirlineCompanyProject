package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

data class AircraftId(val value: Long)

interface AircraftIdGenerator {
    fun generate(): AircraftId
}