package org.dripto.gradle.plugin.plantuml.utils

import de.elnarion.util.plantuml.generator.classdiagram.ClassifierType
import org.gradle.internal.impldep.org.apache.commons.lang.RandomStringUtils
import kotlin.random.Random


fun randomListString(size: Int = 100) = List(Random.nextInt(size)) { randomString() }

fun randomListClassifier(size: Int = 100): List<ClassifierType> =
    List(Random.nextInt(size)) { randomEnum<ClassifierType>() }

fun randomString(size: Int = 100): String =
    RandomStringUtils.randomAlphabetic(Random.nextInt(size))

inline fun <reified T : Enum<T>> randomEnum(except: List<T> = listOf()): T =
    enumValues<T>()
    .filterNot { except.contains(it) }
    .random()

const val TEST_RUNS = 10
