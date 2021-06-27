package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

data class AircraftId(val value: Long)

interface AircraftIdGenerator {
    fun generate(): AircraftId
}