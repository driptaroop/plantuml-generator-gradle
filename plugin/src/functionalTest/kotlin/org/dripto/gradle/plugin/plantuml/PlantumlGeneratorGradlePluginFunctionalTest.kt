/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.dripto.gradle.plugin.plantuml

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import strikt.api.expectThat
import strikt.assertions.isTrue
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * A simple functional test for the 'org.dripto.gradle.plugin.plantuml.greeting' plugin.
 */
class PlantumlGeneratorGradlePluginFunctionalTest {
    private lateinit var projectDir: File
    private var buildScript =
        """
        import org.dripto.gradle.plugin.plantuml.task.PlantUmlGeneratorTask
            plugins {
                java
                id("org.dripto.gradle.plugin.plantuml.plantuml-generator")
            }
        """.trimIndent()

    @BeforeTest
    fun setup() {
        projectDir = File("build/functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle.kts").writeText("""rootProject.name = "plantuml-generator-gradle"""")
    }

    @Test
    fun `can run task and generate puml file`() {
        // Setup the test build
        projectDir.resolve("build.gradle.kts").writeText(
            buildScript +
                """
            
            tasks.withType<PlantUmlGeneratorTask>{
                config {
                    scanPackages = listOf("org.dripto.example.spring.snippets")
                    blacklistRegexp = ".*benchmarking.*"
                    hideFields = true
                    hideMethods = true
                    removeFields = true
                    removeMethods = true
                }
                asciiConfig {
                    enableAsciidocWrapper = false
                }
            }
        """
        )

        // Run the build
        runPlugin()

        // Verify the result
        // assertTrue(result.output.contains("Hello from plugin 'org.dripto.gradle.plugin.plantuml.greeting'"))
        File("$projectDir/build/reports/class-diagram.puml").shouldExist()
    }

    @Test
    fun `can run task and generate puml file with name`() {
        // Setup the test build
        projectDir.resolve("build.gradle.kts").writeText(
            buildScript +
                """
            
            tasks.withType<PlantUmlGeneratorTask>{
                destination="build/class-diagram.puml"
                config {
                    scanPackages = listOf("org.dripto.example.spring.snippets")
                    blacklistRegexp = ".*benchmarking.*"
                    hideFields = true
                    hideMethods = true
                    removeFields = true
                    removeMethods = true
                }
                asciiConfig {
                    enableAsciidocWrapper = false
                }
            }
        """
        )

        // Run the build
        runPlugin()

        // Verify the result
        // assertTrue(result.output.contains("Hello from plugin 'org.dripto.gradle.plugin.plantuml.greeting'"))
        File("$projectDir/build/class-diagram.puml").shouldExist()
        File("$projectDir/build/class-diagram.puml").delete()
    }

    private fun runPlugin(): BuildResult {
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("generateUml")
        runner.withProjectDir(projectDir)
        return runner.build()
    }

    @AfterTest
    fun teardown() {
        projectDir.deleteRecursively()
    }
}

private fun File.shouldExist() {
    expectThat(exists()).isTrue()
}