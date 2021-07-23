package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId

data class OrderItem internal constructor(
    val passenger: Passenger,
    val ticketId: TicketId,
    val price: Price
) : ValueObject {

    companion object {
        fun from(
            passenger: Passenger,
            ticketId: TicketId,
            price: Price
        ): OrderItem {
            return OrderItem(passenger, ticketId, price)
        }
    }
}