package org.dripto.gradle.plugin.plantuml.utils

import de.elnarion.util.plantuml.generator.classdiagram.ClassifierType
import org.apache.commons.lang3.RandomStringUtils
import kotlin.random.Random

fun randomListString(size: Int = 100) = List(Random.nextInt(size)) { randomString() }

fun randomListClassifier(size: Int = 100): List<ClassifierType> =
    List(Random.nextInt(size)) { randomEnum<ClassifierType>() }

fun randomString(size: Int = 100): String =
    RandomStringUtils.randomAlphabetic(5, Random.nextInt(6, size))

inline fun <reified T : Enum<T>> randomEnum(except: List<T> = listOf()): T =
    enumValues<T>()
        .filterNot { except.contains(it) }
        .random()

const val TEST_RUNS = 10
