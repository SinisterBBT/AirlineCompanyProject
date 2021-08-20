package com.polyakovworkbox.stringconcatcourse.leasing.usecase.com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftAlreadyExists
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.TestAircraftPersister
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft.AcquireNewAircraftCaseError
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft.AcquireNewAircraftRequest
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft.AcquireNewAircraftUseCase
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.seatMap
import com.polyakovworkbox.stringconcatcourse.leasing.usecase.seatMapLayout
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AcquireNewAircraftUseCaseTest {

    @Test
    fun `successfully added`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap(seatMapLayout())
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val aircraftPersister = TestAircraftPersister()
        val id = TestAircraftIdGenerator.id

        val result = AcquireNewAircraftUseCase(
            aircraftPersister,
            TestAircraftIdGenerator,
            AircraftNotExist
        ).execute(
            AcquireNewAircraftRequest(
                model,
                payloadCapacity,
                registrationNumber,
                seatMap,
                contractNumber,
                manufactureDate
            )
        )

        result shouldBeRight {
            it shouldBe id
        }

        val aircraft = aircraftPersister[id]
        aircraft.shouldNotBeNull()

        aircraft.id shouldBe id
        aircraft.model shouldBe model
        aircraft.payloadCapacity shouldBe payloadCapacity
        aircraft.seatMap shouldBe seatMap
        aircraft.contractNumber shouldBe contractNumber
        aircraft.manufactureDate shouldBe manufactureDate
    }

    @Test
    fun `aircraft already exists`() {
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap(seatMapLayout())
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val aircraftPersister = TestAircraftPersister()

        val result = AcquireNewAircraftUseCase(
            aircraftPersister,
            TestAircraftIdGenerator,
            AircraftExist
        ).execute(
            AcquireNewAircraftRequest(
                model,
                payloadCapacity,
                registrationNumber,
                seatMap,
                contractNumber,
                manufactureDate
            )
        )

        result shouldBeLeft AcquireNewAircraftCaseError.AlreadyExists
        aircraftPersister.shouldBeEmpty()
    }
}

object TestAircraftIdGenerator : AircraftIdGenerator {
    val id = aircraftId()
    override fun generate() = id
}

object AircraftExist : AircraftAlreadyExists {
    override fun check(registrationNumber: AircraftRegistrationNumber) = true
}

object AircraftNotExist : AircraftAlreadyExists {
    override fun check(registrationNumber: AircraftRegistrationNumber) = false
}