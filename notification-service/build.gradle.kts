plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(
        "org.springframework.boot:spring-boot-starter-amqp",
    )

    implementation(
        "org.springframework.boot:spring-boot-starter-json"
    )

    implementation(
        "com.fasterxml.jackson.module:jackson-module-kotlin",
    )

    implementation(
        "org.jetbrains.kotlin:kotlin-reflect",
    )

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test",
    )

    testImplementation(
        "org.springframework.amqp:spring-rabbit-test",
    )

    testRuntimeOnly(
        "org.junit.platform:junit-platform-launcher",
    )
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}