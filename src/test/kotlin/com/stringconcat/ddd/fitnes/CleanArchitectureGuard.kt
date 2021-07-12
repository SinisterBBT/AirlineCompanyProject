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

    @ArchIgnore
    @ArchTest
    val `onion architecture should be followed for flightManagement` =
            Architectures.onionArchitecture()
                    .domainModels("com.polyakovworkbox.stringconcatcourse.flightManagement.domain..")
                    .domainServices("com.polyakovworkbox.stringconcatcourse.flightManagement.domain..")

    @ArchTest
    val `leasing business logic should depends only on approved packages` =
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
    val `flightManagement business logic should depends only on approved packages` =
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
}