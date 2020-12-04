import common.Day
import extension.split

// Answer #1: 582
// Answer #2: 729

fun main() {
    Day(n = 2) {
        answer {
            lines.split(" ").count { split ->
                val minMax = split[0].split('-')
                val range = minMax[0].toInt()..minMax[1].toInt()
                val letter = split[1][0]
                split[2].count { it == letter } in range
            }
        }
        answer {
            lines.split(" ").count { split ->
                val index = split[0].split('-')
                val indices = listOf(index[0].toInt() - 1, index[1].toInt() - 1)
                val letter = split[1][0]
                indices.count { split[2][it] == letter } == 1
            }
        }
    }
}