package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.TestAircraftPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.seatCount
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ReceiveAircraftUseCaseTest {

    @Test
    fun `successfully received`() {
        val registrationNumber = aircraftRegistrationNumber()
        val model = aircraftModel()
        val seatCount = seatCount()

        val aircraftPersister = TestAircraftPersister()
        val id = TestAircraftIdGenerator.id

        val result = ReceiveAircraftUseCase(
            aircraftPersister,
            TestAircraftIdGenerator
        ).execute(
            ReceiveAircraftRequest(
                registrationNumber,
                model,
                seatCount
            )
        )

        result.value shouldBe id.value

        val aircraft = aircraftPersister[id]
        aircraft.shouldNotBeNull()

        aircraft.registrationNumber shouldBe registrationNumber
        aircraft.model shouldBe model
        aircraft.seatCount shouldBe seatCount
    }
}

object TestAircraftIdGenerator : AircraftIdGenerator {
    val id = aircraftId()
    override fun generate() = id
}