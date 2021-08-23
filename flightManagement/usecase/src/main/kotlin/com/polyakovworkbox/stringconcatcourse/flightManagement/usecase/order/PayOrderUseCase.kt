package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId

class PayOrderUseCase(
    private val orderExtractor: OrderExtractor
) : PayOrder {

    override fun execute(orderId: OrderId): Either<PayOrderCaseError, Order> {
        val order = orderExtractor.getByOrderId(orderId)
        return if (order != null) {
            order.pay()
            order.right()
        } else {
            PayOrderCaseError.OrderNotFound.left()
        }
    }
}