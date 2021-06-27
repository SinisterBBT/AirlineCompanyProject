package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class AircraftIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val aircraftId1 = AircraftId(id)
        val aircraftId2 = AircraftId(id)
        aircraftId1 shouldBe aircraftId2
        aircraftId1 shouldNotBeSameInstanceAs aircraftId2
        aircraftId1.value shouldBe aircraftId2.value
    }
}