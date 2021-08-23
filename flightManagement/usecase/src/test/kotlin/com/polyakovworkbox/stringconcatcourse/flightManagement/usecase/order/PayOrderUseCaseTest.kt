package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Order
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderState
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.orderId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PayOrderUseCaseTest {

    @Test
    fun `successfully paid`() {
        val orderId = orderId()

        val result = PayOrderUseCase(
            TestOrderExtractor
        ).execute(orderId)

        result shouldBeRight {
            it.state shouldBe OrderState.PAID
        }
    }

    @Test
    fun `order not found`() {
        val orderId = orderId()

        val result = PayOrderUseCase(
            TestOrderNotFoundExtractor
        ).execute(orderId)

        result shouldBeLeft PayOrderCaseError.OrderNotFound
    }
}

object TestOrderExtractor : OrderExtractor {
    override fun getByTickedId(ticketId: TicketId): Order = throw UnsupportedOperationException()
    override fun getByOrderId(orderId: OrderId): Order = order()
}

object TestOrderNotFoundExtractor : OrderExtractor {
    override fun getByTickedId(ticketId: TicketId): Order = throw UnsupportedOperationException()
    override fun getByOrderId(orderId: OrderId): Order? = null
}