package com.shaneauckland.adventofcode2018.puzzle

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.PrintStream

class Puzzle2Test {

    private val output: PrintStream = mock()

    @BeforeEach
    fun setup() {
        reset(output)
    }

    @Test
    fun `it handles an empty stream`() {
        Puzzle2(fileWithContent(""), output).run()
        verify(output).println("Total groups of 2: 0")
        verify(output).println("Total groups of 3: 0")
        verify(output).println("Checksum: 0")
    }

    @Test
    fun `it finds no groups in a string of unique characters`() {
        Puzzle2(fileWithContent("abcdef"), output).run()
        verify(output).println("Total groups of 2: 0")
        verify(output).println("Total groups of 3: 0")
        verify(output).println("Checksum: 0")
    }

    @Test
    fun `it finds a single group of 2 characters`() {
        Puzzle2(fileWithContent("abacdef"), output).run()
        verify(output).println("Total groups of 2: 1")
        verify(output).println("Total groups of 3: 0")
    }

    @Test
    fun `it finds a single group of 3 characters`() {
        Puzzle2(fileWithContent("abacdaef"), output).run()
        verify(output).println("Total groups of 2: 0")
        verify(output).println("Total groups of 3: 1")
    }

    @Test
    fun `it ignores multiple groups of two and 3 characters in a single line`() {
        Puzzle2(fileWithContent("aabbcccdddeee"), output).run()
        verify(output).println("Total groups of 2: 1")
        verify(output).println("Total groups of 3: 1")
        verify(output).println("Checksum: 1")
    }

    @Test
    fun `it counts groups of two and 3 characters over multiple lines`() {
        Puzzle2(fileWithContent("aab\naabb\naaabbccc\nfffd"), output).run()
        verify(output).println("Total groups of 2: 3")
        verify(output).println("Total groups of 3: 2")
        verify(output).println("Checksum: 6")
    }

    @Test
    fun `it finds lines with only a single character difference`() {
        Puzzle2(fileWithContent("abcde\nfghij\nklmno\npqrst\nfguij\naxcye\nwvxyz"), output).run()
        verify(output).println("Letters matching: fgij")
    }

    private fun fileWithContent(content: String): File {
        val file = File.createTempFile("changes.txt", null)
        file.writeText(content)
        return file
    }

}