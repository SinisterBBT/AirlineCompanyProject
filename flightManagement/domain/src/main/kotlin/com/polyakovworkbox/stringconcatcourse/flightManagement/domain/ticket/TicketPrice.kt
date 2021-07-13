package com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError
import java.math.BigDecimal

class TicketPrice(
    val price: BigDecimal
) {
    companion object {
        fun from(price: BigDecimal): Either<NegativeTicketPriceError, TicketPrice> {
            return if (price.compareTo(BigDecimal.ZERO) == -1) {
                NegativeTicketPriceError.left()
            } else {
                TicketPrice(BigDecimal.ONE).right()
            }
        }
    }
}

object NegativeTicketPriceError : BusinessError