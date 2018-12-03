package com.shaneauckland.adventofcode2018.puzzle

import com.shaneauckland.adventofcode2018.Puzzle
import java.io.BufferedReader
import java.io.File
import java.io.PrintStream
import java.nio.file.Paths

class Puzzle1(
    private val input: File,
    private val output: PrintStream
) : Puzzle {

    constructor() : this(Paths.get(Puzzle1::class.java.classLoader.getResource("puzzle1.txt").toURI()).toFile(), System.out)

    override fun run() {

        if (input.length().toInt() == 0) {
            output.println("Total: 0")
            output.println("Empty file, no repeater")
            return
        }

        val reader = input.bufferedReader()

        val (firstTotal, firstTotals, firstRepeater) = addLineToTotal(reader, 0, listOf(0), null)

        output.println("Total: $firstTotal")

        if (firstRepeater != null) output.println("Repeater: $firstRepeater")
        else {
            val eventualRepeater = getEventualRepeater(reader, firstTotal, firstTotals, null)
            output.println("Repeater: $eventualRepeater")
        }
    }


    private tailrec fun addLineToTotal(reader: BufferedReader, total: Int, totals: List<Int>, repeater: Int?): Triple<Int, List<Int>, Int?> {
        val line = reader.readLine()
        return if (line == null) Triple(total, totals, repeater)
        else {
            val newTotal = total + line.toInt()
            if (totals.contains(newTotal) && repeater == null) addLineToTotal(reader, newTotal, totals + total, newTotal)
            else addLineToTotal(reader, newTotal, totals + total, repeater)
        }
    }

    private tailrec fun getEventualRepeater(reader: BufferedReader, total: Int, totals: List<Int>, repeater: Int?): Int {
        val line = reader.readLine()
        return if (line == null) {
            reader.close()
            getEventualRepeater (input.bufferedReader(), total, totals, repeater)
        }
        else {
            val newTotal = total + line.toInt()
            if (totals.contains(newTotal)) newTotal
            else getEventualRepeater(reader, newTotal, totals + total, repeater)
        }
    }
}