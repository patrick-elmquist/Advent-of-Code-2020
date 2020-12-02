import common.Day

// Answer #1: 582
// Answer #2: 729

fun main() {
    Day(n = 2) {
        answer {
            lines.count { line ->
                val split = line.split(" ")

                val minMax = split[0].split('-')
                val range = minMax[0].toInt()..minMax[1].toInt()
                val letter = split[1].dropLast(1)[0]

                split[2].count { it == letter } in range
            }
        }
        answer {
            lines.count { line ->
                val split = line.split(" ")

                val index = split[0].split('-')
                val indices = listOf(index[0].toInt().dec(), index[1].toInt().dec())
                val letter = split[1].dropLast(1)[0]

                indices.count { split[2][it] == letter } == 1
            }
        }
    }
}