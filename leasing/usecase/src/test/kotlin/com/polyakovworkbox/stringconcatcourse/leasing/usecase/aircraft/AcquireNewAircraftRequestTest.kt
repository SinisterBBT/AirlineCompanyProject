package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.seatMap
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

internal class AcquireNewAircraftRequestTest {

    @Test
    fun `AcquireNewAircraftRequest is equal to another one with the same parameters`() {
        val model = aircraftModel("Airbus0000")
        val payloadCapacity = payloadCapacity(100500)
        val registrationNumber = aircraftRegistrationNumber("123-456-abc-DEF")
        val seatMap = seatMap()
        val contractNumber = aircraftContractNumber("123-45")
        val manufactureDate = aircraftManufactureDate(LocalDate.of(2010, Month.APRIL, 1))

        val result = AcquireNewAircraftRequest.from(
                model.value,
                payloadCapacity.payloadCapacity,
                registrationNumber.registrationNumber,
                seatMap.seatMap,
                contractNumber.contractNumber,
                manufactureDate.manufactureDate
        )

        val result2 = AcquireNewAircraftRequest.from(
                model.value,
                payloadCapacity.payloadCapacity,
                registrationNumber.registrationNumber,
                seatMap.seatMap,
                contractNumber.contractNumber,
                manufactureDate.manufactureDate
        )

        (result == result2) shouldBe true

    }

    @Test
    fun `AcquireNewAircraftRequest is created successfully`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = AcquireNewAircraftRequest.from(
            model.value,
            payloadCapacity.payloadCapacity,
            registrationNumber.registrationNumber,
            seatMap.seatMap,
            contractNumber.contractNumber,
            manufactureDate.manufactureDate
        )

        result shouldBe AcquireNewAircraftRequest(
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate
        )
    }
}