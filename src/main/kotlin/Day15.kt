import common.Day
import extension.toInts

// Answer #1: 929
// Answer #2: 16671510

fun main() {
    Day(n = 15) {
        answer {
            val numbers = lines.first().split(',').toInts()
            findNthNumber(numbers, 2020)
        }

        answer {
            val numbers = lines.first().split(',').toInts()
            findNthNumber(numbers, 30000000)
        }
    }
}

private fun findNthNumber(numbers: List<Int>, n: Int): Int {
    val lastSeen = numbers.dropLast(1)
        .mapIndexed { index, number -> number to index }
        .toMap().toMutableMap()
    var count = numbers.size - 1
    var previous = numbers.last()
    while (count < n - 1) {
        val next = lastSeen[previous]?.let { count - it } ?: 0
        lastSeen[previous] = count
        previous = next
        count++
    }
    return previous
}
