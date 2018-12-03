package com.shaneauckland.adventofcode2018.puzzle

import com.shaneauckland.adventofcode2018.Puzzle
import java.io.File
import java.io.PrintStream
import java.nio.file.Paths

typealias Fabric = List<MutableList<Int>>

class Puzzle3(
    private val input: File,
    private val output: PrintStream
) : Puzzle {

    constructor() : this(Paths.get(Puzzle1::class.java.classLoader.getResource("puzzle3.txt").toURI()).toFile(), System.out)

    override fun run() {

        val regex = """#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)""".toRegex()

        val squares = input.readLines().map {
            regex.matchEntire(it)
                ?.destructured
                ?.let {
                    (id, x, y, width, height) -> Square(id.toInt(), x.toInt(), y.toInt(), width.toInt(), height.toInt())
                }
                ?: throw IllegalArgumentException("Bad input '$it'")
        }

        val (maxWidth, maxHeight) = squares.fold(Pair(0, 0)) { acc, s ->
            Pair(
                if (s.x + s.width > acc.first) s.x + s.width else acc.first,
                if (s.y + s.height > acc.second) s.y + s.height else acc.second
            )
        }

        val fabric = (0..maxWidth).map {
            (0..maxHeight).map { 0 }.toMutableList()
        }

        squares.forEach { updateRow(fabric, it, 0) }

        val overwrites = fabric.flatten().filter { it > 1 }.size

        output.println("Total overwrites: $overwrites")

        val noOverlapSquare = squares.find { findWithNoOverlap(it, squares.filterNot { s -> s.id == it.id }) }

        output.println("Square ${noOverlapSquare?.id} does not overlap")
    }

    private tailrec fun updateRow(fabric: Fabric, square: Square, x: Int): Fabric =
        if (x >= square.x + square.width) fabric
        else updateRow(updateColumn(fabric, square, x, 0), square, x + 1)

    private fun updateColumn(fabric: Fabric, square: Square, x: Int, y: Int): Fabric =
        if (y >= square.y + square.height) fabric
        else {
            if (x >= square.x && y >= square.y) fabric[x][y] += 1
            updateColumn(fabric, square, x, y + 1)
        }

    private fun findWithNoOverlap(square: Square, squares: List<Square>): Boolean =
        squares.none { hasOverlap(square, it) }

    private fun hasOverlap(square1: Square, square2: Square): Boolean {
        val left = Math.max(square1.x, square2.x)
        val right = Math.min(square1.x + square1.width, square2.x + square2.width)
        val bottom = Math.max(square1.y, square2.y)
        val top = Math.min(square1.y + square1.height, square2.y + square2.height)

        return top - bottom > 0 && right - left > 0
    }

    data class Square(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int)

}