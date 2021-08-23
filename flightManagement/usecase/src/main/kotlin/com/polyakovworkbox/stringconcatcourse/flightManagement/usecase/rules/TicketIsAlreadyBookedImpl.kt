package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.rules

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.TicketIsAlreadyBooked
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order.OrderExtractor

class TicketIsAlreadyBookedImpl(
    private val orderExtractor: OrderExtractor
) : TicketIsAlreadyBooked {

    override fun check(ticketId: TicketId): Boolean =
        orderExtractor.getByTickedId(ticketId) != null
}