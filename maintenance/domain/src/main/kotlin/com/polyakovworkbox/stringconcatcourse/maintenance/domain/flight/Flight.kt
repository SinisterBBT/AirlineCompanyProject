package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class Flight internal constructor(
    id: FlightId,
    val departureAirport: DepartureAirport,
    val actualArrivalAirport: ActualArrivalAirport,
    val flightTime: FlightTime,
    version: Version
) : AggregateRoot<FlightId>(id, version) {

    var state: FlightState = FlightState.REGISTERED
        internal set

    companion object {
        fun registerFlight(
            idGenerator: FlightIdGenerator,
            departureAirport: DepartureAirport,
            actualArrivalAirport: ActualArrivalAirport,
            flightTime: FlightTime,
        ): Flight {
            return Flight(
                    idGenerator.generate(),
                    departureAirport,
                    actualArrivalAirport,
                    flightTime,
                    Version.new()
                ).apply {
                    addEvent(FlightRegisteredDomainEvent(this.id))
                }
        }
    }

    fun isActive(): Boolean = state.active

    fun complete() = changeState(FlightState.COMPLETED, FlightCompletedDomainEvent(id))

    private fun changeState(newState: FlightState, event: DomainEvent): Either<InvalidState, Unit> {
        return when {
            state == newState -> Unit.right()
            state.canChangeTo(newState) -> {
                state = newState
                addEvent(event)
                Unit.right()
            }
            else -> InvalidState.left()
        }
    }
}

enum class FlightState(
    val active: Boolean,
    private val nextStates: Set<FlightState> = emptySet()
) {
    COMPLETED(active = false),
    REGISTERED(active = true, nextStates = setOf(COMPLETED));

    fun canChangeTo(state: FlightState) = nextStates.contains(state)
}

object InvalidState : BusinessError

data class FlightCompletedDomainEvent(val flightId: FlightId) : DomainEvent()