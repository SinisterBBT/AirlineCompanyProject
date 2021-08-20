package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.seatMap
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.seatMapLayout
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

internal class AcquireNewAircraftRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val model = aircraftModel("Airbus0000")
        val payloadCapacity = payloadCapacity(100500)
        val registrationNumber = aircraftRegistrationNumber("123-456-abc-DEF")
        val seatMapLayout = seatMapLayout()
        val contractNumber = aircraftContractNumber("123-45")
        val manufactureDate = aircraftManufactureDate(LocalDate.of(2010, Month.APRIL, 1))

        val result = AcquireNewAircraftRequest.from(
                model.value,
                payloadCapacity.payloadCapacity,
                registrationNumber.registrationNumber,
                seatMapLayout,
                contractNumber.contractNumber,
                manufactureDate.manufactureDate
        )

        val result2 = AcquireNewAircraftRequest.from(
                model.value,
                payloadCapacity.payloadCapacity,
                registrationNumber.registrationNumber,
                seatMapLayout,
                contractNumber.contractNumber,
                manufactureDate.manufactureDate
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = seatMapLayout()
        val seatMap = seatMap(seatMapLayout)
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        val compareToInstance = AcquireNewAircraftRequest(
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate
        )

        result shouldBeRight {
            it.model shouldBe compareToInstance.model
            it.payloadCapacity shouldBe compareToInstance.payloadCapacity
            it.registrationNumber shouldBe compareToInstance.registrationNumber
            it.seatMap shouldBe compareToInstance.seatMap
            it.contractNumber shouldBe compareToInstance.contractNumber
            it.manufactureDate shouldBe compareToInstance.manufactureDate
        }
    }

    @Test
    fun `has empty model value`() {
        val model = ""
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = seatMapLayout()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Aircraft model cannot be empty")
    }

    @Test
    fun `has negative payload capacity value`() {
        val model = aircraftModel()
        val payloadCapacity = -1
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = seatMapLayout()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Payload capacity cannot be negative")
    }

    @Test
    fun `has empty registration number value`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = ""
        val seatMapLayout = seatMapLayout()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Aircraft registration number cannot be empty")
    }

    @Test
    fun `has empty seat map layout`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = emptyList<String>()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Seat map layout cannot be without any seat in it")
    }

    @Test
    fun `has wrong seat format in seat map layout`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = listOf("abc")
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Seat number is empty or has wrong format")
    }

    @Test
    fun `has empty contract number value`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = seatMapLayout()
        val contractNumber = ""
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Contract number cannot be empty")
    }

    @Test
    fun `has manufacture date value in the future`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMapLayout = seatMapLayout()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = LocalDate.now().plusDays(10)

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMapLayout,
            contractNumber.contractNumber,
            manufactureDate
        )

        result shouldBeLeft InvalidAircraftParameters("Manufacture date must be in the past")
    }
}