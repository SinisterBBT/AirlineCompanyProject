package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class FlightIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val flightId1 = FlightId(id)
        val flightId2 = FlightId(id)
        flightId1 shouldBe flightId2
        flightId1 shouldNotBeSameInstanceAs flightId2
        flightId1.value shouldBe flightId2.value
    }
}