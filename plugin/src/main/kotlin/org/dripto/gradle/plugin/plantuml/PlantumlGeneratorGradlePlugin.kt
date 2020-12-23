/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package org.dripto.gradle.plugin.plantuml

import org.dripto.gradle.plugin.plantuml.task.PlantUmlGeneratorTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A simple 'hello world' plugin.
 */
class PlantumlGeneratorGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("generateUml", PlantUmlGeneratorTask::class.java) { task ->
            println("Generating Plant UML file in ${project.buildDir}")
            task.group = "generation"
        }
    }
}