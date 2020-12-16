import common.Day
import extension.match

// Answer #1: 582
// Answer #2: 729

private val REGEX = "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)".toRegex()

fun main() {
    Day(n = 2) {
        answer {
            lines.count { line ->
                REGEX.match(line) { (min, max, c, s) ->
                    val letter = c.first()
                    val minMax = min.toInt()..max.toInt()
                    s.count { it == letter } in minMax
                }
            }
        }
        answer {
            lines.count { line ->
                REGEX.match(line) { (i1, i2, c, s) ->
                    val letter = c.first()
                    val indices = listOf(i1.toInt(), i2.toInt())
                    indices.count { s[it - 1] == letter } == 1
                }
            }
        }
    }
}