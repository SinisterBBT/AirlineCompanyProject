package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.TestAircraftInfoPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.seatCount
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ReceiveAircraftInfoUseCaseTest {

    @Test
    fun `successfully received`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatCount = seatCount()

        val aircraftInfoPersister = TestAircraftInfoPersister()
        val id = TestAircraftIdGenerator.id

        val result = ReceiveAircraftInfoUseCase(
            aircraftInfoPersister,
            TestAircraftIdGenerator
        ).execute(
            ReceiveAircraftInfoRequest(
                registrationNumber,
                model,
                seatCount
            )
        )

        result.value shouldBe id.value

        val aircraftInfo = aircraftInfoPersister[id]
        aircraftInfo.shouldNotBeNull()

        aircraftInfo.registrationNumber shouldBe registrationNumber
        aircraftInfo.model shouldBe model
        aircraftInfo.seatCount shouldBe seatCount
    }
}

object TestAircraftIdGenerator : AircraftIdGenerator {
    val id = aircraftId()
    override fun generate() = id
}