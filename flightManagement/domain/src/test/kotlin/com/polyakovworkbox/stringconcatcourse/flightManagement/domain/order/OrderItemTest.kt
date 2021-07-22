package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.fullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class OrderItemTest {

    @Test
    fun `OrderItem is equal to other OrderItem with the same value`() {
        val passenger = passenger(fullName("Ivanov Ivan Ivanovich"), passportData("1234 123456"))
        val ticket = ticket(BigDecimal.ONE)

        val firstValue = orderItem(passenger, ticket)
        val secondValue = orderItem(passenger, ticket)

        (firstValue == secondValue) shouldBe true
    }

    @Test
    fun `create order item - success`() {
        val passenger = passenger()
        val ticket = ticket()

        val result = OrderItem.from(passenger, ticket)

        result.let {
            it.passenger shouldBe passenger
            it.ticket shouldBe ticket
        }
    }
}