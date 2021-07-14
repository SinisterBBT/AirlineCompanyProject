package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class OrderItemTest {

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