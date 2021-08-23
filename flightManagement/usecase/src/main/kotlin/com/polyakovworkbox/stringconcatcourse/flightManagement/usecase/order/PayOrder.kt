package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId

interface PayOrder {
    fun execute(orderId: OrderId): Either<PayOrderCaseError, Order>
}

sealed class PayOrderCaseError(open val message: String) {
    object OrderNotFound : PayOrderCaseError("Cannot find order with given id to pay")
}