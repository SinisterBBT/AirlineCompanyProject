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
    val `onion architecture should be followed for fleetManagement` =
        Architectures.onionArchitecture()
            .domainModels("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..")
            .domainServices("com.polyakovworkbox.stringconcatcourse.fleetManagement.domain..")

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
}