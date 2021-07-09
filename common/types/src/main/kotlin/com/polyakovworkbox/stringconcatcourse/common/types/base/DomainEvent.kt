package com.polyakovworkbox.stringconcatcourse.common.types.base

import java.time.OffsetDateTime
import java.util.UUID

open class DomainEvent protected constructor() {
    val id = EventId.generate()
}

data class EventId internal constructor(val value: UUID, val created: OffsetDateTime = OffsetDateTime.now()) {
    companion object {
        fun generate(): EventId = EventId(UUID.randomUUID())
    }
}

interface EventPublisher {
    fun publish(events: Collection<DomainEvent>)
}