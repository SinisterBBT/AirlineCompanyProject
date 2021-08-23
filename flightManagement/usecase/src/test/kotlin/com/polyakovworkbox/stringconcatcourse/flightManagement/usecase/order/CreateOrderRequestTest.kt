package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.email
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.fullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.getListOfPurchases
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.passportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.price
import com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CreateOrderRequestTest {

    @Test
    fun `is equal to another one with the same parameters`() {
        val email = email()
        val purchases = getListOfPurchases()

        val result = CreateOrderRequest.from(
            email.email,
            purchases
        )

        val result2 = CreateOrderRequest.from(
            email.email,
            purchases
        )

        (result == result2) shouldBe true
    }

    @Test
    fun `is created successfully`() {
        val email = email()
        val purchases = getListOfPurchases()

        val result = CreateOrderRequest.from(
            email.email,
            purchases
        )

        val compareToInstance = CreateOrderRequest(
            email,
            purchases.map {
                OrderItem.from(
                    Passenger.from(
                        fullName(it.fullBuyerName),
                        passportData(it.buyerPassportData)
                    ),
                    TicketId(it.ticketId),
                    Price(it.ticketPrice)
                )
            }
        )

        result shouldBeRight {
            it.email shouldBe compareToInstance.email
            it.orderItems shouldBe compareToInstance.orderItems
        }
    }

    @Test
    fun `email has wrong format`() {
        val email = ""
        val purchases = getListOfPurchases()

        val result = CreateOrderRequest.from(
            email,
            purchases
        )

        result shouldBeLeft InvalidOrderParameters("Email format is not correct")
    }

    @Test
    fun `full name of buyer has wrong format`() {
        val email = email()
        val purchases = getListOfPurchases().toMutableList().apply {
            this[5] = Purchase(
                "",
                passportData().passportData,
                ticketId().value,
                price().price
            )
        }.toList()

        val result = CreateOrderRequest.from(
            email.email,
            purchases
        )

        result shouldBeLeft InvalidOrderParameters("Name of the buyer cannot be empty")
    }

    @Test
    fun `passport data of buyer has wrong format`() {
        val email = email()
        val purchases = getListOfPurchases().toMutableList().apply {
            this[5] = Purchase(
                fullName().fio,
                "",
                ticketId().value,
                price().price
            )
        }.toList()

        val result = CreateOrderRequest.from(
            email.email,
            purchases
        )

        result shouldBeLeft InvalidOrderParameters("Passport data of buyer cannot be empty")
    }

    @Test
    fun `price has wrong format`() {
        val email = email()
        val purchases = getListOfPurchases().toMutableList().apply {
            this[5] = Purchase(
                fullName().fio,
                passportData().passportData,
                ticketId().value,
                BigDecimal(-5)
            )
        }.toList()

        val result = CreateOrderRequest.from(
            email.email,
            purchases
        )

        result shouldBeLeft InvalidOrderParameters("Price has invalid format")
    }
}