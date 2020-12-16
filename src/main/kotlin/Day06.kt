import common.Day
import extension.splitOnBlank

// Answer #1: 6249
// Answer #2: 3103

fun main() {
    Day(n = 6) {
        answer {
            lines.splitOnBlank()
                .map { answers -> answers.joinToString("") }
                .map { answers -> answers.toSet().count() }
                .sum()
        }
        answer {
            lines.splitOnBlank()
                .map { answers -> answers.size to answers.joinToString("") }
                .map { (size, answers) -> answers.groupingBy { it }.eachCount().count { (_, n1) -> n1 == size } }
                .sum()
        }
    }
}

