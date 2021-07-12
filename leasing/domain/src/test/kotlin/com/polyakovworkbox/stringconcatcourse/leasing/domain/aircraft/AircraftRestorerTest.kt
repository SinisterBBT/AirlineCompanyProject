package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val aircraftId = aircraftId()
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = registrationNumber()
        val seatMap = seatMap()
        val contractNumber = contractNumber()
        val manufactureDate = manufactureDate()
        val version = version()

        val result = AircraftRestorer.restoreAircraft(
            aircraftId,
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate,
            version
        )

        result.id shouldBe aircraftId
        result.model shouldBe model
        result.payloadCapacity shouldBe payloadCapacity
        result.registrationNumber shouldBe registrationNumber
        result.seatMap shouldBe seatMap
        result.contractNumber shouldBe contractNumber
        result.manufactureDate shouldBe manufactureDate
    }
}