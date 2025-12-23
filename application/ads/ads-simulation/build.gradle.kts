plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"

    // id("io.gitlab.arturbosch.detekt") version "1.23.8"
    // id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
}

dependencies {
    implementation(platform("io.arrow-kt:arrow-stack:2.2.0"))

    implementation(project(":application:ads:ads-core"))

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("net.datafaker:datafaker:2.5.3")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

detekt {
    allRules = false
    config.setFrom(files("$rootDir/detekt.yaml"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
