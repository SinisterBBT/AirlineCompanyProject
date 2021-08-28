package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import java.net.URL

interface CreateOrder {
    fun execute(request: CreateOrderRequest): Either<CreateOrderCaseError, PaymentInfo>
}

data class PaymentInfo(
    val orderId: OrderId,
    val overallPrice: Price,
    val paymentURL: URL
)

sealed class CreateOrderCaseError(open val message: String) {
    object OrderIsEmptyError : CreateOrderCaseError("Order cannot be empty")
    object TicketIsAlreadyBookedError : CreateOrderCaseError("One or more tickets in the order are already booked")
}