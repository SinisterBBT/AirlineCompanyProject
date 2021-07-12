package com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class AircraftAcquiredDomainEvent(val aircraftId: AircraftId) : DomainEvent()