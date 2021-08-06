package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftContractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftManufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.domain.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftRegistrationNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.seatMap
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftTest {

    val aircraftId = aircraftId()

    private val idGenerator = object : AircraftIdGenerator {
        override fun generate() = aircraftId
    }

    private object AircraftExist : AircraftAlreadyExists {
        override fun check(registrationNumber: AircraftRegistrationNumber) = true
    }

    private object AircraftDoesNotExist : AircraftAlreadyExists {
        override fun check(registrationNumber: AircraftRegistrationNumber) = false
    }

    @Test
    fun `add aircraft to the fleet - success`() {
        val aircraftAlreadyExists = AircraftDoesNotExist
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = Aircraft.acquireNewAircraft(
            idGenerator,
            aircraftAlreadyExists,
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate
        )

        result shouldBeRight {
            it.id shouldBe aircraftId
            it.model shouldBe model
            it.payloadCapacity shouldBe payloadCapacity
            it.registrationNumber shouldBe registrationNumber
            it.seatMap shouldBe seatMap
            it.contractNumber shouldBe contractNumber
            it.manufactureDate shouldBe manufactureDate
        }
    }

    @Test
    fun `add aircraft to the fleet - with the exiting registration number`() {
        val aircraftAlreadyExists = AircraftExist
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = aircraftRegistrationNumber()
        val seatMap = seatMap()
        val contractNumber = aircraftContractNumber()
        val manufactureDate = aircraftManufactureDate()

        val result = Aircraft.acquireNewAircraft(
            idGenerator,
            aircraftAlreadyExists,
            model,
            payloadCapacity,
            registrationNumber,
            seatMap,
            contractNumber,
            manufactureDate
        )

        result shouldBeLeft AlreadyExistsWithSameRegistrationNumberError
    }
}