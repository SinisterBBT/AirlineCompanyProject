package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

interface AirportAllowsFlight {
    fun check(departureDate: DepartureDate): Boolean
}