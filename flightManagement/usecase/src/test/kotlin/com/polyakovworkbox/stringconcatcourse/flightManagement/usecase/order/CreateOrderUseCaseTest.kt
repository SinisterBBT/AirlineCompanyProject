package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderIdGenerator
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.TicketIsAlreadyBooked
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.TestOrderPersister
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.email
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.orderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.orderItemsList
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.net.URL

internal class CreateOrderUseCaseTest {

    @Test
    fun `successfully created`() {
        val email = email()
        val orderItems = orderItemsList()
        val overallPrice = Price(orderItems.sumOf { it.price.price })

        val orderPersister = TestOrderPersister()
        val id = TestOrderIdGenerator.id
        val urlProvider = TestPaymentUrlProvider

        val result = CreateOrderUseCase(
            orderPersister,
            TestOrderIdGenerator,
            TicketIsNotBooked,
            TestPaymentUrlProvider
        ).execute(
            CreateOrderRequest(
                email,
                orderItems
            )
        )

        result shouldBeRight {
            it.orderId shouldBe id
            it.overallPrice shouldBe overallPrice
            it.paymentURL shouldBe urlProvider.paymentUrl
        }

        val order = orderPersister[id]
        order.shouldNotBeNull()
    }

    @Test
    fun `order is empty`() {
        val email = email()
        val orderItems = listOf<OrderItem>()

        val orderPersister = TestOrderPersister()

        val result = CreateOrderUseCase(
            orderPersister,
            TestOrderIdGenerator,
            TicketIsNotBooked,
            TestPaymentUrlProvider
        ).execute(
            CreateOrderRequest(
                email,
                orderItems
            )
        )

        result shouldBeLeft CreateOrderCaseError.OrderIsEmptyError
        orderPersister.shouldBeEmpty()
    }

    @Test
    fun `ticket is already booked`() {
        val email = email()
        val orderItems = orderItemsList()

        val orderPersister = TestOrderPersister()

        val result = CreateOrderUseCase(
            orderPersister,
            TestOrderIdGenerator,
            TicketIsAlreadyBooked,
            TestPaymentUrlProvider
        ).execute(
            CreateOrderRequest(
                email,
                orderItems
            )
        )

        result shouldBeLeft CreateOrderCaseError.TicketIsAlreadyBookedError
        orderPersister.shouldBeEmpty()
    }
}

object TestOrderIdGenerator : OrderIdGenerator {
    val id = orderId()
    override fun generate() = id
}

object TicketIsAlreadyBooked : TicketIsAlreadyBooked {
    override fun check(ticketId: TicketId) = true
}

object TicketIsNotBooked : TicketIsAlreadyBooked {
    override fun check(ticketId: TicketId) = false
}

object TestPaymentUrlProvider : PaymentUrlProvider {
    val paymentUrl = URL("http://localhost")
    override fun provideUrl(orderId: OrderId, price: Price) = paymentUrl
}