package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.CannotCreateOrderError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.TicketIsAlreadyBooked
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price

class CreateOrderUseCase(
    private val orderPersister: OrderPersister,
    private val idGenerator: OrderIdGenerator,
    private val ticketIsAlreadyBooked: TicketIsAlreadyBooked,
    private val paymentUrlProvider: PaymentUrlProvider
) : CreateOrder {

    override fun execute(request: CreateOrderRequest): Either<CreateOrderCaseError, PaymentInfo> =
        Order.createOrder(
            idGenerator,
            ticketIsAlreadyBooked,
            request.email,
            request.orderItems
        ).mapLeft { e -> e.toError() }.map {
            order -> orderPersister.save(order)
            val overallPrice = Price(order.orderItems.sumOf { it.price.price })
            PaymentInfo(
                order.id,
                overallPrice,
                paymentUrlProvider.provideUrl(order.id, overallPrice)
            )
        }
}

fun CannotCreateOrderError.toError(): CreateOrderCaseError {
    return when (this) {
        CannotCreateOrderError.OrderIsEmptyError ->
            CreateOrderCaseError.OrderIsEmptyError
        CannotCreateOrderError.TicketIsAlreadyBookedError ->
            CreateOrderCaseError.TicketIsAlreadyBookedError
    }
}