import common.Day
import extension.toInts

// Answer #1: 806656
// Answer #2: 230608320

fun main() {
    Day(n = 1) {
        answer {
            val input = lines.toInts()
            input.forEach { n1 ->
                input.forEach { n2 -> if (n1 + n2 == 2020) return@answer n1 * n2 }
            }
        }
        answer {
            val input = lines.toInts()
            input.forEach { n1 ->
                input.forEach { n2 ->
                    input.forEach { n3 -> if (n1 + n2 + n3 == 2020) return@answer n1 * n2 * n3 }
                }
            }
        }
    }
}