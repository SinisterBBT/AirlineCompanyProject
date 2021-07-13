package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.common.types.base.DomainEvent

data class OrderCreatedDomainEvent(val orderId: OrderId) : DomainEvent()