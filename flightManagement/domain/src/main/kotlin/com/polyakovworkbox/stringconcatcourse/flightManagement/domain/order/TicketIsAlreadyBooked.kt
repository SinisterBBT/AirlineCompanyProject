package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId

interface TicketIsAlreadyBooked {
    fun check(ticketId: TicketId): Boolean
}