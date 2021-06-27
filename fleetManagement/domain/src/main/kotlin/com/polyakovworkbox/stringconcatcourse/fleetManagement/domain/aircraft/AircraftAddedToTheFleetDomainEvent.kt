package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class AircraftAddedToTheFleetDomainEvent(val aircraftId: AircraftId) : DomainEvent()