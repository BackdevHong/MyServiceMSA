import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.5.15"
    id("io.spring.dependency-management") version "1.1.7"
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

subprojects {
    group = "com.zeronsoftn.honginsung"
    version = "0.0.1-SNAPSHOT"
    description = "MyServiceMSA"

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    configurations.named("compileOnly") {
        extendsFrom(configurations.named("annotationProcessor").get())
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        add("implementation", "org.springframework.boot:spring-boot-starter-amqp")
        add("implementation", "org.springframework.boot:spring-boot-starter-data-jpa")
        add("implementation", "org.springframework.boot:spring-boot-starter-data-redis")
        add("implementation", "org.springframework.boot:spring-boot-starter-graphql")
        add("implementation", "org.springframework.boot:spring-boot-starter-rsocket")
        add("implementation", "org.springframework.boot:spring-boot-starter-web")
        add("implementation", "org.springframework.amqp:spring-rabbit-stream")

        add("runtimeOnly", "com.h2database:h2")

        add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
        add("testImplementation", "io.projectreactor:reactor-test")
        add("testImplementation", "org.springframework.amqp:spring-rabbit-test")
        add("testImplementation", "org.springframework.graphql:spring-graphql-test")
        add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}