package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

data class FlightId(val value: Long)

interface FlightIdGenerator {
    fun generate(): FlightId
}