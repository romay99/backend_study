plugins {
    kotlin("jvm") version "1.9.23"
}

allprojects {
    group = "dev"
    version = "0.0.1-SNAPSHOT"
    description = "Demo project for Spring Boot"

    apply(plugin = "java")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
    }
}
