package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class Order internal constructor(
    id: OrderId,
    val email: Email,
    val orderItems: List<OrderItem>,
    version: Version
) : AggregateRoot<OrderId>(id, version) {

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
}

object OrderIsEmptyError : BusinessError