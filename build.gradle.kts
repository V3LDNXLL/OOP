import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    application
    alias(libs.plugins.shadow.jar)
}

dependencies {
    implementation(libs.h2)
}

application {
    mainClass.set("com.v3ldnxll.hotel.Main")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("app")
    archiveClassifier.set("")
}
