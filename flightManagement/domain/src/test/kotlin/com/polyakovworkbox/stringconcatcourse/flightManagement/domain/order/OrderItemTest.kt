package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.fullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class OrderItemTest {

    @Test
    fun `is equal to other OrderItem with the same value`() {
        val passenger = passenger(fullName("Ivanov Ivan Ivanovich"), passportData("1234 123456"))
        val ticketId = ticketId(1234567890)
        val price = price(BigDecimal("12.49"))

        val firstValue = orderItem(passenger, ticketId, price)
        val secondValue = orderItem(passenger, ticketId, price)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create order item - success`() {
        val passenger = passenger()
        val ticketId = ticketId()
        val price = price()

        val result = OrderItem.from(passenger, ticketId, price)

        result.let {
            it.passenger shouldBe passenger
            it.ticketId shouldBe ticketId
        }
    }
}