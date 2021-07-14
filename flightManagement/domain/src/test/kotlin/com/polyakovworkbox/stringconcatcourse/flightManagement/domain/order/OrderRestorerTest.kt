package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderRestorer
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderState
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.version
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class OrderRestorerTest {

    @Test
    fun `restore order - success`() {
        val orderId = orderId()
        val email = email()
        val orderItems = listOf(orderItem(), orderItem(), orderItem())
        val state = OrderState.COMPLETED
        val version = version()

        val result = OrderRestorer.restoreOrder(
            orderId,
            email,
            orderItems,
            state,
            version
        )

        result.let {
            it.id shouldBe orderId
            it.email shouldBe email
            it.orderItems shouldContainExactlyInAnyOrder orderItems
        }
    }
}