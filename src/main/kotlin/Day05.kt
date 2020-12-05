import common.Day

// Answer #1: 885
// Answer #2: 623

fun main() {
    Day(n = 5) {
        answer {
            lines.mapToSeatNumbers().maxOrNull()
        }
        answer {
            val numbers = lines.mapToSeatNumbers().sorted()
            (numbers.first()..numbers.last()).first { nbr -> nbr !in numbers }
        }
    }
}

private fun List<String>.mapToSeatNumbers() =
    map { seq(it.take(7), 0..127).last() * 8 + seq(it.drop(7), 0..7).last() }

private fun seq(input: String, range: IntRange) = generateSequence(input to range.toList()) { (input, range) ->
    input.drop(1) to when {
        range.size == 1 -> return@generateSequence null
        input.first() in setOf('F', 'L') -> range.take(range.size / 2)
        input.first() in setOf('B', 'R') -> range.drop(range.size / 2)
        else -> throw IllegalStateException()
    }
}.map { (_, range) -> range.first() }

