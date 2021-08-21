package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.ticket

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.flight.FlightId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketPriceError
import java.math.BigDecimal

data class PublishNewTicketRequest internal constructor(
    val flightId: FlightId,
    val price: Price
) {

    companion object {
        fun from(
            flightId: Long,
            price: BigDecimal
        ): Either<InvalidTicketParameters, PublishNewTicketRequest> {
            return tupled(
                FlightId(flightId).right(),
                Price.from(price).mapLeft { it.toError() }
            ).map {
                params -> PublishNewTicketRequest(params.a, params.b)
            }
        }
    }
}

data class InvalidTicketParameters(val message: String)

fun TicketPriceError.toError() = InvalidTicketParameters("Ticket price value has wrong format")