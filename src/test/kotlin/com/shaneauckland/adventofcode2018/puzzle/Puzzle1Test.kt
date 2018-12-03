package com.shaneauckland.adventofcode2018.puzzle

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.PrintStream

class Puzzle1Test {

    private val output: PrintStream = mock()

    @BeforeEach
    fun setup() {
        reset(output)
    }

    @Test
    fun `it handles an empty stream`() {
        Puzzle1(fileWithContent(""), output).run()
        verify(output).println("Total: 0")
        verify(output).println("Empty file, no repeater")
    }

    @Test
    fun `it finds a repeater within a single list`() {
        Puzzle1(fileWithContent("+1\n-1"), output).run()
        verify(output).println("Total: 0")
        verify(output).println("Repeater: 0")
    }

    @Test
    fun `it finds a repeater after multiple iterations`() {
        Puzzle1(fileWithContent("+3\n+3\n+4\n-2\n-4"), output).run()
        verify(output).println("Total: 4")
        verify(output).println("Repeater: 10")
    }

    private fun fileWithContent(content: String): File {
        val file = File.createTempFile("changes.txt", null)
        file.writeText(content)
        return file
    }
}