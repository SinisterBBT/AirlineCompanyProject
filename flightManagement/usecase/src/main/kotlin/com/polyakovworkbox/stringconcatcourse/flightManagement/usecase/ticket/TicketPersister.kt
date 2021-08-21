package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket

interface TicketPersister {
    fun save(ticket: Ticket)
}
