plugins {
	id(Plugins.spring_boot) version PluginVers.spring_boot
	id(Plugins.spring_dependency_management) version PluginVers.spring_dependency_management
	id(Plugins.spring_kotlin) version PluginVers.spring_kotlin
}

dependencies {

	//project
	implementation(project(":common:types"))
	implementation(project(":leasing:domain"))

	// kotlin
	implementation(Libs.kotlin_jdk8)
	implementation(Libs.kotlin_reflect)
	implementation(Libs.kotlin_stdlib)

	// arrow
	implementation(Libs.arrow)

	// spring
	implementation(Libs.spring_boot_starter_web)
	implementation(Libs.spring_boot_starter_logging)

	// logging
	implementation(Libs.slf4j_api)

	// test
	testImplementation(Libs.kotest_junit)
	testImplementation(Libs.kotest_arrow)
	testImplementation(Libs.junit_engine)
	testImplementation(Libs.junit_params)
	testImplementation(Libs.spring_boot_starter_test) {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}