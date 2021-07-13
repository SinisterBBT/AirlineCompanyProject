package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.Duration

internal class FlightTimeTest {

    @Test
    fun `created flight time - success`() {
        val flightTime = Duration.ofHours(5)

        val result = FlightTime.from(flightTime)

        result shouldBeRight {
            it.flightTime shouldBe flightTime
        }
    }

    @Test
    fun `created flight time - negative`() {
        val flightTime = Duration.ofHours(-5)

        val result = FlightTime.from(flightTime)

        result shouldBeLeft FlightTimeNegativeError
    }
}