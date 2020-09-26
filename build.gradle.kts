import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("js") version "1.4.10" apply false
}

group = "dev.twarner"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks {
    val assembleExtension by registering(Copy::class) {
        from(project(":background").tasks.named<KotlinWebpack>("browserProductionWebpack").map { it.outputFile })
        from(project(":content").tasks.named<KotlinWebpack>("browserProductionWebpack").map { it.outputFile })
        from("manifest.json")
        into(buildDir.resolve("extension"))
    }
}