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
            email: Email,
            orderItems: List<OrderItem>
        ): Either<OrderIsEmptyError, Order> {
            return if (orderItems.isNotEmpty()) {
                Order(
                    idGenerator.generate(),
                    email,
                    orderItems,
                    Version.new()
                ).apply {
                    addEvent(OrderCreatedDomainEvent(this.id))
                }.right()
            } else {
                OrderIsEmptyError.left()
            }
        }
    }

    fun totalPrice(): Price {
        return orderItems
                .map { it.ticket.price }
                .fold(Price.zero(), { total, it -> total.add(it) })
    }

    fun confirm() = changeState(OrderState.CONFIRMED, OrderConfirmedDomainEvent(id))

    fun pay() = changeState(OrderState.PAID, OrderPaidDomainEvent(id))

    fun complete() = changeState(OrderState.COMPLETED, OrderCompletedDomainEvent(id))

    fun cancel() = changeState(OrderState.CANCELLED, OrderCancelledDomainEvent(id))

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

    fun isActive(): Boolean = state.active
}

object OrderIsEmptyError : BusinessError

object InvalidState : BusinessError

enum class OrderState(
    val active: Boolean,
    private val nextStates: Set<OrderState> = emptySet()
) {

    CANCELLED(active = false),
    COMPLETED(active = false),
    CONFIRMED(active = true, nextStates = setOf(COMPLETED)),
    PAID(active = true, nextStates = setOf(CONFIRMED, CANCELLED)),
    WAITING_FOR_PAYMENT(active = true, nextStates = setOf(PAID));

    fun canChangeTo(state: OrderState) = nextStates.contains(state)
}