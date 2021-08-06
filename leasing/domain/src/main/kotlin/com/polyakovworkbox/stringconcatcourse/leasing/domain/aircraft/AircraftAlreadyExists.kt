package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

interface AircraftAlreadyExists {
    fun check(registrationNumber: AircraftRegistrationNumber): Boolean
}