import common.Day

// Answer #1: 806656
// Answer #2: 230608320

fun main() {
    Day(n = 1) {
        answer {
            ints.forEach { n1 ->
                ints.forEach { n2 -> if (n1 + n2 == 2020) return@answer n1 * n2 }
            }
        }
        answer {
            ints.forEach { n1 ->
                ints.forEach { n2 ->
                    ints.forEach { n3 -> if (n1 + n2 + n3 == 2020) return@answer n1 * n2 * n3 }
                }
            }
        }
    }
}