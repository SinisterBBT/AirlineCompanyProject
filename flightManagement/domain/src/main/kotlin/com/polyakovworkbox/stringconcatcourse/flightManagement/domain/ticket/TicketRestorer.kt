package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.Flight

object TicketRestorer {

    fun restoreTicket(
        id: TicketId,
        flight: Flight,
        price: TicketPrice,
        version: Version
    ): Ticket {
        return Ticket(
            id,
            flight,
            price,
            version
        )
    }
}