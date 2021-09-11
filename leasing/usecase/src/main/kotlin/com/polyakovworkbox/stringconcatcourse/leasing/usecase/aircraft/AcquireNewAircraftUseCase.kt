package com.polyakovworkbox.stringconcatcourse.leasing.usecase.aircraft

import arrow.core.Either
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.Aircraft
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftAlreadyExists
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftId
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AircraftIdGenerator
import com.polyakovworkbox.stringconcatcourse.leasing.domain.aircraft.AlreadyExistsWithSameRegistrationNumberError

class AcquireNewAircraftUseCase(
    private val aircraftPersister: AircraftPersister,
    private val idGenerator: AircraftIdGenerator,
    private val aircraftAlreadyExists: AircraftAlreadyExists
) : AcquireNewAircraft {

    override fun execute(request: AcquireNewAircraftRequest): Either<AcquireNewAircraftCaseError, AircraftId> =
        Aircraft.acquireNewAircraft(
            idGenerator = idGenerator,
            aircraftAlreadyExists = aircraftAlreadyExists,
            model = request.model,
            payloadCapacity = request.payloadCapacity,
            registrationNumber = request.registrationNumber,
            seatMap = request.seatMap,
            contractNumber = request.contractNumber,
            manufactureDate = request.manufactureDate
        ).mapLeft { e -> e.toError() }.map {
            aircraft -> aircraftPersister.save(aircraft)
            aircraft.id
        }
}

fun AlreadyExistsWithSameRegistrationNumberError.toError() = AcquireNewAircraftCaseError.AlreadyExists
