plugins {
    kotlin("plugin.spring") version "2.0.21" apply false
    kotlin("plugin.jpa") version "2.0.21" apply false

    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1" apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
