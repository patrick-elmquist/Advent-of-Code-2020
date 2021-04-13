import common.Day

// Answer #1: 216
// Answer #2: 6708199680

fun main() {
    Day(n = 3) {
        answer {
            traverse(lines, 3 to 1).last()
        }
        answer {
            val slopes = listOf(
                1 to 1,
                3 to 1,
                5 to 1,
                7 to 1,
                1 to 2
            )
            slopes.map { slope -> traverse(lines, slope).last() }
                .reduce { a, b -> a * b }
        }
    }
}

private fun traverse(lines: List<String>, slope: Pair<Int, Int>): Sequence<Long> {
    val input = lines.joinToString("")
    val cols = lines.first().length
    return generateSequence(0 to 0) { (previous, trees) ->
        val x = (previous + slope.first) % cols
        val y = (previous + cols * slope.second) / cols
        val next = y * cols + x
        next to when {
            next >= input.length -> return@generateSequence null
            input[next] == '#' -> trees + 1
            else -> trees
        }
    }.map { it.second.toLong() }
}
