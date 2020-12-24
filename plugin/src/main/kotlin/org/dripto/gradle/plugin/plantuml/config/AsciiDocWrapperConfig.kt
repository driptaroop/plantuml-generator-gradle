package org.dripto.gradle.plugin.plantuml.config

import java.io.Serializable

data class AsciiDocWrapperConfig(
    var enableAsciidocWrapper: Boolean = false,
    var asciidocDiagramImageFormat: String = "png",
    var asciidocDiagramName: String = "ascii_diagram",
    var asciidocDiagramBlockDelimiter: String = "----"
) : Serializable {

    fun createAsciidocWrappedDiagramText(paramClassDiagramTextToWrap: String): String =
        """
        [plantuml, ${if (asciidocDiagramName.isNotBlank()) asciidocDiagramName else "ascii_diagram"}.$asciidocDiagramImageFormat, $asciidocDiagramImageFormat]
        $asciidocDiagramBlockDelimiter
        $paramClassDiagramTextToWrap
        $asciidocDiagramBlockDelimiter
        """.trimIndent()
}
