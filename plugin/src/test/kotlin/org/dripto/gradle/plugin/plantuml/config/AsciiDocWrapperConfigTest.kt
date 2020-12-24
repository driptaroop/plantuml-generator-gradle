package org.dripto.gradle.plugin.plantuml.config

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class AsciiDocWrapperConfigTest {
    @Test
    fun `generate AsciidocWrappedDiagramText`() {
        val text = "test"
        val result = AsciiDocWrapperConfig().createAsciidocWrappedDiagramText(text)
        expectThat(result.trim()).isEqualTo("""
            [plantuml, ascii_diagram.png, png]
            |----
            |$text
            |----
        """.trimMargin().trim())
    }
    @Test
    fun `generate AsciidocWrappedDiagramText custom parameters`() {
        val text = "test"
        val result = AsciiDocWrapperConfig(
            asciidocDiagramImageFormat = "jpg",
            asciidocDiagramName = "test",
            asciidocDiagramBlockDelimiter = ".."
        ).createAsciidocWrappedDiagramText(text)
        expectThat(result.trim()).isEqualTo("""
            [plantuml, test.jpg, jpg]
            |..
            |$text
            |..
        """.trimMargin().trim())
    }
}