import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.3.72"
    id("org.jmailen.kotlinter") version "2.4.1"
    maven
}
group = "org.dripto.gradle.plugin.plantuml"
version = "0.0.1"

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("de.elnarion.util:plantuml-generator-util:1.2.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.strikt:strikt-core:0.28.1")
}

gradlePlugin {
    // Define the plugin
    val plantumlGenerator by plugins.registering {
        id = "org.dripto.gradle.plugin.plantuml.plantuml-generator"
        implementationClass = "org.dripto.gradle.plugin.plantuml.PlantumlGeneratorGradlePlugin"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
        apiVersion = "1.4"
        languageVersion = "1.4"
    }
}
// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

// Add a task to run the functional tests
val functionalTest by tasks.registering(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

tasks.check {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}