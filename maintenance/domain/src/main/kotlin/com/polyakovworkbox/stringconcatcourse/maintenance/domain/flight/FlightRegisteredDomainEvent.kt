package com.polyakovworkbox.stringconcatcourse.maintenance.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class FlightRegisteredDomainEvent(val flightId: FlightId) : DomainEvent()