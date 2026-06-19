plugins {
    kotlin("jvm") version "2.3.20"
}

group = "com.zeronsoftn.honginsung"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}