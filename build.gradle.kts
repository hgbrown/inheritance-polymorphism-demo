plugins {
    kotlin("jvm") version "1.6.10"
    java
}

group = "dev.hbrown"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        events("started", "passed", "skipped", "failed")
    }
}
