import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.22"
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.22"
}

group = "com.don"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("io.projectreactor.netty:reactor-netty-core:1.1.5")
    implementation ("io.projectreactor.tools:blockhound:1.0.6.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("io.r2dbc:r2dbc-h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

}

tasks.withType<Test>().all {
    if (JavaVersion.current() >= JavaVersion.VERSION_13) {
        jvmArgs!!.add("-XX:+AllowRedefinitionToAddDeleteMethods")
    }
}