package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Ticket

class OrderItem internal constructor(val passenger: Passenger, val ticket: Ticket) : ValueObject {

    companion object {
        fun from(
            passenger: Passenger,
            ticket: Ticket
        ): OrderItem {
            return OrderItem(passenger, ticket)
        }
    }
}