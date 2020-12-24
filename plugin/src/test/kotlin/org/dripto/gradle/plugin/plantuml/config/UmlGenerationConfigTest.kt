package org.dripto.gradle.plugin.plantuml.config

import de.elnarion.util.plantuml.generator.classdiagram.VisibilityType.PRIVATE
import org.dripto.gradle.plugin.plantuml.utils.TEST_RUNS
import org.dripto.gradle.plugin.plantuml.utils.randomEnum
import org.dripto.gradle.plugin.plantuml.utils.randomListClassifier
import org.dripto.gradle.plugin.plantuml.utils.randomListString
import org.dripto.gradle.plugin.plantuml.utils.randomString
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import java.net.URL
import java.net.URLClassLoader
import kotlin.random.Random

internal class UmlGenerationConfigTest {
    private lateinit var project: Project

    @BeforeEach
    fun setup() {
        project = ProjectBuilder.builder().build().also {
            it.plugins.apply("java")
        }
    }

    @Test
    fun `should get correct plantUml config default values`() {
        val config = UmlGenerationConfig(project = project)
        val result = config.plantUMLConfig
        expect {
            with(result) {
                that(blacklistRegexp).isNull()
                that(scanPackages).hasSize(0)
                that(whitelistRegexp).isNull()
                that(hideClasses).isNull()
                that(isHideFields).isFalse()
                that(isHideMethods).isFalse()
                that(isRemoveFields).isFalse()
                that(isRemoveMethods).isFalse()
                that(isAddJPAAnnotations).isFalse()
                that(fieldBlacklistRegexp).isNull()
                that(methodBlacklistRegexp).isNull()
                that(maxVisibilityFields).isEqualTo(PRIVATE)
                that(maxVisibilityMethods).isEqualTo(PRIVATE)
                that(fieldClassifierToIgnore).hasSize(0)
                that(methodClassifierToIgnore).hasSize(0)
                that(destinationClassloader).isA<URLClassLoader>()
                    .get {
                        that(urLs).isEqualTo(sourceSets())
                    }
            }
        }
    }

    @RepeatedTest(TEST_RUNS)
    fun `should get correct plantUml config non default values with WhiteList`() {
        val config = UmlGenerationConfig(
            project = project,
            scanPackages = randomListString(),
            blacklistRegexp = randomString(),
            whitelistRegexp = randomString(),
            hideClasses = randomListString(),
            hideFields = Random.nextBoolean(),
            hideMethods = Random.nextBoolean(),
            removeFields = Random.nextBoolean(),
            removeMethods = Random.nextBoolean(),
            addJPAAnnotations = Random.nextBoolean(),
            fieldBlacklistRegexp = randomString(),
            methodBlacklistRegexp = randomString(),
            maxVisibilityFields = randomEnum(),
            maxVisibilityMethods = randomEnum(),
            fieldClassifierToIgnore = randomListClassifier(),
            methodClassifierToIgnore = randomListClassifier()
        )

        val result = config.plantUMLConfig
        expect {
            with(result) {
                that(blacklistRegexp).isNull()
                that(scanPackages).hasSize(config.scanPackages.size)
                that(whitelistRegexp).isEqualTo(config.whitelistRegexp)
                that(hideClasses).isEqualTo(config.hideClasses)
                that(isHideFields).isEqualTo(config.hideFields)
                that(isHideMethods).isEqualTo(config.hideMethods)
                that(isRemoveFields).isEqualTo(config.removeFields)
                that(isRemoveMethods).isEqualTo(config.removeMethods)
                that(isAddJPAAnnotations).isEqualTo(config.addJPAAnnotations)
                that(fieldBlacklistRegexp)isEqualTo(config.fieldBlacklistRegexp)
                that(methodBlacklistRegexp).isEqualTo(config.methodBlacklistRegexp)
                that(maxVisibilityFields).isEqualTo(config.maxVisibilityFields)
                that(maxVisibilityMethods).isEqualTo(config.maxVisibilityMethods)
                that(fieldClassifierToIgnore).hasSize(config.fieldClassifierToIgnore.size)
                that(methodClassifierToIgnore).hasSize(config.methodClassifierToIgnore.size)
                that(destinationClassloader).isA<URLClassLoader>()
                    .get {
                        that(urLs).isEqualTo(sourceSets())
                    }
            }
        }
    }

    @RepeatedTest(TEST_RUNS)
    fun `should get correct plantUml config non default values without WhiteList`() {
        val config = UmlGenerationConfig(
            project = project,
            scanPackages = randomListString(),
            blacklistRegexp = randomString(),
            whitelistRegexp = null,
            hideClasses = randomListString(),
            hideFields = Random.nextBoolean(),
            hideMethods = Random.nextBoolean(),
            removeFields = Random.nextBoolean(),
            removeMethods = Random.nextBoolean(),
            addJPAAnnotations = Random.nextBoolean(),
            fieldBlacklistRegexp = randomString(),
            methodBlacklistRegexp = randomString(),
            maxVisibilityFields = randomEnum(),
            maxVisibilityMethods = randomEnum(),
            fieldClassifierToIgnore = randomListClassifier(),
            methodClassifierToIgnore = randomListClassifier()
        )

        val result = config.plantUMLConfig
        expect {
            with(result) {
                that(blacklistRegexp).isEqualTo(config.blacklistRegexp)
                that(scanPackages).hasSize(config.scanPackages.size)
                that(whitelistRegexp).isNull()
                that(hideClasses).isEqualTo(config.hideClasses)
                that(isHideFields).isEqualTo(config.hideFields)
                that(isHideMethods).isEqualTo(config.hideMethods)
                that(isRemoveFields).isEqualTo(config.removeFields)
                that(isRemoveMethods).isEqualTo(config.removeMethods)
                that(isAddJPAAnnotations).isEqualTo(config.addJPAAnnotations)
                that(fieldBlacklistRegexp)isEqualTo(config.fieldBlacklistRegexp)
                that(methodBlacklistRegexp).isEqualTo(config.methodBlacklistRegexp)
                that(maxVisibilityFields).isEqualTo(config.maxVisibilityFields)
                that(maxVisibilityMethods).isEqualTo(config.maxVisibilityMethods)
                that(fieldClassifierToIgnore).hasSize(config.fieldClassifierToIgnore.size)
                that(methodClassifierToIgnore).hasSize(config.methodClassifierToIgnore.size)
                that(destinationClassloader).isA<URLClassLoader>()
                    .get {
                        that(urLs).isEqualTo(sourceSets())
                    }
            }
        }
    }

    private fun sourceSets(): Array<URL> =
        with(project.convention.getPlugin(JavaPluginConvention::class.java).sourceSets.findByName("main")!!) {
            (runtimeClasspath + compileClasspath).map { it.toURI().toURL() }.toList().toTypedArray()
        }
}