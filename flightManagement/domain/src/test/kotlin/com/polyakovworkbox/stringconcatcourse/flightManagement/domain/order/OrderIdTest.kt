package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class OrderIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val orderId1 = OrderId(id)
        val orderId2 = OrderId(id)
        orderId1 shouldBe orderId2
        orderId1 shouldNotBeSameInstanceAs orderId2
        orderId1.value shouldBe orderId2.value
    }
}