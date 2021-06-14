plugins {
	id(Plugins.spring_boot) version PluginVers.spring_boot
	id(Plugins.spring_dependency_management) version PluginVers.spring_dependency_management
	id(Plugins.spring_kotlin) version PluginVers.spring_kotlin
}

dependencies {

	// kotlin
	implementation(Libs.kotlin_jdk8)
	implementation(Libs.kotlin_reflect)
	implementation(Libs.kotlin_stdlib)

	// spring
	implementation(Libs.spring_boot_starter_web)
	implementation(Libs.spring_boot_starter_thymeleaf)
	implementation(Libs.spring_boot_starter_logging)

	// jackson
	implementation(Libs.jackson_kotlin)

	// arrow
	implementation(Libs.arrow)

	// table
	implementation(Libs.ascii_table)

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