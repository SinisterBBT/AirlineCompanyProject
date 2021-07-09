package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

interface AircraftAlreadyExistsChecker {
    fun check(registrationNumber: AircraftRegistrationNumber): Boolean
}