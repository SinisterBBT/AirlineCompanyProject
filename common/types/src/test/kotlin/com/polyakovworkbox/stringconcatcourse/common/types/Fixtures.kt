package com.polyakovworkbox.stringconcatcourse.common.types

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.common.Airport
import com.polyakovworkbox.stringconcatcourse.common.types.common.Count

fun count(value: Int): Count {
    val result = Count.from(value)
    check(result is Either.Right<Count>)
    return result.b
}

fun airport(departureAirport: String = "LED"): Airport {
    val result = Airport.from(departureAirport)
    check(result is Either.Right<Airport>)
    return result.b
}