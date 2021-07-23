package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class OrderTest {

    val orderId = orderId()

    private val idGenerator = object : OrderIdGenerator {
        override fun generate() = orderId
    }

    @Test
    fun `create order - success`() {
        val email = email()
        val orderItems = listOf(orderItem(), orderItem(), orderItem())

        val result = Order.createOrder(idGenerator, email, orderItems)

        result shouldBeRight {
            it.email shouldBe email
            it.orderItems shouldContainExactlyInAnyOrder orderItems
        }
    }

    @Test
    fun `create order - without order items`() {
        val email = email()
        val orderItems = emptyList<OrderItem>()

        val result = Order.createOrder(idGenerator, email, orderItems)

        result shouldBeLeft OrderIsEmptyError
    }

    @Test
    fun `pay order - success`() {
        val order = order(state = OrderState.WAITING_FOR_PAYMENT)

        order.let {
            it.pay() shouldBeRight Unit
            it.state shouldBe OrderState.PAID
            it.popEvents() shouldContainExactlyInAnyOrder listOf(OrderPaidDomainEvent(order.id))
        }
    }

    @Test
    fun `pay order - already`() {
        val order = order(state = OrderState.PAID)

        order.let {
            it.pay() shouldBeRight Unit
            it.state shouldBe OrderState.PAID
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `calculate total`() {
        val orderItem1 = orderItem(passenger(), ticketId(), price(BigDecimal("1.03")))
        val orderItem2 = orderItem(passenger(), ticketId(), price(BigDecimal("91.33")))

        val order = order(orderItems = listOf(orderItem1, orderItem2))

        order.totalPrice().price shouldBeEqualComparingTo BigDecimal("92.36")
    }
}