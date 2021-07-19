package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

data class FlightId(val value: Long)

interface FlightIdGenerator {
    fun generate(): FlightId
}