package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order

data class OrderId(val value: Long)

interface OrderIdGenerator {
    fun generate(): OrderId
}