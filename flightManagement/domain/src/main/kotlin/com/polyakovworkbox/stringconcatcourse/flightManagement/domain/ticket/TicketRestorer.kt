package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId

object TicketRestorer {

    fun restoreTicket(
        id: TicketId,
        flightId: FlightId,
        price: Price,
        version: Version
    ): Ticket {
        return Ticket(
            id,
            flightId,
            price,
            version
        )
    }
}