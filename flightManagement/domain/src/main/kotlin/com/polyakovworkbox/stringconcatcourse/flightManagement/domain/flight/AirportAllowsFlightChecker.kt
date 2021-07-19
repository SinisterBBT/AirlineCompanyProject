package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

interface AirportAllowsFlightChecker {
    fun check(departureDate: DepartureDate): Boolean
}