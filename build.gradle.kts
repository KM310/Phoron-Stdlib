plugins {
    kotlin("jvm") version "2.0.21"
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = project.property("maven_group")!!
version = project.property("mod_version")!!

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    // Kotlin libs als API (werden exportiert)
    api(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Shaded Libraries (Apache-2.0)
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("it.unimi.dsi:fastutil:8.5.12")

    // Fabric Loader API (für ModInitializer)
    compileOnly("net.fabricmc:fabric-loader:0.16.0")

    // Fabric API (nur falls du später API-Klassen nutzt)
    compileOnly("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveClassifier.set("")

    // Relocate shaded libs
    relocate("com.google.gson", "me.km310.shaded.gson")
    relocate("com.github.benmanes.caffeine", "me.km310.shaded.caffeine")
    relocate("it.unimi.dsi.fastutil", "me.km310.shaded.fastutil")
    relocate("kotlinx.coroutines", "me.km310.shaded.coroutines")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
