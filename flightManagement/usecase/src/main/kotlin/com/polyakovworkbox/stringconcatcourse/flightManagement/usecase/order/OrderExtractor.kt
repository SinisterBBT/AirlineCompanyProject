package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId

interface OrderExtractor {

    fun getByOrderId(orderId: OrderId): Order?

    fun getByTickedId(ticketId: TicketId): Order?
}
