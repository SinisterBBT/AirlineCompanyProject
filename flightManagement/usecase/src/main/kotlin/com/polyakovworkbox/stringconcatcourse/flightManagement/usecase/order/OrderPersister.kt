package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order

interface OrderPersister {
    fun save(order: Order)
}
