package com.shaneauckland.adventofcode2018.puzzle

import com.shaneauckland.adventofcode2018.Puzzle
import java.io.BufferedReader
import java.io.File
import java.io.PrintStream
import java.nio.file.Paths

class Puzzle2(
    private val input: File,
    private val output: PrintStream
) : Puzzle {

    constructor() : this(Paths.get(Puzzle1::class.java.classLoader.getResource("puzzle2.txt").toURI()).toFile(), System.out)

    override fun run() {
        val (pairs, triples) = gatherPairsAndTriples(input.bufferedReader(), 0, 0)
        val checksum = pairs * triples

        output.println("Total groups of 2: $pairs")
        output.println("Total groups of 3: $triples")
        output.println("Checksum: $checksum")

        val lines = input.readLines()

        if (lines.isEmpty()) {
            return
        }

        try {
            val (a, b) = findOverallMatch(lines)

            output.println("Letters matching: " + collectMatches(a, b))
        } catch (e: RuntimeException) {

        }
    }

    private tailrec fun gatherPairsAndTriples(reader: BufferedReader, pairs: Int, triples: Int): Pair<Int, Int> {
        val line = reader.readLine()
        return if (line == null) Pair(pairs, triples)
        else {
            val grouped = line.groupBy { it }.values
            gatherPairsAndTriples(
                reader,
                if (grouped.any { it.size == 2 }) pairs + 1 else pairs,
                if (grouped.any { it.size == 3 }) triples + 1 else triples
            )
        }
    }

    private tailrec fun findOverallMatch(targets: List<String>): Pair<String, String> =
        findSingleMatch(targets.first(), targets.drop(1)) ?: findOverallMatch(targets.drop(1))

    private tailrec fun findSingleMatch(subject: String, targets: List<String>): Pair<String, String>? =
        when {
            targets.isEmpty() -> null
            countDifferences(subject, targets.first()) == 1 -> Pair(subject, targets.first())
            else -> findSingleMatch(subject, targets.drop(1))
        }


    private tailrec fun countDifferences(string1: String, string2: String, differences: Int = 0): Int =
        if (string1.isEmpty()) differences
        else countDifferences(
            string1.drop(1),
            string2.drop(1),
            if (string1.first() == string2.first()) differences else differences + 1
        )

    private tailrec fun collectMatches(string1: String, string2: String, matches: String = ""): String =
        if (string1.isEmpty()) matches
        else collectMatches(
            string1.drop(1),
            string2.drop(1),
            if (string1.first() == string2.first()) matches + string1.first() else matches
        )

}