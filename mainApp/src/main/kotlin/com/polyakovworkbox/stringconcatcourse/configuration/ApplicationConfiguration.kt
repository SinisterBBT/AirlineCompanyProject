package com.polyakovworkbox.stringconcatcourse.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
        LeasingConfiguration::class,
        FlightManagementConfiguration::class
)
class ApplicationConfiguration