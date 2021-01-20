import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.12.0"
    kotlin("jvm") version "1.4.21"
    id("org.jmailen.kotlinter") version "3.3.0"
    maven
}
group = "org.dripto.gradle.plugin.plantuml"
version = "0.0.3"
description = "Generate plantUml diagram from code using gradle"
val pluginId = "org.dripto.gradle.plugin.plantuml.plantuml-generator"
val githubUrl ="https://github.com/driptaroop/plantuml-generator-gradle"
val webUrl = "https://github.com/driptaroop/plantuml-generator-gradle"
val pluginName = "plantUmlGeneratorPlugin"

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("de.elnarion.util:plantuml-generator-util:1.2.0")
    testImplementation("io.strikt:strikt-core:0.28.1")
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.apache.commons:commons-lang3:3.11")
}

gradlePlugin {
    // Define the plugin
    plugins{
        register(pluginName) {
            id = pluginId
            implementationClass = "org.dripto.gradle.plugin.plantuml.PlantumlGeneratorGradlePlugin"
        }
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

tasks.withType<Test> {
    useJUnitPlatform()
}

pluginBundle {
    website = webUrl
    vcsUrl = githubUrl
    (plugins) {
        pluginName {
            description = project.description
            tags = listOf("kotlin", "plantuml", "diagram", "generator", "gradle")
            version = project.version.toString()
            displayName = "PlantUml Generator Gradle Plugin"
        }
    }
}
