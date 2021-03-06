/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.dripto.gradle.plugin.plantuml

import org.dripto.gradle.plugin.plantuml.task.PlantUmlGeneratorTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isNotNull

/**
 * A simple unit test for the 'org.dripto.gradle.plugin.plantuml.greeting' plugin.
 */
class PlantumlGeneratorGradlePluginTest {
    @Test
    fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("org.dripto.gradle.plugin.plantuml.plantuml-generator")

        expectThat(project.tasks.findByName("generateUml")).isNotNull().isA<PlantUmlGeneratorTask>()
    }
}
