package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

data class TicketId(val value: Long)

interface TicketIdGenerator {
    fun generate(): TicketId
}