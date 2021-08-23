package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.CannotCreateOrderError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.TicketIsAlreadyBooked

class CreateOrderUseCase(
    private val orderPersister: OrderPersister,
    private val idGenerator: OrderIdGenerator,
    private val ticketIsAlreadyBooked: TicketIsAlreadyBooked
) : CreateOrder {

    override fun execute(request: CreateOrderRequest): Either<CreateOrderCaseError, Order> =
        Order.createOrder(
            idGenerator,
            ticketIsAlreadyBooked,
            request.email,
            request.orderItems
        ).mapLeft { e -> e.toError() }.map {
            order -> orderPersister.save(order)
            order
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