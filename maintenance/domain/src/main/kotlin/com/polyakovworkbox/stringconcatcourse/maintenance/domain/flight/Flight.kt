package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

class Flight internal constructor(
    id: FlightId,
    val departureAirport: DepartureAirport,
    val actualArrivalAirport: ActualArrivalAirport,
    val flightTime: FlightTime,
    version: Version
) : AggregateRoot<FlightId>(id, version) {

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
}