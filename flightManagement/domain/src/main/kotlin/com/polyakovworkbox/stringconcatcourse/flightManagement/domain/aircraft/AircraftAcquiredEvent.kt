package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class AircraftAcquiredEvent(val aircraftId: AircraftId) : DomainEvent()