package com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.polyakovworkbox.stringconcatcourse.common.types.base.AggregateRoot
import com.polyakovworkbox.stringconcatcourse.common.types.base.Version
import com.polyakovworkbox.stringconcatcourse.common.types.error.BusinessError

class Aircraft internal constructor(
    id: AircraftId,
    val model: AircraftModel,
    val payloadCapacity: AircraftPayloadCapacity,
    val registrationNumber: AircraftRegistrationNumber,
    val seatMap: AircraftSeatMap,
    val contractNumber: AircraftContractNumber,
    val manufactureDate: AircraftManufactureDate,
    version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun addAircraftToTheFleet(
            idGenerator: AircraftIdGenerator,
            aircraftAlreadyExists: AircraftAlreadyExistsChecker,
            model: AircraftModel,
            payloadCapacity: AircraftPayloadCapacity,
            registrationNumber: AircraftRegistrationNumber,
            seatMap: AircraftSeatMap,
            contractNumber: AircraftContractNumber,
            manufactureDate: AircraftManufactureDate
        ): Either<AlreadyExistsWithSameRegistrationNumberError, Aircraft> {
            return if (aircraftAlreadyExists.check(registrationNumber)) {
                AlreadyExistsWithSameRegistrationNumberError.left()
            } else {
                Aircraft(
                    idGenerator.generate(),
                    model,
                    payloadCapacity,
                    registrationNumber,
                    seatMap,
                    contractNumber,
                    manufactureDate,
                    Version.new()
                ).apply {
                    addEvent(AircraftAddedToTheFleetDomainEvent(this.id))
                }.right()
            }
        }
    }
}

object AlreadyExistsWithSameRegistrationNumberError : BusinessError