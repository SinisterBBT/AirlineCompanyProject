package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class TicketPublishedDomainEvent(val ticketId: TicketId) : DomainEvent()