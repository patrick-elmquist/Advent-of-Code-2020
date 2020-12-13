import common.Day
import common.lcm
import extension.NEW_LINE

// Answer #1: 2305
// Answer #2: 552612234243498

fun main() {
    Day(n = 13) {
        answer {
            val timestamp = lines.first().toInt()
            lines.drop(1).first().split(",")
                .mapNotNull { it.toIntOrNull() }
                .map { it to (timestamp / it + 1) * it - timestamp }
                .minByOrNull { it.second }
                ?.let { (n, m) -> n * m }
        }
        answer {
            val map = lines.drop(1).first()
                .split(",")
                .mapIndexedNotNull { index, c -> if (c != "x") index.toLong() to c.toLong() else null }

            map.drop(1)
                .fold(map.first().second to 0L) { (step, t), (offset, n) ->
                    var time = t
                    while ((time + offset) % n != 0L) time += step
                    (step * n) to time
                }.second
        }
    }
}
