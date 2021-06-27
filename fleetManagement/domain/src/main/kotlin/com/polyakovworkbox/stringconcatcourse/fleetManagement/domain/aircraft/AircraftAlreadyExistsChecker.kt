package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

interface AircraftAlreadyExistsChecker {
    fun check(registrationNumber: AircraftRegistrationNumber): Boolean
}