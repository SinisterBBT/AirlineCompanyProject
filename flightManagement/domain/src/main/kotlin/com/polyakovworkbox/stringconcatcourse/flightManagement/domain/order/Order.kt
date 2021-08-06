package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price

class Order internal constructor(
    id: OrderId,
    val email: Email,
    val orderItems: List<OrderItem>,
    version: Version
) : AggregateRoot<OrderId>(id, version) {

    var state: OrderState = OrderState.WAITING_FOR_PAYMENT
        internal set

    companion object {
        fun createOrder(
            idGenerator: OrderIdGenerator,
            ticketIsAlreadyBooked: TicketIsAlreadyBooked,
            email: Email,
            orderItems: List<OrderItem>
        ): Either<CannotCreateOrderError, Order> {
            return when {
                orderItems.isEmpty() ->
                    CannotCreateOrderError.OrderIsEmptyError.left()
                orderItems.any { ticketIsAlreadyBooked.check(it.ticketId) } ->
                    CannotCreateOrderError.TicketIsAlreadyBookedError.left()
                else -> {
                    Order(
                        idGenerator.generate(),
                        email,
                        orderItems,
                        Version.new()
                    ).apply {
                        addEvent(OrderCreatedDomainEvent(this.id))
                    }.right()
                }
            }
        }
    }

    fun totalPrice(): Price {
        return orderItems
            .map { it.price }
            .fold(Price.zero()) { total, it -> total.add(it) }
    }

    fun pay() = changeState(OrderState.PAID, OrderPaidDomainEvent(id))

    private fun changeState(newState: OrderState, event: DomainEvent): Either<InvalidState, Unit> {
        return when {
            state == newState -> Unit.right()
            state.canChangeTo(newState) -> {
                state = newState
                addEvent(event)
                Unit.right()
            }
            else -> InvalidState.left()
        }
    }
}

sealed class CannotCreateOrderError : BusinessError {
    object OrderIsEmptyError : CannotCreateOrderError()
    object TicketIsAlreadyBookedError : CannotCreateOrderError()
}

object InvalidState : BusinessError

enum class OrderState(
    private val nextStates: Set<OrderState> = emptySet()
) {
    PAID,
    WAITING_FOR_PAYMENT(nextStates = setOf(PAID));

    fun canChangeTo(state: OrderState) = nextStates.contains(state)
}