package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.orderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
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

    @ParameterizedTest
    @EnumSource(names = ["WAITING_FOR_PAYMENT", "CONFIRMED", "PAID"])
    fun `active - true`(state: OrderState) {
        val order = order(state = state)

        order.isActive() shouldBe true
    }

    @ParameterizedTest
    @EnumSource(names = ["COMPLETED", "CANCELLED"])
    fun `active - false`(state: OrderState) {
        val order = order(state = state)

        order.isActive() shouldBe false
    }

    @Test
    fun `complete order - success`() {
        val order = order(state = OrderState.CONFIRMED)

        order.let {
            it.complete() shouldBeRight Unit
            it.state shouldBe OrderState.COMPLETED
            it.popEvents() shouldContainExactlyInAnyOrder listOf(OrderCompletedDomainEvent(order.id))
        }
    }

    @Test
    fun `complete order - already`() {
        val order = order(state = OrderState.COMPLETED)

        order.let {
            it.complete() shouldBeRight Unit
            it.state shouldBe OrderState.COMPLETED
            it.popEvents().shouldBeEmpty()
        }
    }

    @ParameterizedTest
    @EnumSource(names = ["WAITING_FOR_PAYMENT", "PAID", "CANCELLED"])
    fun `complete order - invalid state`(state: OrderState) {
        val order = order(state = state)

        order.let {
            it.complete() shouldBeLeft InvalidState
            it.state shouldBe state
            it.popEvents().shouldBeEmpty()
        }
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

    @ParameterizedTest
    @EnumSource(names = ["CONFIRMED", "COMPLETED", "CANCELLED"])
    fun `pay - invalid state`(state: OrderState) {
        val order = order(state = state)

        order.let {
            it.pay() shouldBeLeft InvalidState
            it.state shouldBe state
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `cancel order - success`() {
        val order = order(state = OrderState.PAID)

        order.let {
            it.cancel() shouldBeRight Unit
            it.state shouldBe OrderState.CANCELLED
            it.popEvents() shouldContainExactlyInAnyOrder listOf(OrderCancelledDomainEvent(order.id))
        }
    }

    @Test
    fun `cancel order - already`() {
        val order = order(state = OrderState.CANCELLED)

        order.let {
            it.cancel() shouldBeRight Unit
            it.state shouldBe OrderState.CANCELLED
            it.popEvents().shouldBeEmpty()
        }
    }

    @ParameterizedTest
    @EnumSource(names = ["CONFIRMED", "COMPLETED", "WAITING_FOR_PAYMENT"])
    fun `cancel - invalid state`(state: OrderState) {
        val order = order(state = state)

        order.let {
            it.cancel() shouldBeLeft InvalidState
            it.state shouldBe state
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `confirm order - success`() {
        val order = order(state = OrderState.PAID)

        order.let {
            it.confirm() shouldBeRight Unit
            it.state shouldBe OrderState.CONFIRMED
            it.popEvents() shouldContainExactlyInAnyOrder listOf(OrderConfirmedDomainEvent(order.id))
        }
    }

    @Test
    fun `confirm order - already`() {
        val order = order(state = OrderState.CONFIRMED)

        order.let {
            it.confirm() shouldBeRight Unit
            it.state shouldBe OrderState.CONFIRMED
            it.popEvents().shouldBeEmpty()
        }
    }

    @ParameterizedTest
    @EnumSource(names = ["CANCELLED", "COMPLETED", "WAITING_FOR_PAYMENT"])
    fun `confirm - invalid state`(state: OrderState) {
        val order = order(state = state)

        order.let {
            it.confirm() shouldBeLeft InvalidState
            it.state shouldBe state
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `calculate total`() {
        val orderItem1 = orderItem(passenger(), ticket(price = BigDecimal("1.03")))
        val orderItem2 = orderItem(passenger(), ticket(price = BigDecimal("91.33")))

        val order = order(orderItems = listOf(orderItem1, orderItem2))

        order.totalPrice().price shouldBeEqualComparingTo BigDecimal("92.36")
    }
}