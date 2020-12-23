package org.dripto.gradle.plugin.plantuml.config

import de.elnarion.util.plantuml.generator.classdiagram.ClassifierType
import de.elnarion.util.plantuml.generator.classdiagram.VisibilityType
import de.elnarion.util.plantuml.generator.config.PlantUMLConfig
import de.elnarion.util.plantuml.generator.config.PlantUMLConfigBuilder
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import java.io.Serializable
import java.net.URL
import java.net.URLClassLoader

data class UmlGenerationConfig(
    @Transient private val project: Project,
    var destinationClassloader: ClassLoader? = null,
    var scanPackages: List<String> = listOf(),
    var blacklistRegexp: String? = null,
    var whitelistRegexp: String? = null,
    var hideClasses: List<String>? = null,
    var hideFields: Boolean = false,
    var hideMethods: Boolean = false,
    var removeFields: Boolean = false,
    var removeMethods: Boolean = false,
    var addJPAAnnotations: Boolean = false,
    var fieldBlacklistRegexp: String? = null,
    var methodBlacklistRegexp: String? = null,
    var maxVisibilityFields: VisibilityType = VisibilityType.PRIVATE,
    var maxVisibilityMethods: VisibilityType = VisibilityType.PRIVATE,
    var fieldClassifierToIgnore: List<ClassifierType> = ArrayList(),
    var methodClassifierToIgnore: List<ClassifierType> = ArrayList()
) : Serializable {

    val plantUMLConfig: PlantUMLConfig
        get() {
            val loader: ClassLoader = getCompileClassLoader()
            val configBuilder: PlantUMLConfigBuilder = if (whitelistRegexp.isNullOrBlank()) {
                PlantUMLConfigBuilder(if (!blacklistRegexp.isNullOrBlank()) blacklistRegexp else null, scanPackages)
            } else {
                PlantUMLConfigBuilder(scanPackages, whitelistRegexp)
            }

            return configBuilder.withClassLoader(loader).withHideClasses(hideClasses).withHideFieldsParameter(hideFields)
                .withHideMethods(hideMethods).addFieldClassifiersToIgnore(fieldClassifierToIgnore)
                .addMethodClassifiersToIgnore(methodClassifierToIgnore).withRemoveFields(removeFields)
                .withRemoveMethods(removeMethods).withFieldBlacklistRegexp(fieldBlacklistRegexp)
                .withMethodBlacklistRegexp(methodBlacklistRegexp).withMaximumFieldVisibility(maxVisibilityFields)
                .withMaximumMethodVisibility(maxVisibilityMethods).withJPAAnnotations(addJPAAnnotations).build()
        }

    private fun getSourceSet(sourceSetName: String): SourceSet? {
        val sourceSetContainer: SourceSetContainer = project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets
        return sourceSetContainer.findByName(sourceSetName)
    }
    private fun getClassURLArray(sourceSet: SourceSet): Array<URL> {
        val runtimeClasspathElements: FileCollection = sourceSet.runtimeClasspath
        val compileClasspathElements: FileCollection = sourceSet.compileClasspath
        return (runtimeClasspathElements + compileClasspathElements)
            .map { it.toURI().toURL() }
            .toList().toTypedArray()
    }
    private fun getCompileClassLoader() =
        getSourceSet("main") ?. let {
            URLClassLoader(getClassURLArray(it), Thread.currentThread().contextClassLoader)
        } ?: throw IllegalStateException("no main source set")
}
