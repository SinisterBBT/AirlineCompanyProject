package com.stringconcat.ddd.fitnes

import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.domain.JavaPackage
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.metrics.ArchitectureMetrics
import com.tngtech.archunit.library.metrics.MetricsComponents

@AnalyzeClasses(packages = ["com.polyakovworkbox.stringconcatcourse"])
class MetricsGuard {

    @ArchTest
    fun `uncle bob metrics for leasing`(importedClasses: JavaClasses) {
        val packages: Set<JavaPackage> = importedClasses.getPackage(
            "com.polyakovworkbox.stringconcatcourse.leasing").getSubpackages()
        val components: MetricsComponents<JavaClass> = MetricsComponents.fromPackages(packages)

        val metrics = ArchitectureMetrics.componentDependencyMetrics(components)

        println("Ce: " + metrics.getEfferentCoupling(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain"))
        println("Ca: " + metrics.getAfferentCoupling(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain"))
        println("I: " + metrics.getInstability(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain"))
        println("A: " + metrics.getAbstractness(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain"))
        println("D: " + metrics.getNormalizedDistanceFromMainSequence(
            "com.polyakovworkbox.stringconcatcourse.leasing.domain"))
    }
}