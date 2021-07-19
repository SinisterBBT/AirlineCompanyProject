package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.jupiter.api.Test
import kotlin.random.Random

class TicketIdTest {

    @Test
    fun `check equality`() {
        val id = Random.nextLong()

        val ticketId1 = TicketId(id)
        val ticketId2 = TicketId(id)
        ticketId1 shouldBe ticketId2
        ticketId1 shouldNotBeSameInstanceAs ticketId2
        ticketId1.value shouldBe ticketId2.value
    }
}