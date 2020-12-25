import common.*

// Answer #1: 3286137
// Answer #2: -

fun main() {
    Day(n = 25) {
        answer {
            val (k1, k2) = lines.map { it.toLong() }
            val loopSize = loopSequence(k2, 7L, Long.MAX_VALUE).last().first
            loopSequence(0, k1, loopSize).last().second
        }
    }
}

private fun loopSequence(key: Long, subject: Long, loopSize: Long) =
    generateSequence(0L to 1L) { (loops, value) ->
        if (value != key && loops != loopSize) loops + 1 to (value * subject) % 20201227L
        else null
    }
