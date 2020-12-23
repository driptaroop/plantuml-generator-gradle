package org.dripto.gradle.plugin.plantuml.task

import de.elnarion.util.plantuml.generator.PlantUMLClassDiagramGenerator
import org.dripto.gradle.plugin.plantuml.config.AsciiDocWrapperConfig
import org.dripto.gradle.plugin.plantuml.config.UmlGenerationConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

open class PlantUmlGeneratorTask : DefaultTask() {
    var destination: String = "${project.buildDir}/reports/class-diagram.puml"
    @get:Input var config = UmlGenerationConfig(project)
    @get:Input var asciiConfig = AsciiDocWrapperConfig()

    @OutputFile
    fun getDestination(): File = project.file(destination)

    @TaskAction
    fun generate() {
        val result = PlantUMLClassDiagramGenerator(config.plantUMLConfig).generateDiagramText()
            .let { if (asciiConfig.enableAsciidocWrapper) asciiConfig.createAsciidocWrappedDiagramText(it) else it }
        prepFile().writeText(result)
    }

    private fun prepFile(): File = getDestination().also { file ->
        file.delete()
        file.parentFile.mkdirs()
    }

    fun config(conf: UmlGenerationConfig.() -> Unit) {
        config = UmlGenerationConfig(project).apply(conf)
    }
    fun asciiConfig(conf: AsciiDocWrapperConfig.() -> Unit) {
        asciiConfig = AsciiDocWrapperConfig().apply(conf)
    }
}
