package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.domain.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.seatMap
import com.polyakovworkbox.stringconcatcourse.leasing.domain.version
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val aircraftId = aircraftId()
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()
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

        result.let {
            it.id shouldBe aircraftId
            it.model shouldBe model
            it.payloadCapacity shouldBe payloadCapacity
            it.registrationNumber shouldBe registrationNumber
            it.seatMap shouldBe seatMap
            it.contractNumber shouldBe contractNumber
            it.manufactureDate shouldBe manufactureDate
        }
    }
}