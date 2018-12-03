package com.shaneauckland.adventofcode2018.puzzle

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.PrintStream

class Puzzle3Test {

    private val output: PrintStream = mock()

    @BeforeEach
    fun setup() {
        reset(output)
    }

    @Test
    fun `it finds the duplicated square inches from the example`() {
        Puzzle3(fileWithContent("#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2"), output).run()
        verify(output).println("Total overwrites: 4")
        verify(output).println("Square 3 does not overlap")
    }

    private fun fileWithContent(content: String): File {
        val file = File.createTempFile("changes.txt", null)
        file.writeText(content)
        return file
    }
}