import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "11.0.2"
    modules("javafx.controls", "javafx.graphics")
}


dependencies {
    testImplementation(kotlin("test"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:19.0.2.1")
    implementation("org.openjfx:javafx-fxml:19.0.2.1")
    implementation("org.json:json:20230227")
}



tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}