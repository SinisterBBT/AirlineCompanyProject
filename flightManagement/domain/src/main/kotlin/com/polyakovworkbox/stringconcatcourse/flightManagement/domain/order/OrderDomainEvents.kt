package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class OrderCreatedDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderCompletedDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderConfirmedDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderCancelledDomainEvent(val orderId: OrderId) : DomainEvent()
data class OrderPaidDomainEvent(val orderId: OrderId) : DomainEvent()