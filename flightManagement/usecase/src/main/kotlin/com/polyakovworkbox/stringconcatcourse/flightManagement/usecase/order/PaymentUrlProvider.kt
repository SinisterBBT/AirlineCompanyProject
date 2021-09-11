package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import java.net.URL

interface PaymentUrlProvider {
    fun provideUrl(orderId: OrderId, price: Price): URL
}