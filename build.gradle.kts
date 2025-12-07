plugins {
    kotlin("jvm") version "2.0.21"
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
