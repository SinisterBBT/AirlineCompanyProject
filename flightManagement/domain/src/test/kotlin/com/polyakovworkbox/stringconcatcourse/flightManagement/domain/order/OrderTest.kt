package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import email
import io.kotest.assertions.arrow.either.shouldBeLeft
import orderId
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import orderItem
import org.junit.jupiter.api.Test

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
}