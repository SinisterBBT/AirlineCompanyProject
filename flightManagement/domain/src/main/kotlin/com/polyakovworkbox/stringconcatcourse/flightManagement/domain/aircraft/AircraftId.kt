package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

data class AircraftId(val value: Long)

interface AircraftIdGenerator {
    fun generate(): AircraftId
}