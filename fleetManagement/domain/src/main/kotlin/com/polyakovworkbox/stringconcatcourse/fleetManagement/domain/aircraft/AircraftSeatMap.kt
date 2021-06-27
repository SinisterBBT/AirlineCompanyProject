package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.ValueObject
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class AircraftSeatMap internal constructor(val seatMap: ArrayList<ArrayList<ArrayList<Char>>>) : ValueObject {

    companion object {
        fun from(seatMap: ArrayList<ArrayList<ArrayList<Char>>>): Either<WrongSeatMapLayout, AircraftSeatMap> =
            if (isSeatMapFilled(seatMap) && hasOnlyValidSymbols(seatMap)) {
                AircraftSeatMap(seatMap).right()
            } else {
                WrongSeatMapLayout.left()
            }

        private fun isSeatMapFilled(seatMap: ArrayList<ArrayList<ArrayList<Char>>>) =
            seatMap.isNotEmpty() && seatMap[0].isNotEmpty() && seatMap[0][0].isNotEmpty()

        private fun hasOnlyValidSymbols(seatMap: ArrayList<ArrayList<ArrayList<Char>>>): Boolean {
            for (floor in seatMap) {
                for (column in floor) {
                    for (seat in column) {
                        return if (seat != 'S' && seat != 'X') false else continue
                    }
                }
            }
            return true
        }
    }
}

object WrongSeatMapLayout : BusinessError