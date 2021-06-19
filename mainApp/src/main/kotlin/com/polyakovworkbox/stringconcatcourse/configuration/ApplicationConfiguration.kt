package com.polyakovworkbox.stringconcatcourse.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
        FleetManagementConfiguration::class,
        FlightsAndSeatsManagementConfiguration::class,
        AircraftCheckManagementConfiguration::class
)
class ApplicationConfiguration