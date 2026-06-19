plugins {
    id("org.springframework.boot") version "3.5.15" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false

    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    kotlin("plugin.jpa") version "1.9.25" apply false
}

allprojects {
    group = "com.zeronsoftn.honginsung"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}