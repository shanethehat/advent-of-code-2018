package com.shaneauckland.adventofcode2018


fun main(args: Array<String>) {
    if (args.isEmpty()) System.out.print("Specify a puzzle in the format \"Puzzle1\"")
    else startPuzzle(args[0])
}

private fun startPuzzle(name: String) {
    val fullName = "com.shaneauckland.adventofcode2018.puzzle.$name"

    val reference = Class.forName(fullName).newInstance()

    (reference as? Puzzle)?.run()
}
