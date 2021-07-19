package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftModel
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.domain.contractNumber
import com.polyakovworkbox.stringconcatcourse.leasing.domain.manufactureDate
import com.polyakovworkbox.stringconcatcourse.leasing.domain.payloadCapacity
import com.polyakovworkbox.stringconcatcourse.leasing.domain.registrationNumber
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

    private object AircraftExist : AircraftAlreadyExistsChecker {
        override fun check(registrationNumber: AircraftRegistrationNumber) = true
    }

    private object AircraftDoesNotExist : AircraftAlreadyExistsChecker {
        override fun check(registrationNumber: AircraftRegistrationNumber) = false
    }

    @Test
    fun `add aircraft to the fleet - success`() {
        val aircraftAlreadyExistsChecker = AircraftDoesNotExist
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = registrationNumber()
        val seatMap = seatMap()
        val contractNumber = contractNumber()
        val manufactureDate = manufactureDate()

        val result = Aircraft.acquireNewAircraft(
            idGenerator,
            aircraftAlreadyExistsChecker,
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
        val aircraftAlreadyExistsChecker = AircraftExist
        val model = aircraftModel()
        val payloadCapacity = payloadCapacity()
        val registrationNumber = registrationNumber()
        val seatMap = seatMap()
        val contractNumber = contractNumber()
        val manufactureDate = manufactureDate()

        val result = Aircraft.acquireNewAircraft(
            idGenerator,
            aircraftAlreadyExistsChecker,
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