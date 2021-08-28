package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.seatCount
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ReceiveAircraftRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val model = aircraftModel("Airbus0000")
        val registrationNumber = aircraftRegistrationNumber("123-456-abc-DEF")
        val seatCount = seatCount(66)

        val result = ReceiveAircraftRequest.from(
            model.value,
            registrationNumber.registrationNumber,
            seatCount.value
        )

        val result2 = ReceiveAircraftRequest.from(
            model.value,
            registrationNumber.registrationNumber,
            seatCount.value
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatCount = seatCount()

        val result = ReceiveAircraftRequest.from(
            registrationNumber.registrationNumber,
            model.value,
            seatCount.value
        )

        val compareToInstance = ReceiveAircraftRequest(
            registrationNumber,
            model,
            seatCount
        )

        result shouldBeRight {
            it.registrationNumber shouldBe compareToInstance.registrationNumber
            it.model shouldBe compareToInstance.model
            it.seatCount shouldBe compareToInstance.seatCount
        }
    }

    @Test
    fun `has empty registration number`() {
        val registrationNumber = ""
        val model = aircraftModel()
        val seatCount = seatCount()

        val result = ReceiveAircraftRequest.from(
            registrationNumber,
            model.value,
            seatCount.value
        )

        result shouldBeLeft InvalidAircraftParameters("Registration number cannot be empty")
    }

    @Test
    fun `has empty model`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = ""
        val seatCount = seatCount()

        val result = ReceiveAircraftRequest.from(
            registrationNumber.registrationNumber,
            model,
            seatCount.value
        )

        result shouldBeLeft InvalidAircraftParameters("Aircraft model cannot be empty")
    }

    @Test
    fun `has negative seat count`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatCount = -5

        val result = ReceiveAircraftRequest.from(
            registrationNumber.registrationNumber,
            model.value,
            seatCount
        )

        result shouldBeLeft InvalidAircraftParameters("Seat count cannot be negative")
    }
}