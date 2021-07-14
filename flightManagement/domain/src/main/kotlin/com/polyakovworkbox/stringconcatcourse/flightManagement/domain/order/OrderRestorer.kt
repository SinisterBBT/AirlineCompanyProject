package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.Version

object OrderRestorer {

    fun restoreOrder(
        id: OrderId,
        email: Email,
        orderItems: List<OrderItem>,
        version: Version
    ): Order {
        return Order(
            id,
            email,
            orderItems,
            version
        )
    }
}