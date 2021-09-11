package com.polyakovworkbox.stringconcatcourse.flightManagement.usecase.order

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Email
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.EmailIsNotValidError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.EmptyFioError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.EmptyPassportDataError
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.FullName
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.OrderItem
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.Passenger
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.order.PassportData
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.Price
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.ticket.TicketId
import java.math.BigDecimal

data class CreateOrderRequest internal constructor(
    val email: Email,
    val orderItems: List<OrderItem>
) {

    companion object {
        fun from(
            email: String,
            purchases: List<Purchase>
        ): Either<InvalidOrderParameters, CreateOrderRequest> {
            val orderItems = purchases.map {
                    orderItemsParams -> OrderItem.from(
                        Passenger.from(
                            FullName.from(orderItemsParams.fullBuyerName).getOrElse {
                                return@from EmptyFioError.toError().left()
                            },
                            PassportData.from(orderItemsParams.buyerPassportData).getOrElse {
                                return@from EmptyPassportDataError.toError().left()
                            }
                        ),
                        TicketId(orderItemsParams.ticketId),
                        Price.from(orderItemsParams.ticketPrice).getOrElse {
                            return@from InvalidOrderParameters("Price has invalid format").left()
                        }
                    )
                }

            return tupled(
                Email.from(email).mapLeft { it.toError() },
                orderItems.right()
            ).map {
                params -> CreateOrderRequest(params.a, params.b)
            }
        }
    }
}

data class Purchase(
    val fullBuyerName: String,
    val buyerPassportData: String,
    val ticketId: Long,
    val ticketPrice: BigDecimal
)

data class InvalidOrderParameters(val message: String)

fun EmailIsNotValidError.toError() = InvalidOrderParameters("Email format is not correct")
fun EmptyFioError.toError() = InvalidOrderParameters("Name of the buyer cannot be empty")
fun EmptyPassportDataError.toError() = InvalidOrderParameters("Passport data of buyer cannot be empty")