package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.maintenance.domain.actualArrivalAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.departureAirport
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightTime
import com.polyakovworkbox.stringconcatcourse.maintenance.domain.flightId
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class FlightTest {

    val flightId = flightId()

    private val idGenerator = object : FlightIdGenerator {
        override fun generate() = flightId
    }

    @Test
    fun `register flight - success`() {
        val departureAirport = departureAirport()
        val actualArrivalAirport = actualArrivalAirport()
        val flightTime = flightTime()

        val result = Flight.registerFlight(idGenerator, departureAirport, actualArrivalAirport, flightTime)

        result.let {
            it.departureAirport shouldBe departureAirport
            it.actualArrivalAirport shouldBe actualArrivalAirport
            it.flightTime shouldBe flightTime
        }
    }

    @ParameterizedTest
    @EnumSource(names = ["REGISTERED"])
    fun `active - true`(state: FlightState) {
        val order = flight(state = state)

        order.isActive() shouldBe true
    }

    @ParameterizedTest
    @EnumSource(names = ["COMPLETED"])
    fun `active - false`(state: FlightState) {
        val order = flight(state = state)

        order.isActive() shouldBe false
    }

    @Test
    fun `complete flight - success`() {
        val order = flight(state = FlightState.REGISTERED)

        order.let {
            it.complete() shouldBeRight Unit
            it.state shouldBe FlightState.COMPLETED
            it.popEvents() shouldContainExactlyInAnyOrder listOf(FlightCompletedDomainEvent(order.id))
        }
    }

    @Test
    fun `complete order - already`() {
        val order = flight(state = FlightState.COMPLETED)

        order.let {
            it.complete() shouldBeRight Unit
            it.state shouldBe FlightState.COMPLETED
            it.popEvents().shouldBeEmpty()
        }
    }
}