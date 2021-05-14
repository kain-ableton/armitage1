import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("com.github.johnrengelman.shadow") version "5.1.0"
    kotlin("jvm") version "1.3.50"
}

group = "com.wavproductions.www"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://www.jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.msgpack", "msgpack", "0.6.12") //TODO implement and replace with up to date kotlin version
    implementation("com.github.Moocow9m:Viken:master-SNAPSHOT")
    testImplementation("junit", "junit", "4.12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("armitage")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.wavproductions.www.armitage.MainKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}