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
    val `onion architecture should be followed for leasing` =
        Architectures.onionArchitecture()
            .domainModels("com.polyakovworkbox.stringconcatcourse.leasing.domain..")
            .domainServices("com.polyakovworkbox.stringconcatcourse.leasing.domain..")
            .applicationServices("com.polyakovworkbox.stringconcatcourse.leasing.usecase..")

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for flightManagement` =
            Architectures.onionArchitecture()
                    .domainModels("com.polyakovworkbox.stringconcatcourse.flightManagement.domain..")
                    .domainServices("com.polyakovworkbox.stringconcatcourse.flightManagement.domain..")

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for maintenance` =
            Architectures.onionArchitecture()
                    .domainModels("com.polyakovworkbox.stringconcatcourse.maintenance.domain..")
                    .domainServices("com.polyakovworkbox.stringconcatcourse.maintenance.domain..")

    @ArchTest
    val `leasing business logic should depend only on approved packages` =
        ArchRuleDefinition.classes().that()
            .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.leasing.domain..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain..",
            "com.polyakovworkbox.stringconcatcourse.common..",
            "kotlin..",
            "kotlin.collections..",
            "java..",
            "org.jetbrains.annotations..",
            "arrow.core.."
        )

    @ArchTest
    val `flightManagement business logic should depend only on approved packages` =
            ArchRuleDefinition.classes().that()
                    .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.flightManagement.domain..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            "com.polyakovworkbox.stringconcatcourse.flightManagement.domain..",
                            "com.polyakovworkbox.stringconcatcourse.common..",
                            "kotlin..",
                            "kotlin.collections..",
                            "java..",
                            "org.jetbrains.annotations..",
                            "arrow.core.."
                    )

    @ArchTest
    val `maintenance business logic should depend only on approved packages` =
            ArchRuleDefinition.classes().that()
                    .resideInAnyPackage("com.polyakovworkbox.stringconcatcourse.maintenance.domain..")
                    .should().onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            "com.polyakovworkbox.stringconcatcourse.maintenance.domain..",
                            "com.polyakovworkbox.stringconcatcourse.common..",
                            "kotlin..",
                            "kotlin.collections..",
                            "java..",
                            "org.jetbrains.annotations..",
                            "arrow.core.."
                    )
}