package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order

interface CreateOrder {
    fun execute(request: CreateOrderRequest): Either<CreateOrderCaseError, Order>
}

sealed class CreateOrderCaseError(open val message: String) {
    object OrderIsEmptyError : CreateOrderCaseError("Order cannot be empty")
    object TicketIsAlreadyBookedError : CreateOrderCaseError("One or more tickets in the order are already booked")
}