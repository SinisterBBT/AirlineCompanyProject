package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.math.BigDecimal

data class Price(
    val price: BigDecimal
) : ValueObject {
    companion object {

        private const val SCALE = 2

        fun zero() = Price(BigDecimal.ZERO.setScale(SCALE))

        fun from(price: BigDecimal): Either<TicketPriceError, Price> {
            return when {
                price.scale() > SCALE -> TicketPriceError.InvalidScaleTicketPriceError.left()
                price < BigDecimal.ZERO -> TicketPriceError.NegativeTicketPriceError.left()
                else -> Price(price.setScale(SCALE)).right()
            }
        }
    }

    fun add(additional: Price): Price =
            Price(additional.price.add(this.price))
}

sealed class TicketPriceError : BusinessError {
    object InvalidScaleTicketPriceError : TicketPriceError()
    object NegativeTicketPriceError : TicketPriceError()
}