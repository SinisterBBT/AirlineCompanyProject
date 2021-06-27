package com.stringconcat.ddd.fitnes

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchIgnore
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures

@AnalyzeClasses(packages = ["com.polyakovworkbox.stringconcatcourse"])
class CleanArchitectureGuard {

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for aircraftCheckManagement` =
        Architectures.onionArchitecture()
            .domainModels("com.polyakovworkbox.stringconcatcourse.aircraftCheckManagement.domain..")
            .domainServices("com.polyakovworkbox.stringconcatcourse.aircraftCheckManagement.domain..")
/*            .applicationServices("com.polyakovworkbox.stringconcatcourse.aircraftCheckManagement.usecases..")*/

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for fleetManagement` =
        Architectures.onionArchitecture()
            .domainModels("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..")
            .domainServices("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..")
/*            .applicationServices("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain.usecases..")*/

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for flightsAndSeatsManagement` =
        Architectures.onionArchitecture()
            .domainModels("com.polyakovworkbox.stringconcatcourse.flightsAndSeatsManagement.domain..")
            .domainServices("com.polyakovworkbox.stringconcatcourse.flightsAndSeatsManagement.domain..")
    /*            .applicationServices("com.polyakovworkbox.stringconcatcourse.flightsAndSeatsManagement.domain..")*/

    @ArchTest
    val `aircraftCheckManagement business logic should depends only on approved packages` =
        ArchRuleDefinition.classes().that()
            .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.aircraftCheckManagement.domain..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage(
            "com.polyakovworkbox.stringconcatcourse.aircraftCheckManagement.domain..",
            "com.polyakovworkbox.stringconcatcourse.common..",
            "kotlin..",
            "java..",
            "org.jetbrains.annotations..",
            "arrow.core.."
        )

    @ArchTest
    val `fleetManagement business logic should depends only on approved packages` =
        ArchRuleDefinition.classes().that()
            .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage(
            "com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..",
            "com.polyakovworkbox.stringconcatcourse.common..",
            "kotlin..",
            "kotlin.collections..",
            "java..",
            "org.jetbrains.annotations..",
            "arrow.core.."
        )

    @ArchTest
    val `flightsAndSeatsManagement business logic should depends only on approved packages` =
        ArchRuleDefinition.classes().that()
            .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.flightsAndSeatsManagement.domain..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage(
            "com.polyakovworkbox.stringconcatcourse.flightsAndSeatsManagement.domain..",
            "com.polyakovworkbox.stringconcatcourse.common..",
            "kotlin..",
            "java..",
            "org.jetbrains.annotations..",
            "arrow.core.."
        )
}