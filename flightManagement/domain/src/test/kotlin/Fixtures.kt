import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftModel
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.AircraftSeatMap
import com.polyakovworkbox.stringconcatcourse.flightManagement.domain.aircraft.Seat
import kotlin.random.Random

// Seat map value object
fun correctSeatMapLayout(): ArrayList<Seat> {
    val result = ArrayList<Seat>()

     listOf("A1", "A2", "A3", "A4", "A5", "A6",
             "B1", "B2", "B3", "B4", "B5", "B6",
             "C1", "C2", "C3", "C4", "C5", "C6",
             "D1", "D2", "D3", "D4", "D5", "D6",
             "F1", "F2", "F3", "F4", "F5", "F6",
             "G1", "G2", "G3", "G4", "G5", "G6").forEach { seatName ->
         Seat.from(seatName).also {
             check(it is Either.Right<Seat>)
             result.add(it.b)
        }
     }

    return result
}

// Aircraft aggregate
fun aircraftId() = AircraftId(Random.nextLong())

fun aircraftModel(): AircraftModel {
    val result = AircraftModel.from("Model ${Random.nextInt()}")
    check(result is Either.Right<AircraftModel>)
    return result.b
}

fun seatMap(): AircraftSeatMap {
    val result = AircraftSeatMap.from(correctSeatMapLayout())
    check(result is Either.Right<AircraftSeatMap>)
    return result.b
}

// Aircraft restorer
fun version() = Version.new()