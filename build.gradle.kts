/*
 * Written in 2024 by Nikomaru <nikomaru@nikomaru.dev>
 *
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide.This software is distributed without any warranty.
 *
 * You should have received a copy of the CC0 Public Domain Dedication along with this software.
 * If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import xyz.jpenilla.resourcefactory.bukkit.Permission

plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory)
}

group = "dev.nikomaru"
version = "1.0-SNAPSHOT"

fun captureVersion(dependency: Dependency): String {
    return dependency.version ?: throw IllegalArgumentException("Version not found for $dependency")
}


repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://plugins.gradle.org/m2/")
    maven("https://repo.incendo.org/content/repositories/snapshots")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly(libs.paper.api)

    compileOnly(kotlin("stdlib"))

    implementation(libs.lamp.common)
    implementation(libs.lamp.bukkit)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.csv)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.mccoroutine.bukkit.api)
    implementation(libs.mccoroutine.bukkit.core)

    implementation(libs.koin.core)

    compileOnly(libs.squaremap.api)
    compileOnly(libs.protocolLib)

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    testImplementation(libs.junit.jupiter)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
        kotlinOptions.javaParameters = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "21"
    }
    build {
        dependsOn("shadowJar")
    }
    runServer {
        minecraftVersion("1.20.6")
    }
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
    test {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "skipped", "failed")
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}



sourceSets.main {
    resourceFactory {
        bukkitPluginYaml {
            name = "AdvanceRailway"
            version = "miencraft_plugin_version"
            website = "https://github.com/Nlkomaru/AdvanceRailway"
            depend = listOf("ProtocolLib", "squaremap")
            main = "$group.advancerailway.AdvanceRailway"
            authors = listOf("Nikomaru")

            permissions {
                register("advancerailway.admin") {
                    default = Permission.Default.TRUE
                    children(
                        "advancerailway.command.group",
                        "advancerailway.command.station",
                        "advancerailway.command.railway",
                        "advancerailway.command.common"
                    )
                }
            }
            apiVersion = "1.20"
        }
    }
}