plugins {
    kotlin("jvm") version "1.9.21"
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
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:19.0.2.1")
    implementation("org.openjfx:javafx-fxml:19.0.2.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}