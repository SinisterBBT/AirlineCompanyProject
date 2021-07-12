package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class FlightAnnouncedDomainEvent(val aircraftId: FlightId) : DomainEvent()